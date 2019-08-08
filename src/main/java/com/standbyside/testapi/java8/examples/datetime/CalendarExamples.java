package com.standbyside.testapi.java8.examples.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarExamples {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");

  public static void example1() {
    Calendar calendar = new GregorianCalendar(2013, 1, 28, 13, 24, 56);

    System.out.println(DATE_FORMAT.format(calendar.getTime()));

    System.out.println(calendar.get(Calendar.YEAR));
    // Jan = 0, dec = 11
    System.out.println(calendar.get(Calendar.MONTH));
    System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
    System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
    System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
    System.out.println(calendar.get(Calendar.WEEK_OF_MONTH));

    // 12 hour clock
    System.out.println(calendar.get(Calendar.HOUR));
    // 24 hour clock
    System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
    System.out.println(calendar.get(Calendar.MINUTE));
    System.out.println(calendar.get(Calendar.SECOND));
    System.out.println(calendar.get(Calendar.MILLISECOND));
  }

  /**
   * Set a date manually
   */
  public static void example2() {
    Calendar calendar = new GregorianCalendar(2013, 1, 28, 13, 24, 56);
    System.out.println(DATE_FORMAT.format(calendar.getTime()));

    // update a date
    calendar.set(Calendar.YEAR, 2014);
    calendar.set(Calendar.MONTH, 11);
    calendar.set(Calendar.MINUTE, 33);
    System.out.println(DATE_FORMAT.format(calendar.getTime()));
  }

  /**
   * Add or subtract from a date
   */
  public static void example3() {
    Calendar calendar = new GregorianCalendar(2013, 10, 28);
    System.out.println(DATE_FORMAT.format(calendar.getTime()));

    // add one month
    calendar.add(Calendar.MONTH, 1);
    System.out.println(DATE_FORMAT.format(calendar.getTime()));

    // subtract 10 days
    calendar.add(Calendar.DAY_OF_MONTH, -10);
    System.out.println(DATE_FORMAT.format(calendar.getTime()));
  }

  /**
   * Compare
   *
   * @throws ParseException
   */
  public static void example4() throws ParseException {
    Date date1 = DATE_FORMAT.parse("2009-12-31 00:00:00");
    Date date2 = DATE_FORMAT.parse("2010-01-31 00:00:00");

    System.out.println("date1 : " + DATE_FORMAT.format(date1));
    System.out.println("date2 : " + DATE_FORMAT.format(date2));

    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date1);
    cal2.setTime(date2);

    if (cal1.after(cal2)) {
      System.out.println("Date1 is after Date2");
    }

    if (cal1.before(cal2)) {
      System.out.println("Date1 is before Date2");
    }

    if (cal1.equals(cal2)) {
      System.out.println("Date1 is equal Date2");
    }
  }
}
