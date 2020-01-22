package com.vitalykhan.webapps;

public class MultiThreading {
    private static int counter = 0;
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(getName() + "  " + i);
                }
            }
        };
//        thread0.start();

        long lStart = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    increment();
                }
            }).start();
        }
        Thread.sleep(500);
        System.out.println(System.currentTimeMillis() - lStart + " ms");
        System.out.println(counter);

    }
    private static void increment() {
        for (int i = 0; i < 1000; i++) {
            double a = Math.sin(0.13);
        }
        synchronized (MultiThreading.class) {
            counter++;
        }

    }
}
