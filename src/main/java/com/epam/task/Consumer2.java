package com.epam.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Consumer2 implements Runnable {

    private final BlockingQueue<String> queue;
    private final ConcurrentHashMap<String, List<String>> generalMap;

    private final List<String> collection = new ArrayList<>();
    private final Map<String, Integer> resultMap = new HashMap<>();

    public Consumer2(BlockingQueue<String> queue, ConcurrentHashMap<String, List<String>> map) {
        this.queue = queue;
        this.generalMap = map;
    }

    @Override
    public void run() {
        final String STOP_STRING = "--";
        final String USELESS = "----------.----------.----------.----------.----------.----------.----------.----------.----------.----------.";
        final String threadShortName = "cons2";

        Thread.currentThread().setName(threadShortName);
        try {
            while (true) {
                if (!queue.isEmpty()) {
                    String str = queue.peek();
                    if (str != null) {
                        if (str.length() > 100) {
                            collection.add(str);
                            System.out.println(threadShortName + ": " + str);
                            queue.remove(str);
                        }
                        if (str.equals(STOP_STRING)) {
                            collection.remove(USELESS);
                            generalMap.put(threadShortName, collection);
                            resultMap.put(threadShortName, getCountWords(collection));
                            System.out.println("\n" + resultMap);
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Integer getCountWords(List<String> collection) {
        return collection.stream()
                .map(s -> s.split(" ").length)
                .reduce(0, Integer::sum);
    }
}
