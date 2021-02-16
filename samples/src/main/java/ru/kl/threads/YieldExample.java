package ru.kl.threads;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YieldExample {

    private volatile int counter;

    private synchronized void increaseCounter() {
        counter++;
        Thread.yield();
    }

    public void calcCounter() throws InterruptedException {
        Runnable runnable = this::increaseCounter;
        Thread firstThread = new Thread(runnable);
        Thread secondThread = new Thread(runnable);

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.yield();

        YieldExample yieldExample = new YieldExample();
        yieldExample.calcCounter();

        log.info("{}", yieldExample.getCounter());
    }
}
