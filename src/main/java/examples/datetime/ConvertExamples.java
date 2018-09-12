package examples.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.MinguoDate;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class ConvertExamples {

  public static void calendarToDate() {
    Calendar calendar = Calendar.getInstance();
    Date date = calendar.getTime();
    System.out.println(date);
  }

  public static void dateToCalendar() throws ParseException {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
  }

  public static void instantToLocalDateTime() {
    Instant instant = Instant.now();
    System.out.println("Instant : " + instant);

    // Convert instant to LocalDateTime, no timezone, add a zero offset / UTC+0
    LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    System.out.println("LocalDateTime : " + ldt);
  }

  public static void localDateTimeToInstant() {
    // Hard code a date time
    LocalDateTime dateTime = LocalDateTime.of(2016, Month.AUGUST, 18, 6, 17, 10);
    System.out.println("LocalDateTime : " + dateTime);

    // Convert LocalDateTime to Instant, UTC+0
    Instant instant = dateTime.toInstant(ZoneOffset.UTC);
    System.out.println("Instant : " + instant);
  }

  public static void dateToJavaTime() {
    // Asia/Kuala_Lumpur +8
    ZoneId defaultZoneId = ZoneId.systemDefault();
    System.out.println("System Default TimeZone : " + defaultZoneId);

    // toString() append +8 automatically.
    Date date = new Date();
    System.out.println("date : " + date);

    // 1. Convert Date -> Instant
    Instant instant = date.toInstant();
    // Zone : UTC+0
    System.out.println("instant : " + instant);

    // 2. Instant + system default time zone + toLocalDate() = LocalDate
    LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
    System.out.println("localDate : " + localDate);

    // 3. Instant + system default time zone + toLocalDateTime() = LocalDateTime
    LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
    System.out.println("localDateTime : " + localDateTime);

    // 4. Instant + system default time zone = ZonedDateTime
    ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
    System.out.println("zonedDateTime : " + zonedDateTime);

  }

  public static void javaTimeToDate() {
    // Asia/Kuala_Lumpur +8
    ZoneId defaultZoneId = ZoneId.systemDefault();
    System.out.println("System Default TimeZone : " + defaultZoneId);

    LocalDate localDate = LocalDate.of(2016, 8, 19);
    Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    System.out.println("\n1. LocalDate -> Date");
    System.out.println("localDate : " + localDate);
    System.out.println("date : " + date);

    LocalDateTime localDateTime = LocalDateTime.of(2016, 8, 19, 21, 46, 31);
    Date date2 = Date.from(localDateTime.atZone(defaultZoneId).toInstant());
    System.out.println("\n2. LocalDateTime -> Date");
    System.out.println("localDateTime : " + localDateTime);
    System.out.println("date2 : " + date2);

    ZonedDateTime zonedDateTime = localDateTime.atZone(defaultZoneId);
    Date date3 = Date.from(zonedDateTime.toInstant());
    System.out.println("\n3. ZonedDateTime -> Date");
    System.out.println("zonedDateTime : " + zonedDateTime);
    System.out.println("date3 : " + date3);
  }

  public static void localDateToMinguoDate() {
    LocalDate localDate = LocalDate.of(1912, 1, 1);
    MinguoDate minguo = MinguoDate.from(localDate);
    System.out.println("LocalDate : " + localDate);
    System.out.println("MinguoDate : " + minguo);
  }

  public static void minguoDateToLocalDate() {
    MinguoDate minguo2 = MinguoDate.of(105, 8, 24);
    LocalDate localDate2 = LocalDate.from(minguo2);
    System.out.println("MinguoDate : " + minguo2);
    System.out.println("LocalDate : " + localDate2);
  }
}
