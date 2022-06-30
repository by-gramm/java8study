# 6. CompletableFuture

### Java 동시성 프로그래밍

**스레드를 만드는 2가지 방법**

1. Thread 클래스 상속받기

```java
public static void main(String[] args) {
    MyThread myThread = new MyThread();
    myThread.start();
}

static class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("MyThread: " + Thread.currentThread().getName());
    }
}
```

2. Runnable (+람다) 활용하기

```java
public static void main(String[] args) {
    Thread myThread = new Thread(() -> {
        System.out.println("MyThread: " + Thread.currentThread().getName());
    });
    
    myThread.start();
}
```

**스레드 주요 기능**

- `sleep`
  - 현재 스레드를 멈추게 한다.
- `interrupt`
  - 다른 스레드를 깨워서 **interruptedException**을 발생시킨다. 
  - 주의할 점은, `interrupt` 메서드 자체가 스레드를 종료시키지는 않는다는 점이다. `interrupt`로 스레드를 종료시키기 위해서는 아래와 같이 예외 처리를 해주면 된다.

```java
Thread thread = new Thread(() -> {
    while (true) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            return;
        }
    }
});

thread.interrupt();

// interrupt() 호출시, InterruptedException이 발생하여 catch문 내의 코드가 실행된다.
// null을 return하면 while문을 빠져나와서, 스레드가 종료한다.
```

- `join`
  - 다른 스레드가 끝날 때까지 기다린다.

<br>

### Executors

**사용 이유**

- Thread나 Runnable은 스레드를 low-level에서 다룬다.

- 하지만 수많은 스레드를 직접 관리하기 어려우므로, Executors라는 API에 스레드를 만들고 관리하는 작업을 위임할 수 있다.
- Executors는 스레드를 만들고, 스레드를 관리하고, 작업을 처리 및 실행한다.

**주요 기능**

- `Executors.newSingleThreadExecutor()`
  - **싱글 스레드를 사용**하는 ExecutorService를 생성한다.
- `Executors.newFixedThreadPool(n)`
  - **스레드를 n개 사용**하는 ExecutorService를 생성한다.
- `Executors.newSingleThreadScheduledExecutor()`
  - **ScheduledExecutorService**를 생성한다.
  - ScheduledExecutorService는 특정 시간 혹은 특정 주기에 맞추어 작업을 처리한다.
- `ExecutorService.submit()`
  - 괄호 안의 코드를 실행시킨다.

- `ExecutorService.shutdown()`
  - **graceful shutdown** : 스레드가 할 일을 마치고 종료시킨다.

- `ExecutorService.shutdownNow()`
  - **hard shutdown** : 스레드의 현재 작업을 무시하고 종료시킨다.

**한계**

- **Runnable**은 리턴값이 없다. 
- 따라서 스레드에서 어떤 작업을 수행한 뒤, 작업의 결과에 해당하는 값을 받아올 수 없다.
- 이러한 문제를 해결하기 위해 등장한 것이 **Callable**이다.
- Callable은 Runnable과 유사하지만, Runnable과 달리 **리턴 타입이 존재한다.**

<br>

### Future

```java
public static void main(String[] args) throws {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Future<String> submit = executorService.submit(hello);
}
```

- `submit` 메소드의 인자로 Callable 객체를 넘겨주었다.
- 이때 리턴값은 Callable의 반환 타입(String)의 **Future**가 된다.

- Future는 비동기적인 작업의 현재 상태를 조회하거나 결과를 가져올 수 있다.

**Future API**

