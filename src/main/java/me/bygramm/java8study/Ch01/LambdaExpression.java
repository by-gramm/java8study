package me.bygramm.java8study.Ch01;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class LambdaExpression {

    public static void main(String[] args) {
        LambdaExpression lambdaExpression = new LambdaExpression();
        lambdaExpression.run();
    }

    private void run() {
        int baseNumber = 10;

        // 로컬 클래스
        class LocalClass {
            void printBaseNumber() {
                int baseNumber = 11;
                System.out.println(baseNumber);  // 11 (셰도잉 O)
            }
        }

        // 익명 클래스
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer baseNumber) {
                System.out.println(baseNumber);  // 매개변수로 들어온 값 출력 (셰도잉 O)
            }
        };

        // 람다 표현식
        // 매개변수명을 i 대신 baseNumber로 할 수 없다. 람다에서는 셰도잉이 불가능하기 때문이다.
        IntConsumer printInt = (i) -> {
            System.out.println(i + baseNumber);
        };

        printInt.accept(10);
    }
}
