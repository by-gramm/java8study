package me.bygramm.java8study.Ch03;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();

        names.add("karina");
        names.add("winter");
        names.add("ningning");
        names.add("giselle");

        List<String> collect = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);  // 대문자 이름들 출력

        System.out.println("==============");

        names.forEach(System.out::println);    // 소문자 이름들 출력 (stream은 원본 데이터를 변경하지 않음)

        List<String> collect2 = names.parallelStream().map(s -> {
            System.out.println(s + " " + Thread.currentThread().getName());
            return s.toUpperCase();
        }).collect(Collectors.toList());

        collect2.forEach(System.out::println);  // 병행 처리 => 각각 다른 스레드명을 출력

    }
}
