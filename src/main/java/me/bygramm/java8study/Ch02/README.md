# 2. 인터페이스의 변화

Java 8 전까지 인터페이스에는 선언부만으로 이루어진 추상 메소드만 정의할 수 있었다. 하지만 Java 8부터는 인터페이스에도 구현부가 포함된 default method나 static method를 정의할 수 있게 되었다.

### default method (디폴트 메소드)

- `default` 키워드를 사용하여 정의한다.

- Q. 디폴트 메소드는 왜 필요한가?

  - 인터페이스에 새로운 추상 메소드를 추가하는 경우, 인터페이스를 구현하는 모든 클래스에서 해당 메소드를 새로 오버라이드해야 한다.
  - 반면 디폴트 메소드는 하위 클래스에서 재정의할 필요가 없어서, **구현 클래스를 변경하지 않고도** 새로운 메소드를 추가할 수 있다.

- Q. 디폴트 메소드의 위험성은 무엇인가?

  - 구현체에 따라 런타임 에러가 발생할 수 있다. 따라서

    1.  `@implSpec` 자바독 태그 등을 이용하여 문서화할 필요가 있으며,

    2. 에러가 발생하는 경우 디폴트 메소드를 오버라이드해야 한다.

- Object가 제공하는 equals, hashCode 등의 메소드는 디폴트 메소드로 제공할 수 없다.

- 인터페이스 A를 상속받는 인터페이스 B에서 A의 디폴트 메소드를 사용하고 싶지 않은 경우, B에서 해당 메소드를 추상 메소드로 재정의하는 방법이 있다.

- 다이아몬드 상속

  - 같은 이름을 가진 디폴트 메소드를 정의한 두 인터페이스를 동시에 상속받는 경우, 컴파일 에러가 발생한다.
  - 에러를 없애기 위해서는 상속받은 클래스/인터페이스에서 해당 메소드를 오버라이드해야 한다.

<br>

### static method (스태틱 메소드)

- `static` 키워드를 사용하여 정의한다.
- 인스턴스 생성 없이 클래스 단에서 직접 메소드를 호출할 수 있다.
- 주로 헬퍼 메소드나 유틸리티 메소드를 제공할 때 사용된다. (Util 클래스 참고)

<br>

### Java 8에서 도입된 디폴트 메소드로 인한 API 변화

#### 1. Collection 인터페이스

`void forEach(Consumer<? super T> action)`

- 순회를 쉽게 할 수 있다.
- 내부 메소드에 인자로 **Consumer** 인터페이스가 들어온다.

```java
// for ~ of
for (String name : names) {
    System.out.println(name);
}

// forEach문을 이용한 순회
names.forEach(name -> System.out.println(name));

// forEach문 + method reference(Ch01 참고)를 결합한 방식
names.forEach(System.out::println);
```

- 참고) Map은 key와 value를 함께 저장하기 때문에, forEach의 내부 메소드에 인자로 **BiConsumer** 인터페이스가 들어온다. 

```java
map.forEach((key, value) -> {
    System.out.println(key + value);
})
```

`Spliterator 인터페이스`

```java
public interface Spliterator<T> {
    
    boolean tryAdvance(Consumer<? super T> action);
    Spliterator<T> trySplit();
    ...
}
```

- 순회를 쉽게 할 수 있다.
- **tryAdvance()**는 Iterator의 **hasNext()**와 유사하다. 더 이상 순회할 값이 없을 때까지 다음 값을 순회한다.

```java
Spliterator<String> spliterator = names.spliterator();

while (spliterator.tryAdvance(System.out::println));   // 이름 차례대로 출력
```

- **trySplit()** 메소드는 spliterator의 앞쪽 절반을 꺼내 새로운 spliterator를 만들어 리턴한다.

```java
List<String> names = new ArrayList<>();

names.add("karina");
names.add("winter");
names.add("ningning");
names.add("giselle");

Spliterator<String> spliterator = names.spliterator();

// 요소의 절반을 spliterator에서 꺼내 spliterator1을 만든다.
Spliterator<String> spliterator1 = spliterator.trySplit();

while (spliterator.tryAdvance(System.out::println));   // ningning, giselle 출력
System.out.println("======== 구분선 ========");
while (spliterator1.tryAdvance(System.out::println));  // karina, winter 출력
```

`stream`

- 챕터 3에서 자세히 살펴볼 예정

`removeIf(Predicate<? super E> filter)`

- 내부 메소드를 만족하는 요소를 제거한다.

<br>

#### 2. Comparator 인터페이스

`reversed()`

- 정렬 순서를 반대로 만든다.

`thenComparing()`

`static reverseOrder() / naturalOrder()`

`static nullsFirst() / nullsLast()`

`static comparing()` 

<br>

#### **디폴트 메소드 도입의 의의**

- 인터페이스를 구현하는 클래스는 인터페이스의 모든 추상 메소드를 오버라이드해야 한다. 인터페이스의 추상 메소드가 많아질수록, 오버라이드할 메소드는 점점 더 많아진다.
- 그렇다면 인터페이스에서 원하는 메소드만 오버라이드할 수는 없을까? 인터페이스를 구현하는 추상 클래스를 만들고, 다시 그 추상 클래스를 상속하면 된다. 인터페이스의 추상 메소드들은 이미 추상 클래스에서 오버라이드했기 때문에, 다시 그 추상 클래스를 상속하는 클래스에서는 오버라이드할 필요가 없다.
- 그런데 인터페이스와 달리 추상 메소드는 다중 상속이 불가능하므로, 여러 인터페이스에 대하여 위와 같은 방식을 활용할 수는 없다.
- 그런데 Java 8에서 디폴트 메소드가 도입되면서 문제가 해결되었다. 
  	1) 디폴트 메소드는 오버라이드할 필요가 없으므로 원하는 메소드만 오버라이드할 수 있으며
  	2) 인터페이스는 다중 구현이 가능하므로 여러 인터페이스를 동시에 구현할 수 있다.


