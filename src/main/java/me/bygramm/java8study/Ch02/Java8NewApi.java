package me.bygramm.java8study.Ch02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;

public class Java8NewApi {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();

        names.add("karina");
        names.add("winter");
        names.add("ningning");
        names.add("giselle");

        // Java 8 전의 순회 방식
        System.out.println("======== for ~ of ========");
        for (String name : names) {
            System.out.println(name);
        }

        // 1. forEach
        System.out.println("======== forEach ========");
        names.forEach(name -> System.out.println(name));

        // forEach + method reference
        System.out.println("======== forEach + method reference ========");
        names.forEach(System.out::println);

        // 2. spliterator
        Spliterator<String> spliterator = names.spliterator();

        Spliterator<String> spliterator1 = spliterator.trySplit();

        System.out.println("======== spliterator 시작 ========");
        while (spliterator.tryAdvance(System.out::println));        // ningning, giselle
        System.out.println("======== spliterator1 시작 ========");
        while (spliterator1.tryAdvance(System.out::println));       // karina, winter

        // 3. removeIf
        System.out.println("======== removeIf ========");
        names.removeIf(name -> name.length() > 6);   // 이름 길이가 6보다 크면 제거
        names.forEach(System.out::println);

        names.add("ningning");
        names.add("giselle");

        // 4. comparator
        System.out.println("======== 문자열 정렬 ========");
        names.sort(String::compareToIgnoreCase);       // 문자열 정렬
        names.forEach(System.out::println);

        System.out.println("======== 문자열 역순 정렬 ========");
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        names.sort(compareToIgnoreCase.reversed());    // 문자열 역순 정렬
        names.forEach(System.out::println);

    }
}
