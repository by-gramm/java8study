package me.bygramm.java8study.Ch01;

public class Foo {

    /**
     * Java 8 전
     */
//    public static void main(String[] args) {
//        RunSomething runSomething = new RunSomething() {
//            @Override
//            public void printABC() {
//                System.out.println("ABC");
//            }
//        };
//    }

    /**
     * Java 8 이후 => 람다 표현식 사용 가능
     */
    public static void main(String[] args) {
        RunSomething runSomething = () -> System.out.println("ABC");
        runSomething.printABC();
    }
}
