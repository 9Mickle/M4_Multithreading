package com.epam.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class Producer implements Callable<ConcurrentHashMap<String, List<String>>> {

    private static boolean done = false;
    private final BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public ConcurrentHashMap<String, List<String>> call() {
        try {
            Files.lines(Path.of("./data/data.txt")).forEach(e -> {
                try {
                    queue.put(e);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        done = true;
        return null;
    }

    public static boolean isDone() {
        return done;
    }
}
