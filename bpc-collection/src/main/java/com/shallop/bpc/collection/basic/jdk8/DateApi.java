package com.shallop.bpc.collection.basic.jdk8;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @since v2.2.0
 * Created by nkcoder on 1/31/16.
 */
public class DateApi {

    public void testInstantAndDuration() throws Exception {
        // 1. stats time
        Instant begin = Instant.now();
        begin.toEpochMilli();
        for (int i = 0; i < 10; i++) {
            System.out.println("i = " + i);
            TimeUnit.MILLISECONDS.sleep(10);
        }
        Instant end = Instant.now();
        Duration elapsed = Duration.between(begin, end);
        System.out.println("elapsed: " + elapsed.toMillis());

        // 2. arithmetic operation
        begin.plus(5, ChronoUnit.SECONDS);
        begin.minusMillis(50);
        begin.isBefore(Instant.now());

        // 3. duration
        elapsed.dividedBy(10).minus(Duration.ofMillis(10)).isNegative();
        elapsed.isZero();
        elapsed.plusHours(3);

    }

    public void testLocalDate() {
        LocalDate now = LocalDate.now();
        LocalDate today = LocalDate.of(2016, 1, 31);
        LocalDate today2 = LocalDate.of(2016, Month.JANUARY, 31);   // JANUARY = 1, ..., DECEMBER = 12

        // operations
        today2.getDayOfWeek().getValue();   // Monday = 1, ..., Sunday = 7
        LocalDate dayOfYear = Year.now().atDay(220);
        YearMonth april = Year.of(2016).atMonth(Month.APRIL);
        LocalDate nextMonth = LocalDate.of(2016, 1, 31).plusMonths(1);  // 2016-02-29

        // Period
        LocalDate fiveDaysLater = LocalDate.now().plusDays(5);
        Period period = LocalDate.now().until(fiveDaysLater).plusMonths(2);
        period.isNegative();

        // TemporalAdjusters
        LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY));
        LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
    }

    public void testLocalTime() {
        // LocalTime
        LocalTime.now().isBefore(LocalTime.of(16, 2, 1));
        LocalTime.now().plusHours(2).getHour();

        // LocalDateTime
        LocalDateTime.now().plusDays(3).minusHours(5).isAfter(LocalDateTime.of(2016, 1, 30, 10, 20, 30));
    }

    public void testZonedDateTime() {
        Set<String> zones = ZoneId.getAvailableZoneIds();
        System.out.println("zones: " + Arrays.toString(zones.toArray(new String[0])));

        ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).plusMonths(1).minusHours(3).isBefore(ZonedDateTime.now());

        ZonedDateTime nowOfShanghai = LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate();
        ZonedDateTime nowOfShanghai2 = Instant.now().atZone(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("UTC")).toInstant();
    }

    public void testFormattingAndParsing() {
        // 2016-01-31T15:39:31.481
        String time1 = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());

        // Jan 31, 2016 3:50:04 PM
        String time2 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(LocalDateTime.now());
        System.out.println("time: " + time2);

        // Sun 2016-01-31 15:50:04
        String time3 = DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        System.out.println("time: " + time3);

        // parse
        LocalDateTime.parse("2016-01-31 15:51:00-0400", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxx"));
        LocalDate.parse("2016-01-31");

    }
    

    public static void main(String[] args) throws Exception {
        DateApi dateApi = new DateApi();
//        dateApi.testInstantAndDuration();

//        dateApi.testLocalDate();

//        dateApi.testZonedDateTime();
        dateApi.testFormattingAndParsing();

    }
}
