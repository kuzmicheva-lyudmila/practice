package ru.kl.threads.accountservice;

public interface AccountService {

    void makeTransactionWithDeadLock(long firstPersonId, long secondPersonId, long amount);
    boolean makeTransactionWithoutDeadLock(long firstPersonId, long secondPersonId, long amount);
    long getBalance(long personId);
}
