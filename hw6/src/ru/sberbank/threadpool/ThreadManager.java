package ru.sberbank.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ThreadManager {
    private final Queue<Runnable> queue;
    private List<Thread> threadList;
    private final int threadCountMin;
    private final int threadCountMax;
    private final int threshold;

    public ThreadManager(Queue<Runnable> queue, int threadCountMin, int threadCountMax, int threshold) {
        this.queue = queue;
        this.threadCountMin = threadCountMin;
        this.threadCountMax = threadCountMax;
        this.threshold = threshold;
        threadList = new ArrayList<>();
        for (int i = 0; i < threadCountMin; i++)
            threadList.add(new Thread(new ScalableThreadPoolRunnable(queue, this)));
    }

    public synchronized void taskRun() {
        if (queue.size() > threshold && threadList.size() < threadCountMax) {
            Thread thread = new Thread(new ScalableThreadPoolRunnable(queue, this));
            threadList.add(thread);
            thread.start();
        }
    }

    public synchronized boolean taskWait() {
        if (threadList.size() > threadCountMin) {
            Thread thread = threadList.remove(threadList.indexOf(Thread.currentThread()));
            thread.interrupt();
            return true;
        }
        return false;
    }

    public synchronized void start() {
        threadList.forEach(Thread::start);
    }
}
