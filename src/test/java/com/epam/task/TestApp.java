package com.epam.task;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;

public class TestApp {

    private static BlockingQueue<String> queue;
    private static ConcurrentHashMap<String, List<String>> map;
    private static boolean flag;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        queue = new LinkedBlockingQueue<>(5);
        map = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(new Producer(queue));
        executorService.execute(new Consumer(queue, map));
        executorService.execute(new Consumer2(queue, map));

        executorService.shutdown();
        flag = executorService.awaitTermination(5, TimeUnit.SECONDS);
        queue.clear();
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
        if (flag) {
            int expected = 2;
            int actual = map.size();
            Assertions.assertEquals(expected, actual);
        }
    }

    /**
     * Проверка кол-ва слов в документе.
     */
    @Test
    public void checkCountWords() {
        if (flag) {
            int expected = 14384;

            int countWords1 = map.get("cons1")
                    .stream()
                    .map(s -> s.split(" ").length)
                    .reduce(0, Integer::sum);

            int countWords2 = map.get("cons2")
                    .stream()
                    .map(s -> s.split(" ").length)
                    .reduce(0, Integer::sum);

            int actual = countWords1 + countWords2;
            Assertions.assertEquals(expected, actual);
        }
    }
}
