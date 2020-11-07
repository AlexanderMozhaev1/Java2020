package ru.sberbank.threadpool;

import java.util.Queue;

public class ScalableThreadPoolRunnable implements Runnable {
    private final Queue<Runnable> queue;
    private final ThreadManager threadManager;

    public ScalableThreadPoolRunnable(Queue<Runnable> queue, ThreadManager threadManager) {
        this.queue = queue;
        this.threadManager = threadManager;
    }

    @Override
    public void run() {
        Runnable task = getTask();
        while (!Thread.currentThread().isInterrupted()){
            threadManager.taskRun();
            doRun(task);
            task = getTask();
        }
    }

    private Runnable getTask() {
        Runnable task;
        synchronized (queue){
            while (queue.isEmpty()){
                if(threadManager.taskWait()) return null;
                doWait();
            }
            task = queue.remove();
        }
        return task;
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
