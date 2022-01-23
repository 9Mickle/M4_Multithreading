package com.epam.task;

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
            // Стоп слово для consumer2.
            queue.put("--");
            // Стоп слово для consumer1.
            queue.put("----------.----------.----------.----------.----------.----------.----------.----------.----------.----------.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
