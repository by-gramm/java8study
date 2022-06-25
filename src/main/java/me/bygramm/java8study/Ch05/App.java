package me.bygramm.java8study.Ch05;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class App {

    public static void main(String[] args) throws InterruptedException {

        Instant instant = Instant.now();
        System.out.println(instant);        // 기준시 UTC, GMT

        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTime);  // 서울 기준 시간 (UTC+9)

        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now);

        LocalDateTime kongDay = LocalDateTime.of(2022, Month.FEBRUARY, 22, 22, 22, 22);
        System.out.println("kongDay = " + kongDay);

        ZonedDateTime nowInSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println("nowInSeoul = " + nowInSeoul);

        // 기간 구하기
        LocalDate today = LocalDate.now();
        LocalDate thisYearKongDay = LocalDate.of(2022, Month.FEBRUARY, 22);

        long between = ChronoUnit.DAYS.between(thisYearKongDay, today);
        System.out.println("between = " + between);

        Period until = thisYearKongDay.until(today);
        System.out.println("until = " + until.getDays());

        LocalDateTime after10Seconds = now.plus(10, ChronoUnit.SECONDS);
        Duration between1 = Duration.between(now, after10Seconds);
        System.out.println("between1 = " + between1.getSeconds());

        // 포맷팅과 파싱
        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println("format = " + now.format(MMddyyyy));

        LocalDate parse = LocalDate.parse("02/22/2002", MMddyyyy);
        System.out.println("parse = " + parse);

    }
}
