package me.bygramm.java8study.Ch06;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class CallableAndFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Future<String> helloFuture = executorService.submit(hello);
        System.out.println(helloFuture.isDone());
        System.out.println("Started!");

        helloFuture.get();  // blocking call
        helloFuture.cancel(true);

        System.out.println(helloFuture.isDone());

        System.out.println("End!");
        executorService.shutdown();


        ExecutorService executorService2 = Executors.newFixedThreadPool(4);

        Callable<String> java = () -> {
            Thread.sleep(2000L);
            return "Java";
        };

        Callable<String> python = () -> {
            Thread.sleep(3000L);
            return "Python";
        };

        Callable<String> javascript = () -> {
            Thread.sleep(1000L);
            return "Javascript";
        };

        List<Future<String>> futures = executorService2.invokeAll(Arrays.asList(java, python, javascript));
        for (Future<String> future : futures) {
            System.out.println(future.get());
        }

        String s = executorService2.invokeAny(Arrays.asList(java, python, javascript));
        System.out.println("s = " + s);

        executorService2.shutdown();
    }
}
