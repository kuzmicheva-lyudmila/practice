package ru.kl.blockingqueue.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public interface Manager {

    Callable<String> served(BlockingQueue<String> queue);
}
