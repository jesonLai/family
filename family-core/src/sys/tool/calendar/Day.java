package sys.tool.calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Day
{
  public static int poorDays(String dateTime, String dateTime2)
    throws ParseException
  {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    long to = df.parse(dateTime).getTime();
    long from = df.parse(dateTime2).getTime();
    int day = (int)((to - from) / 86400000L);
    return day;
  }

  public static long[] getDateChar(Date date)
  {
    long[] dateLong = new long[4];
    long l = new Date().getTime() - date.getTime();
    long day = l / 86400000L;
    long hour = l / 3600000L - day * 24L;
    long min = l / 60000L - day * 24L * 60L - hour * 60L;
    long s = l / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
    dateLong[0] = day;
    dateLong[1] = hour;
    dateLong[2] = min;
    dateLong[3] = s;
    return dateLong;
  }

  public static Date getDateByBeforeDays(String dateString, int beforeDays)
    throws ParseException
  {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date inputDate = dateFormat.parse(dateString);

    Calendar cal = Calendar.getInstance();
    cal.setTime(inputDate);

    int inputDayOfYear = cal.get(6);
    cal.set(6, inputDayOfYear + beforeDays);

    return cal.getTime();
  }

  public static int GetMonthDaysInt(int yy, int mm)
  {
    return Integer.parseInt(GetMonthDays(yy, mm));
  }

  public static String GetMonthDays(int yy, int mm)
  {
    String days = "31";
    if ((mm == 4) || (mm == 6) || (mm == 9) || (mm == 11)) days = "30";
    if (mm == 2) {
      days = "28";
      if (yy / 4 * 4 == yy) days = "29";
    }
    return days;
  }
}