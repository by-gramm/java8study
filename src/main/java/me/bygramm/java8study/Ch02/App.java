package me.bygramm.java8study.Ch02;

public class App {

    public static void main(String[] args) {
        Foo foo = new DefaultFoo("ByGramm");
        foo.printName();            // ByGramm

        // default method
        foo.printNameUpperCase();   // BYGRAMM
        foo.printNameLowerCase();   // bygramm

        // static method
        System.out.println(Util.regNumToGender("990101-2000000"));  // Female
        System.out.println(Util.regNumToGender("990101-3000000"));  // Male

    }
}
