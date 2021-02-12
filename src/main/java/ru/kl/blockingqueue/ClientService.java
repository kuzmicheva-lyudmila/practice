package ru.kl.blockingqueue;

import ru.kl.blockingqueue.client.Client;
import ru.kl.blockingqueue.manager.Manager;

import java.util.concurrent.*;

import static ru.kl.blockingqueue.CheckParametersUtils.checkLeftRangeBorder;
import static ru.kl.blockingqueue.CheckParametersUtils.checkNotNull;

public class ClientService {

    private final BlockingQueue<String> queue;
    private final ExecutorService executor;

    public ClientService(int capacity, ExecutorService executor) {
        checkLeftRangeBorder("capacity", capacity);
        checkNotNull("executor", executor);
        this.queue = new ArrayBlockingQueue<>(capacity);
        this.executor = executor;
    }

    public int queueSize() {
        return queue.size();
    }

    public String element() {
        return queue.element();
    }

    public String peek() {
        return queue.peek();
    }

    public boolean contains(String s) {
        return queue.contains(s);
    }

    public Future<Boolean> registerClient(Client client) {
        checkNotNull("client", client);
        return executor.submit(client.registered(queue));
    }

    public Future<String> registerManager(Manager manager) {
        checkNotNull("manager", manager);
        return executor.submit(manager.served(queue));
    }
}
