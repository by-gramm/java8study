package me.bygramm.java8study.Ch01;

public class Greeting {

    private String name;

    // 3-1
    public Greeting() {
    }

    // 3-2
    public Greeting(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 1
    public static String hi(String name) {
        return "hi " + name;
    }

    // 2
    public String hello(String name) {
        return "hello " + name;
    }

}
