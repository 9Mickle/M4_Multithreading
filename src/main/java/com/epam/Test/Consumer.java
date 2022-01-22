package com.epam.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {

    private final BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String str = queue.take();
                if (str.length() <= 100) {
                    System.out.println("cons1: " + str);
                } else {
                    queue.put(str);
                }
                if (str.equals("----------.----------.----------.----------.----------.----------.----------.----------.----------.----------."))
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void run() {
//        try {
//            while (true) {
//                while (!queue.isEmpty()) {
//                    String str = queue.element();
//                    if (str.length() <= 100) {
//                        System.out.println("cons1: " + str);
//                        queue.remove(str);
//                    }
//                    if (str.equals("--")) return;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
