package me.bygramm.java8study.Ch07;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class ParallelSort {

    public static void main(@Chicken String[] args) {
        int size = 1500;
        int[] numbers = new int[size];
        Random random = new Random();

        // Arrays.sort() => 싱글 쓰레드 사용
        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        long start = System.nanoTime();
        Arrays.sort(numbers);
        System.out.println("serial sorting took " + (System.nanoTime() - start));

        // Arrays.parallelSort() => 멀티 쓰레드 사용
        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        start = System.nanoTime();
        Arrays.parallelSort(numbers);
        System.out.println("parallel sorting took " + (System.nanoTime() - start));

        // 결과
        // serial sorting took 1173500
        // parallel sorting took 614800
    }
}
