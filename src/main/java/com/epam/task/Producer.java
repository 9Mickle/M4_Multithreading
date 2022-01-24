package com.epam.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class Producer implements Callable<Object> {

    private static boolean done = false;
    private final BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public Object call() {
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
