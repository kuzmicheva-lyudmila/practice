package ru.kl.blockingqueue.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static ru.kl.blockingqueue.CheckParametersUtils.*;

public final class WaitWithTimerManager implements Manager {

    private final long timeout;

    private final TimeUnit unit;

    public WaitWithTimerManager(long timeout, TimeUnit unit) {
        checkLeftRangeBorder("timeout", timeout);
        checkNotNull("unit", unit);

        this.timeout = timeout;
        this.unit = unit;
    }

    @Override
    public Callable<String> served(BlockingQueue<String> queue) {
        return () -> queue.poll(timeout, unit);
    }
}
