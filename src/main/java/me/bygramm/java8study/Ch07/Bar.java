package me.bygramm.java8study.Ch07;


import java.util.Arrays;

@Pizza("페퍼로니")
@Pizza("하와이안")
public class Bar {
    public static void main(String[] args) {
        Pizza[] pizzas = Bar.class.getAnnotationsByType(Pizza.class);
        Arrays.stream(pizzas).forEach(p -> {
            System.out.println(p.value());
        });
    }
}
