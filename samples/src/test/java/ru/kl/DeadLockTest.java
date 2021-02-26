package ru.kl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kl.threads.accountservice.Account;
import ru.kl.threads.accountservice.AccountService;
import ru.kl.threads.accountservice.DefaultAccountService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeadLockTest {

    private static final long FIRST_PERSON_ID = 1L;
    private static final long SECOND_PERSON_ID = 2L;
    private static final long ACCOUNT_BALANCE = 100L;
    private static final long FIRST_TRANSACTION_AMOUNT = 10L;
    private static final long SECOND_TRANSACTION_AMOUNT = 5L;

    private AccountService accountService;

    private ExecutorService executorService;

    @BeforeEach
    void fillData() {
        Map<Long, Account> accounts = new HashMap<>();
        accounts.put(FIRST_PERSON_ID, new Account(FIRST_PERSON_ID, ACCOUNT_BALANCE));
        accounts.put(SECOND_PERSON_ID, new Account(SECOND_PERSON_ID, ACCOUNT_BALANCE));
        accountService = new DefaultAccountService(accounts);

        executorService = Executors.newFixedThreadPool(3);
    }

    @AfterEach
    void shutdownExecutor() {
        if (executorService != null) executorService.shutdown();
    }

    @Test
    void withDeadLockTest() throws InterruptedException {
        executorService.submit(
                () -> accountService.makeTransactionWithDeadLock(
                        FIRST_PERSON_ID,
                        SECOND_PERSON_ID,
                        FIRST_TRANSACTION_AMOUNT
                )
        );
        executorService.submit(
                () -> accountService.makeTransactionWithDeadLock(
                        SECOND_PERSON_ID,
                        FIRST_PERSON_ID,
                        SECOND_TRANSACTION_AMOUNT
                )
        );

        Thread.sleep(3000);
        assertTrue(ACCOUNT_BALANCE == accountService.getBalance(FIRST_PERSON_ID)
                && ACCOUNT_BALANCE == accountService.getBalance(SECOND_PERSON_ID));
    }

    @Test
    void withoutDeadLockTestWithLock() throws ExecutionException, InterruptedException {
        Boolean firstAttempt = executorService.submit(
                    () -> accountService.makeTransactionWithoutDeadLockFirstMethod(
                            FIRST_PERSON_ID,
                            SECOND_PERSON_ID,
                            FIRST_TRANSACTION_AMOUNT
                    )
                )
                .get();
        Boolean secondAttempt = executorService.submit(
                    () -> accountService.makeTransactionWithoutDeadLockFirstMethod(
                            SECOND_PERSON_ID,
                            FIRST_PERSON_ID,
                            SECOND_TRANSACTION_AMOUNT
                    )
                ).get();

        await().until(() -> firstAttempt);
        await().until(() -> secondAttempt);
    }

    @Test
    void withoutDeadLockTestWithSynchronized() throws InterruptedException {
        executorService.submit(
                () -> accountService.makeTransactionWithoutDeadLockSecondMethod(
                        FIRST_PERSON_ID,
                        SECOND_PERSON_ID,
                        FIRST_TRANSACTION_AMOUNT
                )
        );
        executorService.submit(
                () -> accountService.makeTransactionWithoutDeadLockSecondMethod(
                        SECOND_PERSON_ID,
                        FIRST_PERSON_ID,
                        SECOND_TRANSACTION_AMOUNT
                )
        );

        Thread.sleep(3000);
        assertTrue(
                ACCOUNT_BALANCE - FIRST_TRANSACTION_AMOUNT + SECOND_TRANSACTION_AMOUNT
                        == accountService.getBalance(FIRST_PERSON_ID)
                && ACCOUNT_BALANCE + FIRST_TRANSACTION_AMOUNT - SECOND_TRANSACTION_AMOUNT
                        == accountService.getBalance(SECOND_PERSON_ID)
        );
    }
}
