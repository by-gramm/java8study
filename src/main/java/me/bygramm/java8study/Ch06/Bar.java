package me.bygramm.java8study.Ch06;

import java.util.concurrent.*;

public class Bar {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            System.out.println("First = " + Thread.currentThread().getName());
            return "First";
        });

        CompletableFuture<String> future = first.thenCompose(Bar::getNext);
        System.out.println(future.get());


        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            System.out.println("Second = " + Thread.currentThread().getName());
            return "Second";
        });

        CompletableFuture<String> future2 = first.thenCombine(second, (f, s) -> f + " | " + s);
        System.out.println(future2.get());

        // 예외 처리

        boolean throwError = true;

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalArgumentException();
            }
            System.out.println("Hello = " + Thread.currentThread().getName());
            return "Hello";
        }).handle((result, ex) -> {
            // result: 정상 실행 후 반환한 결과값
            // ex: 실행중 발생한 exception
            if (ex != null) {
                System.out.println(ex);
                System.out.println(result);  // null
                return "ERROR!";
            }
            return result;
        });

        System.out.println(hello.get());
    }

    private static CompletableFuture<String> getNext(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Next = " + Thread.currentThread().getName());
            return "Next";
        });
    }
}
