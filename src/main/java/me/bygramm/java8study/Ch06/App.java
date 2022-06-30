package me.bygramm.java8study.Ch06;

import java.util.concurrent.*;

public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("bygramm");
        System.out.println(future.get());

        CompletableFuture<String> python = CompletableFuture.completedFuture("python");
        System.out.println(python.get());


        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            System.out.println("Future2 " + Thread.currentThread().getName());
        });
        future2.get();


        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Future3 " + Thread.currentThread().getName());
            return "Hello";
        }).thenApply((s) -> {
            System.out.println(s + " : " + Thread.currentThread().getName());
            return s.toUpperCase();
        });

        System.out.println(future3.get());

    }

}
