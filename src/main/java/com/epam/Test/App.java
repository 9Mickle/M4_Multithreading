package com.epam.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class App {

    public static void main(String[] args) {

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(new Producer(queue));
        executorService.execute(new Consumer(queue));
        executorService.execute(new Consumer2(queue));

        executorService.shutdown();
    }
}
