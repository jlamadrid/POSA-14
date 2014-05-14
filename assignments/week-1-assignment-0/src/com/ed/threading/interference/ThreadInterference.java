package com.ed.threading.interference;

/**
 * Created by jl25292 on 5/13/14.
 */
public class ThreadInterference {

    public static void main(String[] args) throws InterruptedException {

        /**
         * under a single thread exec scenario this counter would always print 0,
         * but because multiple threads are sharing the counter and counter is not thread safe
         * we get random output count.
         */
        Counter counter = new Counter();
        System.out.println("Count: " + counter.value());

        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Count: " + counter.value());
    }
}

class MyThread extends Thread {
    Counter counter;

    MyThread(Counter counter) {
        this.counter = counter;
    }

    public void run() {

        String message = "Running ";
        String threadName = Thread.currentThread().getName();

        System.out.format("%s: %s%n", threadName,  message);

        for (int x = 0; x < 100000; x++) {
            counter.increment();
            counter.decrement();
            counter.increment();
            counter.decrement();
        }
    }
}

class Counter {

    private int c = 0;

    public void increment() {
        c = c + 1;
    }

    public void decrement() {
        c = c - 1;
    }

    public int value() {
        return c;
    }
}
