package ru.kl.blockingqueue.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public interface Client {

    Callable<Boolean> registered(BlockingQueue<String> queue);
}
