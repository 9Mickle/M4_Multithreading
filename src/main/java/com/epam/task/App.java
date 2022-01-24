package com.epam.task;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class App {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
        ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>();

        List<Callable<ConcurrentHashMap<String, List<String>>>> taskList =
                List.of(new Producer(queue), new Consumer(queue, map), new Consumer2(queue, map));

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.invokeAll(taskList);
        executorService.shutdown();

        System.out.println("\n" + map);
    }
}
