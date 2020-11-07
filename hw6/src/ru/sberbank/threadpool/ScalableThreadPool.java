package ru.sberbank.threadpool;

import java.util.*;

public class ScalableThreadPool implements ThreadPool {
    private final Queue<Runnable> queue = new ArrayDeque<>();
    private final ThreadManager threadManager;
    private boolean isStart = false;

    public ScalableThreadPool(int threadCountMin, int threadCountMax, int threshold) {
        threadManager = new ThreadManager(queue, threadCountMin, threadCountMax, threshold);
    }

    public ScalableThreadPool(int threadCountMin, int threadCountMax) {
        this(threadCountMin, threadCountMax, 3);
    }

    @Override
    public void start() {
        isStart = true;
        threadManager.start();
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (queue) {
            queue.add(runnable);
            if (isStart) {
                queue.notify();
            }
        }
    }
}