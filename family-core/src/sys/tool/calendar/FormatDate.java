package sys.tool.calendar;

import java.util.Date;

public final class FormatDate
{
  public static String CurDateTimeToString()
  {
    String[] month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    String[] dstr = new Date().toString().split(" ");

    String strdate = dstr[5] + "-";
    for (int i = 0; i < month.length; i++) {
      if (month[i].equalsIgnoreCase(dstr[1])) {
        if (i < 9)
          strdate = strdate + "0";
        strdate = strdate + Integer.toString(i + 1) + "-";
        break;
      }
    }
    strdate = strdate + dstr[2];
    strdate = strdate + " " + dstr[3];
    return strdate;
  }

  public static String CurDateHHToString()
  {
    return CurDateTimeToString().substring(0, 13);
  }

  public static String CurDateHHMMToString()
  {
    return CurDateTimeToString().substring(0, 16);
  }

  public static String CurDateToYYYYMM()
  {
    return CurDateTimeToString().substring(0, 7);
  }

  public static String CurDateToYYMM()
  {
    return CurDateTimeToString().substring(2, 7).replace("-", "");
  }

  public static String CurDateToYYMMDD()
  {
    return CurDateTimeToString().substring(2, 10).replace("-", "");
  }

  public static String getCurYear()
  {
    return CurDateTimeToString().substring(0, 4);
  }

  public static String[] CurDateToLastMonthDays()
  {
    int[] intdate = GetDateInt();

    String[] last_month_days = new String[2];
    last_month_days[0] = (Integer.toString(intdate[0]) + "-");

    if (intdate[1] < 10)
    {
      int tmp53_52 = 0;
      String[] tmp53_51 = last_month_days; tmp53_51[tmp53_52] = (tmp53_51[tmp53_52] + "0");
    }
    int tmp76_75 = 0;
    String[] tmp76_74 = last_month_days; tmp76_74[tmp76_75] = (tmp76_74[tmp76_75] + Integer.toString(intdate[1]));

    last_month_days[1] = last_month_days[0];
    int tmp109_108 = 0;
    String[] tmp109_107 = last_month_days; tmp109_107[tmp109_108] = (tmp109_107[tmp109_108] + "-01");
    int tmp132_131 = 1;
    String[] tmp132_130 = last_month_days; tmp132_130[tmp132_131] = (tmp132_130[tmp132_131] + "-" + Integer.toString(Day.GetMonthDaysInt(intdate[0], intdate[1])));
    return last_month_days;
  }

  public static String[] LastMonthDays()
  {
    int[] intdate = GetDateInt();
    if (intdate[1] == 1) {
      intdate[1] = 12;
      intdate[0] -= 1;
    } else {
      intdate[1] -= 1;
    }

    String[] last_month_days = new String[2];
    last_month_days[0] = (Integer.toString(intdate[0]) + "-");
    if (intdate[1] < 10)
    {
      int tmp82_81 = 0;
      String[] tmp82_80 = last_month_days; tmp82_80[tmp82_81] = (tmp82_80[tmp82_81] + "0");
    }
    int tmp105_104 = 0;
    String[] tmp105_103 = last_month_days; tmp105_103[tmp105_104] = (tmp105_103[tmp105_104] + Integer.toString(intdate[1]));

    last_month_days[1] = last_month_days[0];
    int tmp138_137 = 0;
    String[] tmp138_136 = last_month_days; tmp138_136[tmp138_137] = (tmp138_136[tmp138_137] + "-01");
    int tmp161_160 = 1;
    String[] tmp161_159 = last_month_days; tmp161_159[tmp161_160] = (tmp161_159[tmp161_160] + "-" + Integer.toString(Day.GetMonthDaysInt(intdate[0], intdate[1])));
    return last_month_days;
  }

  public static int[] GetDateInt()
  {
    int[] intdate = new int[6];

    String[] month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    String[] dstr = new Date().toString().split(" ");
    String[] hhmmss = dstr[3].split(":");

    intdate[0] = Integer.parseInt(dstr[5]);
    for (int i = 0; i < month.length; i++) {
      if (month[i].equalsIgnoreCase(dstr[1])) {
        intdate[1] = (i + 1);
        break;
      }
    }
    intdate[2] = Integer.parseInt(dstr[2]);

    intdate[3] = Integer.parseInt(hhmmss[0]);
    intdate[4] = Integer.parseInt(hhmmss[1]);
    intdate[5] = Integer.parseInt(hhmmss[2]);
    return intdate;
  }
}