package me.bygramm.java8study.Ch02;

public class DefaultFoo implements Foo, Bar {

    String name;

    public DefaultFoo(String name) {
        this.name = name;
    }

    @Override
    public void printName() {
        System.out.println(name);
    }

    // Foo, Bar가 모두 printNameUpperCase() 디폴트 메소드를 정의했으므로
    // 충돌을 피하기 위해서는 해당 메소드를 재정의해야 한다.
    @Override
    public void printNameUpperCase() {
        Foo.super.printNameUpperCase();
    }

    // Foo의 printNameLowerCase()는 디폴트 메소드이므로 재정의하지 않아도 된다.

    @Override
    public String getName() {
        return name;
    }

}
