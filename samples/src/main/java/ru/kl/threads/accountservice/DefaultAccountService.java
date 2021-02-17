package ru.kl.threads.accountservice;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DefaultAccountService implements AccountService {

    private static final int LOCK_ATTEMPTS = 3;
    private static final long MAX_LOCK_TIMEOUT_MS = 1000;
    public static final String LOCKED_FIRST_ACCOUNT = "Locked first account: {}";
    public static final String LOCKED_SECOND_ACCOUNT = "Locked second account: {}";

    private final ConcurrentHashMap<Long, Account> accounts;
    private final ReentrantLock firstAccountLock;
    private final ReentrantLock secondAccountLosk;

    public DefaultAccountService(Map<Long, Account> accounts) {
        this.accounts = new ConcurrentHashMap<>();
        accounts.forEach(this.accounts::put);
        firstAccountLock = new ReentrantLock();
        secondAccountLosk = new ReentrantLock();
    }

    @Override
    public void makeTransactionWithDeadLock(long firstPersonId, long secondPersonId, long amount) {
        Account firstAccount = getAccount(firstPersonId);
        Account secondAccount = getAccount(secondPersonId);

        synchronized(firstAccount) {
            log.info(LOCKED_FIRST_ACCOUNT, firstPersonId);
            checkTransaction();
            synchronized(secondAccount) {
                log.info(LOCKED_SECOND_ACCOUNT, secondPersonId);
                executeTransaction(firstAccount, secondAccount, amount);
            }
        }
        log.info("Success transaction");
    }

    @Override
    public boolean makeTransactionWithoutDeadLockFirstMethod(long firstPersonId, long secondPersonId, long amount) {
        Account firstAccount = getAccount(firstPersonId);
        Account secondAccount = getAccount(secondPersonId);

        int count = 0;
        boolean isSuccessfulTransaction = false;
        while (count < LOCK_ATTEMPTS && !isSuccessfulTransaction) {
            count++;
            try {
                if (firstAccountLock.tryLock(generateTimeout(), TimeUnit.MILLISECONDS)) {
                    log.info(LOCKED_FIRST_ACCOUNT, firstPersonId);
                    checkTransaction();
                    if (secondAccountLosk.tryLock(generateTimeout(), TimeUnit.MILLISECONDS)) {
                        log.info(LOCKED_SECOND_ACCOUNT, secondPersonId);
                        executeTransaction(firstAccount, secondAccount, amount);

                        log.info("Success transaction with attempts: {}", count);
                        isSuccessfulTransaction = true;
                    }
                }
            } catch (InterruptedException e) {
                count = LOCK_ATTEMPTS;
                Thread.currentThread().interrupt();
            } finally {
                unlock(firstAccountLock);
                unlock(secondAccountLosk);
            }
        }

        return isSuccessfulTransaction;
    }

    public void makeTransactionWithoutDeadLockSecondMethod(long firstPersonId, long secondPersonId, long amount) {
        Account firstAccount;
        Account secondAccount;
        long amountForTransaction = amount;

        if (firstPersonId > secondPersonId) {
            firstAccount = getAccount(secondPersonId);
            secondAccount = getAccount(firstPersonId);
            amountForTransaction *= -1;
        } else {
            firstAccount = getAccount(firstPersonId);
            secondAccount = getAccount(secondPersonId);
        }

        synchronized(firstAccount) {
            log.info(LOCKED_FIRST_ACCOUNT, firstPersonId);
            checkTransaction();
            synchronized(secondAccount) {
                log.info(LOCKED_SECOND_ACCOUNT, secondPersonId);
                executeTransaction(firstAccount, secondAccount, amountForTransaction);
            }
        }
        log.info("Success transaction");
    }

    @Override
    public long getBalance(long personId) {
        Account account = getAccount(personId);
        return account.getBalance();
    }

    private void unlock(ReentrantLock accountLock) {
        if (accountLock.isLocked()) {
            accountLock.unlock();
        }
    }

    private long generateTimeout() {
        return ThreadLocalRandom.current().nextLong(MAX_LOCK_TIMEOUT_MS / 2, MAX_LOCK_TIMEOUT_MS);
    }

    private Account getAccount(long personId) {
        Account account = accounts.get(personId);
        if (Objects.isNull(account)) {
            throw new IllegalArgumentException("Account doesn't find for personId = " + personId);
        }

        return account;
    }

    private void checkTransaction() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void executeTransaction(Account firstAccount, Account secondAccount, long amount) {
        firstAccount.setBalance(firstAccount.getBalance() - amount);
        secondAccount.setBalance(secondAccount.getBalance() + amount);
    }
}
