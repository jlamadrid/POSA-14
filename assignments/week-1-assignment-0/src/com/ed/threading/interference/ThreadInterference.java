package com.ed.threading.interference;

/**
 * Created by jl25292 on 5/13/14.
 */
public class ThreadInterference {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();
        System.out.println("Count: " + counter.value());

        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);
        MyThread t3 = new MyThread(counter);
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Count: " + counter.value());
    }
}

class MyThread extends Thread {
    Counter counter;

    MyThread(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int x = 0; x < 100000000; x++) {
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
