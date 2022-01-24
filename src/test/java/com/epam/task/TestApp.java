package com.epam.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

public class TestApp {

    private static BlockingQueue<String> queue;
    private static ConcurrentHashMap<String, List<String>> map;
    private static ExecutorService executorService;
    private static List<Future<ConcurrentHashMap<String, List<String>>>> futures;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        queue = new LinkedBlockingQueue<>(5);
        map = new ConcurrentHashMap<>();

        List<Callable<ConcurrentHashMap<String, List<String>>>> taskList =
                List.of(new Producer(queue), new Consumer(queue, map), new Consumer2(queue, map));

        executorService = Executors.newCachedThreadPool();
        futures = executorService.invokeAll(taskList);
        executorService.shutdown();
    }

    /**
     * Проверка на кол-во свободных мест в очереди.
     */
    @Test
    public void checkQueueCapacity() {
        int expected = 5;
        int actual = queue.remainingCapacity();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Проверка количества элементов в мапе.
     */
    @Test
    public void checkCountElemInMap() {
        int expected = 2;
        int actual = map.size();
        Assertions.assertFalse(map.isEmpty());
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Проверка кол-ва слов в документе.
     */
    @Test
    public void checkCountWords() {
        int expected = 14384;
        int countWords1 = Integer.parseInt(map.get("cons1").get(0));
        int countWords2 = Integer.parseInt(map.get("cons2").get(0));
        int actual = countWords1 + countWords2;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkQueue() {
        Assertions.assertTrue(queue.isEmpty());
        Assertions.assertThrows(NoSuchElementException.class, () -> queue.element());
        Assertions.assertNull(queue.peek());

        // Переполнение очереди.
        Assertions.assertThrows(IllegalStateException.class, () -> {
            for (int i = 0; i < 6; i++) {
                queue.add(String.valueOf(i));
            }
        });
    }

    @Test
    public void checkExecutorService() {
        Assertions.assertTrue(executorService.isShutdown());
        Assertions.assertTrue(executorService.isTerminated());
    }

    @Test
    public void checkFuturesList() {
        Assertions.assertFalse(futures.isEmpty());
        Assertions.assertEquals(3, futures.size());
    }
}
