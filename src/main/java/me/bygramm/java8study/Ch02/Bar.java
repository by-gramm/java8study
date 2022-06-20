package me.bygramm.java8study.Ch02;

public interface Bar {

    default void printNameUpperCase() {
        System.out.println("BAR");
    }

}
