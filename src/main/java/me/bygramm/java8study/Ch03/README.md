# 3. Stream

> 배열/컬렉션에 저장된 요소를 하나씩 순회하면서 처리하는 기술

- Collection처럼 데이터를 담는 저장소가 아니라, 데이터를 받아서 일련의 연산을 처리하는 기능이다.

- Functional하다. 즉, 기존 데이터를 변경하지 않는다.

- 스트림으로 처리하는 데이터는 오직 한번만 처리한다.

- **병렬 처리**를 쉽게 할 수 있다.
- 병렬 처리란 멀티 스레드를 이용한 처리를 의미한다.
  - `Collection.parellelStream()` 메소드를 이용하면 쉽게 병렬 처리를 할 수 있다. 
  
- 주의) 병렬 처리를 한다고 반드시 속도가 더 빨라지는 것은 아니다. 스레드를 만드는 비용, context switch의 비용 등이 발생하기 때문이다.

```java
List<String> names = new ArrayList<>();

names.add("karina");
names.add("winter");
names.add("ningning");
names.add("giselle");

// paralletStream()을 통한 병행처리
// 요소와 스레드명을 함께 출력하도록 함.
List<String> collect2 = names.parallelStream().map(s -> {
    System.out.println(s + " " + Thread.currentThread().getName());
    return s.toUpperCase();
}).collect(Collectors.toList());

collect2.forEach(System.out::println);

// 출력 결과 => 각각 다른 스레드에서 처리됨.

// karina ForkJoinPool.commonPool-worker-9
// winter ForkJoinPool.commonPool-worker-5
// giselle ForkJoinPool.commonPool-worker-3
// ningning main

// collect2에는 멤버명이 대문자로 저장됨.

// KARINA
// WINTER
// NINGNING
// GISELLE
```

- Stream의 operation은 크게 2가지로 나눌 수 있다.
  - **중개 오퍼레이션**
    - Stream을 리턴한다.
    - lazy하다. 종료 오퍼레이션이 실행되기 전까지는 실행되지 않음을 의미한다.
    - `filter`, `map`, `limit`, `skip`, `sorted` 등
  - **종료 오퍼레이션**
    - Stream을 리턴하지 않는다.
    - `collect`, `allMatch`, `count`, `forEach`, `min`, `max` 등



### Stream API 활용 예시

```java
List<OnlineClass> springClasses = new ArrayList<>();

springClasses.add(new OnlineClass(1, "spring boot", true));
springClasses.add(new OnlineClass(2, "spring data jpa", true));
springClasses.add(new OnlineClass(3, "spring mvc", false));
springClasses.add(new OnlineClass(4, "spring core", false));
springClasses.add(new OnlineClass(5, "rest api development", false));

List<OnlineClass> javaClasses = new ArrayList<>();
javaClasses.add(new OnlineClass(6, "The Java, Test", true));
javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

List<List<OnlineClass>> keesunEvents = new ArrayList<>();
keesunEvents.add(springClasses);
keesunEvents.add(javaClasses);
```



- 스프링 수업 중에 spring이 들어간 수업의 제목만 모아서 List로 만들기

```java
List<String> classesWithSpringTitle = springClasses.stream()
        .filter(oc -> oc.getTitle().contains("spring"))
        .map(OnlineClass::getTitle)
        .collect(Collectors.toList());
```



- 두 수업 목록에 들어있는 모든 수업의 아이디 출력하기

```java
keesunEvents.stream()
        .flatMap(Collection::stream)
        .forEach(oc -> System.out.println(oc.getId()));
```



- 30부터 2씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개까지 출력하기

```java
Stream.iterate(30, i -> i + 2)
        .skip(10)   // 초반 10개 제외
        .limit(10)  // 최대 10개까지만 순회
        .forEach(System.out::println);
```

<br>

### 🔸 **연습문제 소개**🔸

Stream은 많은 연습을 통해 익숙해지는 것이 중요하다고 생각했는데, 마침 망나니개발자 MangKyu님이 제공해주신 Stream API 연습문제가 있었습니다.



블로그 포스트 : https://mangkyu.tistory.com/116?category=872426

깃헙 저장소 : https://github.com/MangKyu/stream-quiz



자동화된 테스트로 정답 여부를 확인할 수 있기 때문에, Stream API를 연습하고 싶은 분들이라면 활용하면 좋을 듯 합니다. 개인적으로도 문제를 풀면서 많은 도움을 받았습니다.



**문제 풀이 중 기록할 만한 부분들**

- String 스트림 개수 세기 (String을 key, 개수를 value로 하는 HashMap 만들기)

```java
.collect(Collectors.toMap(s -> s, s -> 1, (prev, curr) -> curr += prev));
```

- Integer 스트림 합 구하기

```java
// 방법 1
.reduce(0, Integer::sum);

// 방법 2
.mapToInt(num -> num)
.sum();
```

- String 스트림 콤마(`,`)로 구분한 문자열로 합치기

```java
.collect(Collectors.joining(", "))
```

- **boxed()**
  - 원사 타입(ex. `int`)을 wrapper 타입(ex. `Integer`)으로 감싸서 반환한다.



