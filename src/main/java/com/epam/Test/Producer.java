package com.epam.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (String str : Files.readAllLines(Path.of("./data/data.txt"))) {
                queue.put(str);
            }
            // Для consumer1.
            queue.put("--");
            // Строка более 100 символов для consumer2.
            queue.put("----------.----------.----------.----------.----------.----------.----------.----------.----------.----------.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
