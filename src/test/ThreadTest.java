package test;

public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(new Task());
        Thread thread2 = new Thread(new Task());
        Thread thread3 = new Thread(new Task());

        // blocking
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
        thread3.join();

        // non-blocking
//        thread1.start();
//        thread2.start();
//        thread3.start();
//
        thread1.join();
        thread2.join();
        thread3.join();
        // Mono.zip(api1, api2, api3).block();

        // Mono.block(api1, api2, api3);

        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - start));
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

