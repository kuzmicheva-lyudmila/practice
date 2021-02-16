package ru.kl.blockingqueue.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public final class FastManager implements Manager {

    @Override
    public Callable<String> served(BlockingQueue<String> queue) {
        return queue::remove;
    }
}
