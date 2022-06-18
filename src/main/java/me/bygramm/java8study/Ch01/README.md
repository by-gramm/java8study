# Ch01. 함수형 인터페이스와 람다



### 함수형 인터페이스 (Functional Interface)

> 1개의 추상 메소드를 갖는 인터페이스

- Java 7까지 => 인터페이스에는 오직 추상 메소드만 정의 가능
- Java 8 이후 => 인터페이스에 default method, static method 정의 가능
- default/static method의 개수와 관계없이, **추상 메소드가 1개면  함수형 인터페이스**다.
- `@FunctionalInterface` 어노테이션을 추가하면, 컴파일러에서 함수형 인터페이스를 제대로 정의했는지 체크한다.



**순수 함수**

- 1. 같은 input에 대해 항상 같은 output을 리턴하는 함수

- 2. 함수 외부의 어떤 상태도 변경시키지 않는 함수

- 이러한 함수를 통해 데이터를 처리하는 프로그래밍 패러다임이 함수형 프로그래밍이다.

<br>

### 람다 표현식 (Lambda Expression)

> 함수를 하나의 식으로 표현한 것

- 함수형 프로그래밍을 지원하기 위해 Java 8에서 도입되었다.

- **람다 표현식은 함수형 인터페이스에 대해서만 사용 가능하다.**
  - 하나의 람다 표현식으로 여러 추상 메소드를 동시에 구현할 수는 없기 때문이다.

```java
// RunSomething.java

@FunctionalInterface
public interface RunSomething {
    void printABC();
}
```

```java
// Foo.java
public class Foo {

    public static void main(String[] args) {
        RunSomething runSomething = () -> System.out.println("ABC");
        runSomething.printABC();
    }
}
```

- 위 코드에서는 람다 표현식을 통해 직접 RunSomething 인터페이스의 추상 메소드(`printABC`)를 구현했다. 
- 람다 표현식은 이름이 없지만, 그럼에도 이 람다 표현식이 `printABC` 메소드를 구현한 것임을 알 수 있다. 함수형 인터페이스는 구현해야 할 추상 메소드가 하나밖에 없기 때문이다. 
- 반면 인터페이스의 추상 메소드가 2개 이상이라면, 하나의 람다 표현식으로 구현할 수 없을 것이다. 



**vs 로컬 클래스, 익명 클래스**

- 공통점
  - 클래스 외부의 변수를 참조할 수 있다.
- 차이점
  - **섀도잉(shadowing)**
    - 메소드 밖에 선언된 것과 같은 이름의 변수를 사용하는 경우, 외부 변수가 가려지는 것
  
  - 람다 표현식은 로컬 클래스/익명 클래스와 달리 섀도잉이 이루어지지 않는다.
    - 람다 표현식은 람다를 감싸고 있는 영역과 동일한 스코프를 가지기 때문이다.
  - 람다 표현식에 사용되는 변수는 `final`이거나 사실상 `final`이어야 한다.
    - 아래 코드의 경우, 람다 표현식 내부에서 `final`이 아닌 변수 num이 사용되었으므로, 컴파일 에러가 발생한다.
  

```java
private void run() {
    int num = 10;

    IntConsumer printNum = () -> {
        System.out.println(num);   // 컴파일 에러!
    };

    num++; 
}
```

<br>

### Java가 기본 제공하는 함수형 인터페이스

아래의 함수형 인터페이스는 모두 `java.util.function` 패키지 내에 존재한다.



**Function<T, R>**

- `R apply(T t)`
  - 하나의 값을 받아서 하나의 값을 리턴한다.
- `A.compose(B)`
  - input값을 B 함수에 대입한 후, 리턴값을 다시 A 함수에 대입한다.
- `A.andThen(B)`
  - input값을 A 함수에 대입한 후, 리턴값을 다시 B 함수에 대입한다.



**BiFunction<T, U, R>**

- `R apply(T t, U u)`
  - 2개의 값을 받아서 하나의 값을 리턴한다.



**Consumer<T>**

- `void accept(T t)`
  - 값을 받은 뒤 아무 값도 리턴하지 않는다.



**Supplier<T>**

- `T get()`
  - 값을 입력받지 않고, T 타입 값을 리턴한다.



**Predicate<T>**

- `boolean test(T t)`
  - 값을 받아서 boolean값을 리턴한다.



**UnaryOperator<T> extends Function<T, T>**

- Function 인터페이스에서 입력값과 리턴값의 타입이 동일한 경우



**BinaryOperator<T> extends BiFunction<T, T, T>**

- BiFunction 인터페이스에서 입력값 2개와 리턴값의 타입이 모두 동일한 경우

<br>

### 메소드 레퍼런스 (Method Reference)

- `::`으로 표현

- `타입::스태틱 메소드`

  - 스태틱 메소드 참조

- `인스턴스 변수::인스턴스 메소드`

  - 특정 객체의 인스턴스 메소드 참조

- `타입::new`

  - 생성자 참조

  - 리턴 타입에 따라 특정 생성자를 참조하도록 할 수 있다.

- `타입::인스턴스 메소드`

  - 임의의 (불특정 다수의) 인스턴스 메소드 참조