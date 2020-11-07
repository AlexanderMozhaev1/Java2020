package ru.sberbank.threadpool;

import java.util.ArrayList;
import java.util.Queue;

public class FixedThreadPoolRunnable implements Runnable {
    private final Queue<Runnable> queue;

    public FixedThreadPoolRunnable(Queue<Runnable> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        Runnable task;
        while (!Thread.currentThread().isInterrupted()){
            synchronized (queue){
                while (queue.isEmpty()){
                    doWait();
                }
                task = queue.remove();
            }
            doRun(task);
        }
    }

    private void doRun(Runnable task) {
        task.run();
    }

    private void doWait() {
        try {
            queue.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
