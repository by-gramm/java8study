# 5. Date와 Time



### Why : Date-Time API의 등장 배경

Java 8 전의 Date를 나타내는 방식에는 여러 문제가 있다.

```java
Date date = new Date();
Calendar calendar = new GregorianCalendar();
SimpleDateFormat dateFormat = new SimpleDateFormat();
```

- 작명의 문제가 있다.
  - ex. Date()가 날짜뿐 아니라 시간까지 다룬다.
- java.util.Date 클래스는 **mutable**하기 때문에 **thread-safe하지 않다.**
- 버그가 발생할 여지가 많다.
  - ex. GregorianCalendar()의 month가 0부터 시작한다.



위의 문제들을 해결하기 위해 Java 8에서는 아래의 디자인 원칙을 가진 Date-Time API가 추가되었다.

- Clear
- Fluent
  - 대부분의 메소드가 null값을 허용하지 않기에, 메소드를 연속해서 쓸 수 있으며, 코드가 이해하기 쉬워진다.
- **Immutable**
  - Date-Time API의 클래스 대부분은 immutable하다. 기존 값은 불변이므로, 값을 수정할 경우 새로운 객체를 만든다. 따라서 **thread-safe**하다.
- Extensible

<br>

### What : Date-Time API의 종류

**machine time**

- EPOCK (1970.1.1 00:00:00)부터 현재까지의 timestamp
- `Instant.now()`
  - 현재 UTC (GMT)를 리턴한다.
- `Duration.between(Temporal startInclusive, Temporal endExclusive)`
  - 두 Temporal(machine time) 사이의 기간을 계산한다.

**human time**

- 연, 월, 일, 시, 분, 초 등 우리가 흔히 사용하는 시간 표현
- `LocalDateTime.now()`
  - 현재 시스템 Zone에 해당하는 일시를 리턴한다.
- `LocalDateTime.of(int year, Month month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond)`
  - 로컬의 특정 일시를 리턴한다.
  - hour 아래로는 생략 가능하며, 그 경우 모두 0으로 설정된다.
- `ZonedDateTime.of(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond, ZoneId zone)`
  - 특정 Zone의 특정 일시를 리턴한다.

- `Period.between(LocalDate startDateInclusive, LocalDate endDateExclusive)`
  - 두 LocalDate(human time) 사이의 기간을 계산한다.

**parsing / formatting**

- [미리 정의된 formatter](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html)를 사용하거나, 커스텀 formatter를 만들어 사용할 수 있다.

```java
LocalDateTime now = LocalDateTime.now();

// formatting
DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
System.out.println("format = " + now.format(MMddyyyy));

// parsing
LocalDate parse = LocalDate.parse("02/22/2002", MMddyyyy);
System.out.println("parse = " + parse);
```