- `get()`
  - Callable이 반환하는 값을 가져온다.
  - **Blocking Call** : get 메소드를 만나면, 값을 가져올 때까지 아래 코드를 이어서 실행하지 않는다.
    - cf) [Blocking vs NonBlocking 참고](https://github.com/by-gramm/TIL/blob/master/computer_science/blocking_io_vs_nonblocking_io.md)
- `boolean isDone()`
  - 작업이 끝난 경우 true, 아니면 false를 리턴한다.
  - **cancel()**에 의해 작업이 중단된 경우라도 true를 리턴한다.
- `boolean cancel(boolean mayInterruptIfRunning)`
  - 인자로 true를 주면 현재 진행중인 작업을 interrupt하여 취소시킨다.
  - 인자로 false를 주면 현재 진행중인 작업 완료를 기다린다.
    - 단, 이 경우에도 `get()`의 작업의 결과값을 가져올 수 없게 된다.
  - 이미 작업이 완료된 경우 false, 그렇지 않은 경우 true를 리턴한다.
- `invokeAll()`
  - 여러 작업을 동시에 실행한다.
  - 모든 작업이 끝날 때까지 기다린다.
- `invokeAny()`
  - 제일 짧게 걸리는 작업이 끝나면 종료한다.
  - Blocking Call이다.

<br>

### CompletableFuture

> Java에서 비동기 프로그래밍을 가능하게 하는 인터페이스

**사용 이유**

- Future로는 하기 어려운 작업들이 존재한다.
  - 예외 처리용 API가 없다.
  - Future를 외부에서 완료시킬 수 없다. (취소하거나, 타임아웃 설정은 가능)
  - 여러 Future를 조합할 수 없다.
  - Future의 리턴값을 가지고 수행하는 작업은 `get()` 이후에 작성되어야 한다.
- 위와 같은 Future의 한계를 극복하기 위해 CompletableFuture가 등장했다.

**CompletableFuture의 특징**

- 외부에서 명시적으로 완료시킬 수 있다. (이름에 Completable이 붙은 이유)
- 명시적으로 Executor를 선언할 필요가 없다.
  - 이것이 가능한 이유는 **ForkJoinPool** 때문이다. ForkJoinPool은 작업의 크기에 따라 분할(fork)한 뒤, 처리된 각 작업을 합쳐서(join) 리턴하는 방식으로 동작한다. 
  - 따로 Executor를 선언하지 않아도 ForkJoinPool의 Common Pool을 사용하기 때문에, 별도의 스레드 풀을 만들지 않아도 되는 것이다.
  - 물론 별도의 스레드 풀을 생성해서 사용할 수도 있다.

```java
// Future => Executor 선언 필요
ExecutorService executorService = Executors.newSingleThreadExecutor();

Callable<String> hello = () -> {
    Thread.sleep(2000L);
    return "Hello";
};

Future<String> submit = executorService.submit(hello);

// CompletableFuture => Executor 선언 필요 X
CompletableFuture<String> future = new CompletableFuture<>();
future.complete("bygramm");
```

<br>

### **CompletableFuture API**

**비동기로 작업 실행하기**

- `runAsync()`
  - 리턴값이 없는 경우

- `supplyAsync()`
  - 리턴값이 있는 경우

**콜백 제공하기**

- `thenApply()`
  - 작업의 리턴값을 받아 새로운 값을 반환한다.

- `thenAccept()`
  - 작업의 리턴값을 받아 로직을 처리한다. (리턴값 X)
- `thenRun()`
  - 작업의 리턴값 없이 로직을 처리한다. (리턴값 X)

**조합하기**

- `thenCompose()`
  - 연관관계가 있는 두 작업이 서로 이어서 실행하도록 조합한다.

- `thenCombine()`
  - 두 작업을 독립적으로 수행하고, 두 작업이 모두 완료됐을 때 콜백 함수를 실행한다.
- `allOf()`
  - 2개 이상의 모든 작업을 합쳐서 수행하고, 각 결과에 콜백 함수를 실행한다.
- `anyOf()`
  - 여러 작업 중 가장 먼저 끝난 작업의 결과에 콜백 함수를 실행한다.

**예외 처리**

- `exceptionally(Function)`
  - 예외 발생시 실행된다.
- `handle(BiFunction)`
  - 예외 발생 여부에 상관없이 실행된다.
  -  첫번째 인자로 CompletableFuture가 정상 실행 후 반환한 결과값, 두번째 인자로 실행중 발생한 exception을 받는다.
