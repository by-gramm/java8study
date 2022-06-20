package me.bygramm.java8study.Ch02;

public interface Foo {

    void printName();

    /**
     * @implSpec
     * 이 메소드는 getName()으로 가져온 문자열을 대문자로 바꾸어 출력한다.
     * getName()으로 null을 가져올 경우 오류가 발생할 수 있으므로, 재정의해야 한다.
     */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    /**
     * @implSpec
     * 이 메소드는 getName()으로 가져온 문자열을 소문자로 바꾸어 출력한다.
     * getName()으로 null을 가져올 경우 오류가 발생할 수 있으므로, 재정의해야 한다.
     */
    default void printNameLowerCase() {
        System.out.println(getName().toLowerCase());
    }

    String getName();

}
