package me.bygramm.java8study.Ch04;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {

    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();

        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        Optional<OnlineClass> springOptional = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        Optional<OnlineClass> jpaOptional = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("jpa"))
                .findFirst();

        boolean jpaPresent = jpaOptional.isPresent();
        System.out.println(jpaPresent);

        springOptional.ifPresent(oc -> System.out.println(oc.getTitle()));

        System.out.println("======== orElse ========");

        OnlineClass onlineClass1 = springOptional.orElse(createNewClass());
        System.out.println(onlineClass1.getTitle());

        System.out.println("======== orElseGet ========");

        OnlineClass onlineClass2 = springOptional.orElseGet(App::createNewClass);
        System.out.println(onlineClass2.getTitle());

        System.out.println("======== orElseThrow ========");

        // OnlineClass onlineClass3 = springOptional.orElseThrow(IllegalStateException::new);

        Optional<OnlineClass> onlineClass4 = springOptional
                .filter(OnlineClass::isClosed);

        // flatMap vs map
        Optional<Progress> progress = springOptional.flatMap(OnlineClass::getProgress);

        Optional<Optional<Progress>> progress1 = springOptional.map(OnlineClass::getProgress);
        Optional<Progress> progress2 = progress1.orElse(Optional.empty());


    }

    private static OnlineClass createNewClass() {
        System.out.println("creating new online class");
        return new OnlineClass(10, "New Class", false);
    }
    
}
