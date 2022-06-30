package me.bygramm.java8study.Ch06;

public class JavaConcurrency {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread: " + Thread.currentThread().getName());
        });
        thread.start();

        System.out.println("Main: " + Thread.currentThread().getName());


        Thread thread2 = new Thread(() -> {
            while (true) {
                System.out.println("SecondThread = " + Thread.currentThread().getName());

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    System.out.println("스레드 종료!");
                    return;
                }
            }
        });

        thread2.start();
        Thread.sleep(3000L);
        thread2.interrupt();


        Thread thread3 = new Thread(() -> {
            System.out.println("ThirdThread = " + Thread.currentThread().getName());

            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        thread3.start();

        thread3.join();
        System.out.println("third thread" + " is finished");

    }

}
