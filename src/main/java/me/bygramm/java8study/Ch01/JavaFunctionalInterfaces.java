package me.bygramm.java8study.Ch01;

import java.util.function.*;


public class JavaFunctionalInterfaces{

    public static void main(String[] args) {

        // 1. Function
        Function<Integer, Integer> plus10 = num -> num + 10;
        Function<Integer, Integer> multiply2 = i -> i * 2;

        System.out.println(plus10.apply(10));                     // 20 (10 + 10)
        System.out.println(plus10.compose(multiply2).apply(2));   // 14 (2 * 2 + 10)
        System.out.println(plus10.andThen(multiply2).apply(2));   // 24 (2 + 10) * 2

        // 2. BiFunction
        BiFunction<Integer, Integer, Integer> getSum = (num1, num2) -> num1 + num2;

        System.out.println(getSum.apply(3, 5));                // 3 + 5 = 8

        // 3. Consumer
        Consumer<String> printName = name -> System.out.println("이름 : " + name);

        printName.accept("스프링");                                // 이름 : 스프링

        // 4. Supplier
        final int MAX_COUNT = 100;
        Supplier<Integer> getMaxCount = () -> MAX_COUNT;

        System.out.println("max Count : " + getMaxCount.get());     // max Count : 100

        // 5. Predicate
        Predicate<Integer> isOdd = i -> i % 2 == 1;

        System.out.println(isOdd.test(100));                     // false
        System.out.println(isOdd.test(101));                     // true
    }

}
