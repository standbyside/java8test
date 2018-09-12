package examples.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class DateExamples {

  public static void dateToString() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    String date = sdf.format(new Date());
    System.out.println(date);
  }

  public static void stringToDate() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    String dateInString = "31-08-1982 10:20:56";
    Date date = sdf.parse(dateInString);
    System.out.println(date);
  }

  /**
   * Return value is 0 if both dates are equal.
   * Return value is greater than 0 , if Date is after the date argument.
   * Return value is less than 0, if Date is before the date argument.
   */
  public static void compareDate() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date1 = sdf.parse("2009-12-31");
    Date date2 = sdf.parse("2010-01-31");

    System.out.println("date1 : " + sdf.format(date1));
    System.out.println("date2 : " + sdf.format(date2));

    if (date1.compareTo(date2) > 0) {
      System.out.println("Date1 is after Date2");
    }
    if (date1.compareTo(date2) < 0) {
      System.out.println("Date1 is before Date2");
    }
    if (date1.compareTo(date2) == 0) {
      System.out.println("Date1 is equal to Date2");
    }

    if (date1.after(date2)) {
      System.out.println("Date1 is after Date2");
    }
    if (date1.before(date2)) {
      System.out.println("Date1 is before Date2");
    }
    if (date1.equals(date2)) {
      System.out.println("Date1 is equal Date2");
    }
  }
}
