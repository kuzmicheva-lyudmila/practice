package ru.kl.threads.accountservice;

public interface AccountService {

    void makeTransactionWithDeadLock(long firstPersonId, long secondPersonId, long amount);
    boolean makeTransactionWithoutDeadLockFirstMethod(long firstPersonId, long secondPersonId, long amount);
    void makeTransactionWithoutDeadLockSecondMethod(long firstPersonId, long secondPersonId, long amount);
    long getBalance(long personId);
}
