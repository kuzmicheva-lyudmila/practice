package ru.kl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kl.blockingqueue.ClientService;
import ru.kl.blockingqueue.client.FastClient;
import ru.kl.blockingqueue.client.WaitClient;
import ru.kl.blockingqueue.client.WaitWithTimerClient;
import ru.kl.blockingqueue.manager.FastManager;
import ru.kl.blockingqueue.manager.WaitManager;
import ru.kl.blockingqueue.manager.WaitWithTimerManager;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.*;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class BlockingQueueTest {

    private static final int QUEUE_CAPACITY = 3;
    private static final String CLIENT_NAME = "client";
    private static final String NEW_CLIENT_NAME = "new_client";

    private ExecutorService executor;

    @BeforeEach
    void beforeEachTest() {
        executor = Executors.newCachedThreadPool();
    }

    @AfterEach
    void afterEachTest() {
        shutdownExecutor(executor);
    }

    @Test
    void retrieveFromEmptyQueryViaPeekTest() {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);
        assertNull(clientService.peek());
    }

    @Test
    void retrieveFromEmptyQueryViaElementTest() {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);
        assertThrows(NoSuchElementException.class, clientService::element);
    }

    @Test
    void retrieveFromFillQueryViaPeekTest() {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);
        preparedData(clientService, 1, CLIENT_NAME);

        assertEquals(CLIENT_NAME, clientService.peek());
    }

    @Test
    void retrieveFromFillQueryViaElementTest() {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);
        preparedData(clientService, 1, CLIENT_NAME);

        assertEquals(CLIENT_NAME, clientService.element());
    }

    @Test
    void registeredFastClientTest() throws InterruptedException {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);
        preparedData(clientService, QUEUE_CAPACITY, CLIENT_NAME);

        Future<Boolean> registerClient = clientService.registerClient(new FastClient(NEW_CLIENT_NAME));

        Boolean result = null;
        try {
            result = registerClient.get();
        } catch (ExecutionException e) {
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
        assertNull(result);
        assertFalse(() -> clientService.contains(NEW_CLIENT_NAME));
    }

    @Test
    void registeredWaitClientTest() throws InterruptedException {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);
        preparedData(clientService, QUEUE_CAPACITY, CLIENT_NAME);

        Future<Boolean> registerClient = clientService.registerClient(new WaitClient(NEW_CLIENT_NAME));
        Thread.sleep(1000);

        clientService.registerManager(new FastManager());
        await().until(registerClient::get);
        assertTrue(() -> clientService.contains(NEW_CLIENT_NAME));
    }

    @Test
    void registeredWaitWithTimerClientSuccessTest() throws InterruptedException {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);
        preparedData(clientService, QUEUE_CAPACITY, CLIENT_NAME);

        Future<Boolean> registerClient = clientService.registerClient(
                new WaitWithTimerClient(NEW_CLIENT_NAME, 1, TimeUnit.SECONDS)
        );
        Thread.sleep(300);

        clientService.registerManager(new FastManager());
        await().until(registerClient::get);
        assertTrue(() -> clientService.contains(NEW_CLIENT_NAME));
    }

    @Test
    void registeredWaitWithTimerClientFailureTest() throws InterruptedException {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);
        preparedData(clientService, QUEUE_CAPACITY, CLIENT_NAME);

        Future<Boolean> registerClient = clientService.registerClient(
                new WaitWithTimerClient(NEW_CLIENT_NAME, 1, TimeUnit.SECONDS)
        );
        Thread.sleep(1500);

        clientService.registerManager(new FastManager());
        await().until(() -> !registerClient.get());
        assertFalse(() -> clientService.contains(NEW_CLIENT_NAME));
    }

    private void preparedData(ClientService clientService, int size, String clientName) {
        for (int i = 0; i < size; i++) {
            clientService.registerClient(new FastClient(clientName));
        }
        await().until(() -> clientService.queueSize() == size);
    }

    @Test
    void registeredFastManagerTest() throws InterruptedException {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);

        Future<String> registerManager = clientService.registerManager(new FastManager());

        String result = null;
        try {
            result = registerManager.get();
        } catch (ExecutionException e) {
            assertEquals(NoSuchElementException.class, e.getCause().getClass());
        }
        assertNull(result);
    }

    @Test
    void registeredWaitManagerTest() throws InterruptedException {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);

        Future<String> registerManager = clientService.registerManager(new WaitManager());
        Thread.sleep(1000);

        clientService.registerClient(new FastClient(NEW_CLIENT_NAME));
        await().until(() -> registerManager.get().equals(NEW_CLIENT_NAME));

        assertFalse(() -> clientService.contains(NEW_CLIENT_NAME));
        assertEquals(0, clientService.queueSize());
    }

    @Test
    void registeredWaitWithTimerManagerSuccessTest() throws InterruptedException {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);

        Future<String> registerManager = clientService.registerManager(
                new WaitWithTimerManager(2, TimeUnit.SECONDS)
        );
        Thread.sleep(300);


        clientService.registerClient(new FastClient(NEW_CLIENT_NAME));
        await().until(() -> registerManager.get().equals(NEW_CLIENT_NAME));

        assertFalse(() -> clientService.contains(NEW_CLIENT_NAME));
        assertEquals(0, clientService.queueSize());
    }

    @Test
    void registeredWaitWithTimerManagerFailureTest() throws InterruptedException {
        ClientService clientService = new ClientService(QUEUE_CAPACITY, executor);

        Future<String> registerManager = clientService.registerManager(
                new WaitWithTimerManager(200, TimeUnit.MILLISECONDS)
        );
        Thread.sleep(1300);


        clientService.registerClient(new FastClient(NEW_CLIENT_NAME));
        await().until(() -> Objects.isNull(registerManager.get()));

        assertTrue(() -> clientService.contains(NEW_CLIENT_NAME));
        assertEquals(1, clientService.queueSize());
    }

    void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.out.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
