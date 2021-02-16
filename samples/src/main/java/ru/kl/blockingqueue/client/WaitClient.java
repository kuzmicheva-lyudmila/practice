package ru.kl.blockingqueue.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import static ru.kl.blockingqueue.CheckParametersUtils.checkNotEmpty;

public final class WaitClient implements Client {

    private final String client;

    public WaitClient(String client) {
        checkNotEmpty("client", client);
        this.client = client;
    }

    @Override
    public Callable<Boolean> registered(BlockingQueue<String> queue) {
        return () -> { queue.put(client); return true; };
    }
}
