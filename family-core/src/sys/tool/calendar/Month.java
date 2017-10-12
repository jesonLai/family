package sys.tool.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Month
{
  public static int poorMonths(String dateTime, String dateTime2)
    throws ParseException
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();

    c1.setTime(sdf.parse(dateTime));
    c2.setTime(sdf.parse(dateTime2));

    int months = c2.get(2) - c1.get(2);

    return months == 0 ? 1 : Math.abs(months);
  }

  public static int getMonthCount(Date start, Date end)
  {
    if (start.after(end)) {
      Date t = start;
      start = end;
      end = t;
    }
    Calendar startCalendar = Calendar.getInstance();
    startCalendar.setTime(start);
    Calendar endCalendar = Calendar.getInstance();
    endCalendar.setTime(end);
    Calendar temp = Calendar.getInstance();
    temp.setTime(end);
    temp.add(5, 1);

    int year = endCalendar.get(1) - startCalendar.get(1);
    int month = endCalendar.get(2) - startCalendar.get(2);

    if ((startCalendar.get(5) == 1) && (temp.get(5) == 1))
      return year * 12 + month + 1;
    if ((startCalendar.get(5) != 1) && (temp.get(5) == 1))
      return year * 12 + month;
    if ((startCalendar.get(5) == 1) && (temp.get(5) != 1)) {
      return year * 12 + month;
    }
    return year * 12 + month - 1 < 0 ? 0 : year * 12 + month;
  }
}