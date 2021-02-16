package ru.kl.blockingqueue.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static ru.kl.blockingqueue.CheckParametersUtils.*;

public final class WaitWithTimerClient implements Client {

    private final String client;

    private final long timeout;

    private final TimeUnit unit;

    public WaitWithTimerClient(String client, long timeout, TimeUnit unit) {
        checkNotEmpty("client", client);
        checkLeftRangeBorder("timeout", timeout);
        checkNotNull("unit", unit);

        this.client = client;
        this.timeout = timeout;
        this.unit = unit;
    }

    @Override
    public Callable<Boolean> registered(BlockingQueue<String> queue) {
        return () -> queue.offer(client, timeout, unit);
    }
}
