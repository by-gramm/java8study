package me.bygramm.java8study.Ch01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class MethodReference {

    public static void main(String[] args) {
        // 1. 스태틱 메소드 참조
        UnaryOperator<String> hi = Greeting::hi;

        // 2. 인스턴스 메소드 참조
        Greeting greeting1 = new Greeting();
        UnaryOperator<String> hello = greeting1::hello;

        // 3-1. 인자가 없는 생성자 참조
        Supplier<Greeting> greeting2 = Greeting::new;
        greeting2.get();

        // 3-2. 인자가 있는 생성자 참조
        Function<String, Greeting> greeting3 = Greeting::new;
        Greeting bygramm = greeting3.apply("justgramm");
        System.out.println(bygramm.getName());                 // justgramm

        // 4. 임의의 (불특정 다수의) 인스턴스 메소드 참조
        String[] names = {"paul", "john", "george", "ringo"};
        Arrays.sort(names, String::compareToIgnoreCase);       // [george, john, paul, ringo]

        /**
         * 참고 : Java 8 이전의 정렬 => Comparator 활용
         */
        Arrays.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return 0;
            }
        });

    }
}
