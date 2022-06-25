# 4. Optional

> 하나의 값이 들어있을 수도 없을 수도 있는 컨테이너



### Why : Optional의 등장 배경

> NullPointerException을 보다 쉽게 예방하기 위해서

아래와 같이 name, grade 필드를 가지는 Major 클래스와 id, name, Major 필드를 가지는 Student 클래스가 있다고 하자.

```java
// Major.java (Getter, Setter 생략)
public class Major {
    private String name;
    
    private Integer grade;
    
    public Major(String name, Integer grade) {
        this.name = name;
        this.grade = grade;
    }
}

// Student.java (Getter, Setter 생략)
public class Student {
    private Integer id;

    private String name;

    private Major major;

    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

- 아래 코드는 **NullPointerException**을 발생시킨다. student1.getMajor()가 null이고, null 객체에서 다시 getName() 메소드를 호출했기 때문이다.

```java
Student student1 = Student(1, "bygramm");
String gradeName = student1.getMajor().getName();
```

- 기존에는 아래와 같은 방식으로 NullPointerException을 방지했다. 하지만 null이 발생할 수 있는 모든 경우에 예외 처리를 하면 코드가 복잡해지며, 실수로 예외 처리를 하지 않을 가능성도 많다. 

```java
Major major = student1.getMajor();
if (major != null) {
    // 예외 처리
}
```

- Java 8에서는 null에 대한 처리를 보다 쉽게 하기 위해 **Optional**이 등장했다.

<br>

### How : Optional 사용시 주의 사항

- **리턴값으로만 쓰는 것이 권장된다.**
  - 매개변수 타입? Optional을 쓰지 않을 때보다 더욱 복잡해진다.
  - Map의 key 타입? Map의 key는 not null이어야 하므로 Optional을 쓰지 않아야 한다.
  - 인스턴스 필드 타입? 설계상으로 좋지 않다.

- Optional을 리턴하는 메소드에서 null을 리턴하지 않아야 한다.
  - 애초에 null 체크를 하지 않기 위해 Optional을 쓰기 때문이다.
  - 리턴할 값이 없다면 null 대신 **Optional.empty()**를 리턴한다.
- Optional에 담길 값이 원시 타입인 경우, 원시 타입용 Optional을 쓰는 것이 좋다.
  - **OptionalInt, OptionalLong, OptionalDouble** 등
  - 불필요한 boxing, unboxing을 방지할 수 있기 때문이다.
  - 아래 코드에서 Optional을 사용한 경우, int형 값 100은 Integer라는 Wrapper class로 감싸진(**boxing**) 이후, get()으로 100이라는 값을 꺼낼 때 다시 **unboxing**이 이루어진다.
  - 반면 OptionalInt를 사용한 경우, boxing이나 unboxing이 이루어지지 않는다.

```java
// Optional
Optional<Integer> integer = Optional.of(100);   // Integer로 boxing
int price = integer.get();                      // int로 unboxing

// OptionalInt
OptionalInt optionalInt = OptionalInt.of(38);   // boxing X
int price = optionalInt.getAsInt();             // unboxing X
```

- collections, maps, streams, arrays, optionals 등 **컨테이너 타입은 Optional로 감싸지 않아야 한다.**
  - 그 자체로 빈 값에 대한 처리가 가능하기 때문이다.

<br>

### What : Optional API의 종류

**1. Optional 만들기**

- `Optional.of`
  - 값으로 null이 들어가는 경우 **NullPointerException**이 발생한다.
  - Optional.ofNullable과 달리 null 여부를 따로 체크하지 않으므로 더 효율적이다. 따라서 null이 아님이 확실한 객체인 경우에 사용하면 좋다.

- `Optional.ofNullable`

- `Optional.empty()`
  - 빈 값을 나타내는데 사용한다.

**2. 값이 있는지 확인하기**

- `isPresent()`
  - 값이 있으면 true, 없으면 false
- `isEmpty()` (Java 11부터 제공)
  - 값이 있으면 false, 없으면 true

**3. 값 가져오기 (+ 검사하기)** 

- `get()`
  - 값이 있으면 가져오고, 없으면 **NoSuchElementException**을 발생시킨다.
  - get()보다는 아래의 API들을 사용하는 것이 권장된다.
- `ifPresent()`
  - 값이 있으면 괄호 안의 식을 수행한다.
- `orElse()` / `orElseGet(Supplier)`
  - 값이 있으면 가져오고, 없으면 괄호 안에 선언한 내용을 반환한다.
  - 차이 1 : orElse는 인자로 어떤 타입이든 올 수 있지만, orElseGet()은 인자로 Supplier 타입의 인터페이스만 올 수 있다.
  - **차이 2 : orElse()의 인자는 값이 있는 경우에도 호출되지만, orElseGet()의 인자는 값이 있으면 호출되지 않는다.**
- `orElseThrow()`
  - 값이 있으면 가져오고, 없으면 에러를 발생시킨다.

**4. 값 걸러내거나 변환하기**

- `Optional filter(Predicate)`
- `Optional map(Function)`
  - 각각 Stream API의 filter, map과 유사한 방식
- `Optional flatMap(Function)`
  - Optional 안에 다시 Optional이 들어있는 경우 내부 원소값을 한번에 꺼낸다.

```java
// flatMap
Optional<Progress> progress = springOptional.flatMap(OnlineClass::getProgress);

// map
Optional<Optional<Progress>> progress1 = springOptional.map(OnlineClass::getProgress);
Optional<Progress> progress2 = progress1.orElse(Optional.empty());
```

