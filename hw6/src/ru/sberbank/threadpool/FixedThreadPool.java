package ru.sberbank.threadpool;

import java.util.*;

public class FixedThreadPool implements ThreadPool {
    private final Queue<Runnable> queue = new ArrayDeque<>();
    private List<Thread> threadList;
    private boolean isStart = false;

    public FixedThreadPool(int threadCount) {
        threadList = new ArrayList<>();
        for (int i = 0; i < threadCount; i++)
            threadList.add(new Thread(new FixedThreadPoolRunnable(queue)));
    }

    @Override
    public void start() {
        isStart = true;
        threadList.forEach(Thread::start);
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
