package me.bygramm.java8study.Ch06;

import java.util.concurrent.*;

public class ExecutorPractice {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(getRunnable("Kendrick"));
        executorService.submit(getRunnable("Earl"));
        executorService.submit(getRunnable("Vince"));
        executorService.submit(getRunnable("Tyler"));
        executorService.submit(getRunnable("Denzel"));

        executorService.shutdown();

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(getRunnable("Hello"), 3, TimeUnit.SECONDS);   // 3초 후 실행

        scheduledExecutorService.shutdown();
    }

    private static Runnable getRunnable(String message) {
        return () -> System.out.println(message + " => " + Thread.currentThread().getName());
    }
}
