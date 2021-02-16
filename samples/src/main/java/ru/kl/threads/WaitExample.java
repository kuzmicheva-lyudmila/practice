package ru.kl.threads;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitExample {

    private synchronized void callWait() {
        log.info(Thread.currentThread().getName() + " before waiting...");
        try {
            this.wait(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info(Thread.currentThread().getName() + " after waiting...");
    }

    public void testWait() throws InterruptedException {
        Runnable runnable = this::callWait;
        Thread firstThread = new Thread(runnable);
        Thread secondThread = new Thread(runnable);

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }

    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        try {
            o.wait();
        } catch (IllegalMonitorStateException e) {
            log.error("wait() must be called from within a synchronized context", e);
        }

        new WaitExample().testWait();
    }
}
