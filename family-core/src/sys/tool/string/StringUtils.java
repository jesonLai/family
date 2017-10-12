package sys.tool.string;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public final class StringUtils
{
  public static String str;
  public static final String EMPTY_STRING = "";
  private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

  public static boolean isNull(Object obj) {
    return obj == null;
  }

  public static boolean isNotNull(Object obj) {
    return !isNull(obj);
  }

  public static boolean isEmpty(Object obj)
  {
    if (obj == null)
    {
      return true;
    }
    if ((obj instanceof CharSequence))
    {
      return ((CharSequence)obj).length() == 0;
    }
    if ((obj instanceof Collection))
    {
      return ((Collection)obj).isEmpty();
    }
    if ((obj instanceof Map))
    {
      return ((Map)obj).isEmpty();
    }
    if (obj.getClass().isArray())
    {
      return Array.getLength(obj) == 0;
    }
    return false;
  }
  public static boolean isNotEmpty(Object obj) {
    return !isEmpty(obj);
  }

  public static boolean isAllEmpty(String[] strs)
  {
    for (int i = 0; i < strs.length; i++) {
      if (!isEmpty(strs[i])) {
        return false;
      }

    }

    return true;
  }

  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0)
      n = 256 + n;
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }

  public static String byteArrayToHexString(byte[] b)
  {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString();
  }
}