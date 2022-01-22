package com.epam.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer2 implements Runnable{

    public Consumer2(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    private final BlockingQueue<String> queue;

    @Override
    public void run() {
        try {
            while (true) {
                while (!queue.isEmpty()) {
                    String str = queue.element();
                    if (str.length() > 100) {
                        System.out.println("cons2: " + str);
                        queue.remove(str);
                    }
                    if (str.equals("----------.----------.----------.----------.----------.----------.----------.----------.----------.----------."))
                        return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
