package me.bygramm.java8study.Ch07;


import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(@Chicken String[] args) throws @Chicken RuntimeException {
        List<@Chicken String> names = Arrays.asList("bygramm");
    }


    static class FeelsLikeChicken<@Chicken T> {
        public static <@Chicken C> void print(@Chicken C c) {
            System.out.println(c);
        }
    }
}