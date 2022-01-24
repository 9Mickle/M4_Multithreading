package com.epam.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class Consumer2 implements Callable<ConcurrentHashMap<String, List<String>>> {

    private final BlockingQueue<String> queue;
    private final ConcurrentHashMap<String, List<String>> generalMap;

    private final List<String> collection = new ArrayList<>();

    public Consumer2(BlockingQueue<String> queue, ConcurrentHashMap<String, List<String>> map) {
        this.queue = queue;
        this.generalMap = map;
    }

    @Override
    public ConcurrentHashMap<String, List<String>> call() {
        final String threadShortName = "cons2";

        Thread.currentThread().setName(threadShortName);
        try {
            while (true) {
                if (!queue.isEmpty()) {
                    String str = queue.peek();
                    if (str != null && str.length() > 100) {
                        collection.add(str);
                        System.out.println(threadShortName + ": " + str);
                        queue.remove(str);
                    }
                } else {
                    if (Producer.isDone()) {
                        generalMap.put(threadShortName, List.of(String.valueOf(getCountWords(collection))));
                        return generalMap;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generalMap;
    }

    private Integer getCountWords(List<String> collection) {
        return collection.stream()
                .map(s -> s.split(" ").length)
                .reduce(0, Integer::sum);
    }
}
