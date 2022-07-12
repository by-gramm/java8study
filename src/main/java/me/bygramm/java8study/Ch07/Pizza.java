package me.bygramm.java8study.Ch07;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Repeatable(PizzaContainer.class)
public @interface Pizza {
    String value();
}
