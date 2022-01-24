package com.epam.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
        ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>();

        List<Callable<Object>> taskList =
                List.of(new Producer(queue), new Consumer(queue, map), new Consumer2(queue, map));

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.invokeAll(taskList);
        executorService.shutdown();

        Map<String, Integer> resultMap = map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> getCountWords(v.getValue())
                ));

        System.out.println("\n" + resultMap);
    }

    public static Integer getCountWords(List<String> collection) {
        return collection.stream()
                .map(s -> s.split(" ").length)
                .reduce(0, Integer::sum);
    }
}
