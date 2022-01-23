package com.epam.task;

import java.util.List;
import java.util.concurrent.*;

public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
        ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(new Producer(queue));
        executorService.execute(new Consumer(queue, map));
        executorService.execute(new Consumer2(queue, map));
        executorService.shutdown();
    }
}
