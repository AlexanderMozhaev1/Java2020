package ru.sberbank.threadpool;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        FixedThreadPool pool = new FixedThreadPool(5);
//        for(int i = 0; i < 12; i++){
//            int index = i;
//            pool.execute(() -> {
//                System.out.println("index: " + index +" Thread: " + Thread.currentThread().getName() + " Start");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("index: " + index +" Thread: " + Thread.currentThread().getName() + " Stop");
//            });
//        }
//        pool.start();
//
//        Thread.sleep(6000);
//
//        for(int i = 0; i < 20; i++){
//            int index = i;
//            pool.execute(() -> {
//                System.out.println("index: " + index +" Thread: " + Thread.currentThread().getName() + " Start");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("index: " + index +" Thread: " + Thread.currentThread().getName() + " Stop");
//            });
//        }


        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(5, 7);
        for(int i = 0; i < 12; i++){
            int index = i;
            scalableThreadPool.execute(() -> {
                System.out.println("index: " + index +" Thread: " + Thread.currentThread().getName() + " Start");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("index: " + index +" Thread: " + Thread.currentThread().getName() + " Stop");
            });
        }
        scalableThreadPool.start();

        Thread.sleep(16000);

        for(int i = 0; i < 20; i++){
            int index = i;
            scalableThreadPool.execute(() -> {
                System.out.println("index: " + index +" Thread: " + Thread.currentThread().getName() + " Start");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("index: " + index +" Thread: " + Thread.currentThread().getName() + " Stop");
            });
        }
    }
}
