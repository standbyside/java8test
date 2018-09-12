package examples.datetime;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class OtherExamples {

  /**
   * 【Duration】 – Measures time in seconds and nanoseconds.
   * A java.time.Duration example to find out difference seconds between two LocalDateTime
   */
  public static void duration() {
    // Creating Durations
    Duration oneHours = Duration.ofHours(1);
    System.out.println(oneHours.getSeconds() + " seconds");

    Duration oneHours2 = Duration.of(1, ChronoUnit.HOURS);
    System.out.println(oneHours2.getSeconds() + " seconds");

    // Test Duration.between
    System.out.println("\n--- Duration.between --- ");

    LocalDateTime oldDate = LocalDateTime.of(2016, Month.AUGUST, 31, 10, 20, 55);
    LocalDateTime newDate = LocalDateTime.of(2016, Month.NOVEMBER, 9, 10, 21, 56);

    System.out.println(oldDate);
    System.out.println(newDate);

    // count seconds between dates
    Duration duration = Duration.between(oldDate, newDate);
    System.out.println(duration.getSeconds() + " seconds");
  }

  /**
   * 【Period】 – Measures time in years, months and days.
   * A java.time.Period example to find out differently (years, months, days) between two LocalDates
   */
  public static void period() {
    Period tenDays = Period.ofDays(10);
    System.out.println(tenDays.getDays());

    Period oneYearTwoMonthsThreeDays = Period.of(1, 2, 3);
    System.out.println(oneYearTwoMonthsThreeDays.getYears());
    System.out.println(oneYearTwoMonthsThreeDays.getMonths());
    System.out.println(oneYearTwoMonthsThreeDays.getDays());

    LocalDate oldDate = LocalDate.of(1982, Month.AUGUST, 31);
    LocalDate newDate = LocalDate.of(2016, Month.NOVEMBER, 9);

    System.out.println(oldDate);
    System.out.println(newDate);

    // check period between dates
    Period period = Period.between(oldDate, newDate);

    System.out.print(period.getYears() + " years,");
    System.out.print(period.getMonths() + " months,");
    System.out.print(period.getDays() + " days");
  }

  public static void chronoUnit() {
    LocalDateTime oldDate = LocalDateTime.of(1982, Month.AUGUST, 31, 10, 20, 55);
    LocalDateTime newDate = LocalDateTime.of(2016, Month.NOVEMBER, 9, 10, 21, 56);

    System.out.println(oldDate);
    System.out.println(newDate);

    // count between dates
    System.out.println(ChronoUnit.YEARS.between(oldDate, newDate) + " years");
    System.out.println(ChronoUnit.MONTHS.between(oldDate, newDate) + " months");
    System.out.println(ChronoUnit.WEEKS.between(oldDate, newDate) + " weeks");
    System.out.println(ChronoUnit.DAYS.between(oldDate, newDate) + " days");
    System.out.println(ChronoUnit.HOURS.between(oldDate, newDate) + " hours");
    System.out.println(ChronoUnit.MINUTES.between(oldDate, newDate) + " minutes");
    System.out.println(ChronoUnit.SECONDS.between(oldDate, newDate) + " seconds");
    System.out.println(ChronoUnit.MILLIS.between(oldDate, newDate) + " milis");
    System.out.println(ChronoUnit.NANOS.between(oldDate, newDate) + " nano");
  }

  /**
   * https://docs.oracle.com/javase/8/docs/api/java/time/temporal/TemporalAdjusters.html
   */
  public static void temporalAdjusters() {
    LocalDate localDate = LocalDate.now();
    System.out.println("current date : " + localDate);

    LocalDate with = localDate.with(TemporalAdjusters.firstDayOfMonth());
    System.out.println("firstDayOfMonth : " + with);

    LocalDate with1 = localDate.with(TemporalAdjusters.lastDayOfMonth());
    System.out.println("lastDayOfMonth : " + with1);

    LocalDate with2 = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    System.out.println("next monday : " + with2);

    LocalDate with3 = localDate.with(TemporalAdjusters.firstDayOfNextMonth());
    System.out.println("firstDayOfNextMonth : " + with3);
  }
}
