package sys.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.safehaus.uuid.UUIDGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.common.collect.Maps;

import sys.BaseHint;

/**
 * Created by IntelliJ IDEA. User: lxr To change this template use File |
 * Settings | File Templates.
 */
public final class StringUtil {
	final static Pattern pattern = Pattern.compile("\\S*[?]\\S*");
	/**
	 * 判斷是否是null 或是 ""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {

		if (str == null)
			return true;
		if (str != null && "".equals(str.toString().trim()))
			return true;

		return false;
	}

	public static boolean isAllEmpty(String... strs) {
		for (int i = 0; i < strs.length; i++) {
			if (isEmpty(strs[i]) == false) {
				return false;
			} else {
				continue;
			}
		}
		return true;
	}
	public static String NVL(String sSrc, String sNotNull, String sIsNull){
		return (sSrc== null)? sIsNull: sNotNull;
	}
	public static String NVLStr(String sSrc, String sNotNull, String sIsNull){
		return (sSrc== null||sSrc.equals("null")||sSrc=="null"||sSrc==""||sSrc.equals("")||sSrc=="undefined"||sSrc.equals("undefined"))? sIsNull: sNotNull;
	}	
	public static String nullIF(String sSrc, String sRtn){ return NVL(sSrc, sSrc, sRtn); }
	public static String nullStr(String sSrc, String sRtn){ return NVLStr(sSrc, sSrc, sRtn); }
	// public static String getID() {
	// return UUID.randomUUID().toString();
	// }
	public static String getUUIDGenerator() {
		return UUIDGenerator.getInstance().generateRandomBasedUUID().toString().replaceAll("-", "");
	}

	public static String str;
	public static final String EMPTY_STRING = "";

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F" };

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	/*
	 * 按月计算 开通时间-->到期时间 当前时间，月数[单位 :月]
	 * 
	 * @key=purchase_dredge_time
	 * 
	 * @key=purchase_expire_time
	 * 
	 * @key=gold
	 */
	public static Map<String, Object> starEndtDateFormat(Date date, SimpleDateFormat sdf, Integer month) {
		int gold = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		map.put("purchase_dredge_time", sdf.format(calendar.getTime()));
		calendar.add(Calendar.MONTH, month);
		map.put("purchase_expire_time", sdf.format(calendar.getTime()));
		if (month == 60)
			gold = 109500;
		else if (month == 12)
			gold = 36500;
		else if (month == 6)
			gold = 30000;
		else if (month == 1)
			gold = 15000;
		else
			gold = 0;
		map.put("gold", gold);
		map.put("month", month);
		return map;
	}

	/*
	 * 按天计算 开通时间-->到期时间 当前时间，月数[单位 :月]
	 * 
	 * @key=purchase_dredge_time
	 * 
	 * @key=purchase_expire_time
	 * 
	 * @key=gold
	 */
	public static Map<String, Object> starEndtDateDayFormat(Date date, SimpleDateFormat sdf, Integer day) {
		int gold = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		map.put("purchase_dredge_time", sdf.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_MONTH, day);
		map.put("purchase_expire_time", sdf.format(calendar.getTime()));
		if (day == 60)
			gold = 109500;
		else if (day == 12)
			gold = 36500;
		else if (day == 6)
			gold = 30000;
		else if (day == 1)
			gold = 15000;
		else
			gold = 0;
		map.put("gold", gold);
		map.put("month", day);
		return map;
	}

	/*
	 * 编号生成
	 */
	public static String getID(String total) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = format.format(new Date());
		String id = date + total;
		return id;
	}

	private static final AtomicInteger integer = new AtomicInteger(0);

	public static String getId() {
		long time = System.currentTimeMillis();
		StringBuilder str = new StringBuilder(20);
		str.append(time);
		int intValue = integer.getAndIncrement();
		if (integer.get() >= 10000) {
			integer.set(0);
		}
		if (intValue < 10) {
			str.append("000");
		} else if (intValue < 100) {
			str.append("00");
		} else if (intValue < 1000) {
			str.append("0");
		}
		str.append(intValue);
		return str.toString();
	}

	/*
	 * 时间差得天数
	 * 
	 * @
	 */
	public static int getDay(String dateTime, String dateTime2) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long to = df.parse(dateTime).getTime();
		long from = df.parse(dateTime2).getTime();
		int day = (int) ((to - from) / (1000 * 60 * 60 * 24));
		return day;
	}

	/**
	 * 计算指定时间与当前系统时间的时间差
	 * 
	 * <p>
	 * nowData:2014-12-24 14:15:12
	 * </p>
	 * <p>
	 * data:2014-12-24 14:14:22
	 * </p>
	 * 相差0天0小时0分50秒
	 * 
	 * @param date
	 *            需要计算的时间
	 * @return dateLong[天,时,分,秒]
	 */
	public static long[] getDateChar(Date date) {
		long[] dateLong = new long[4];
		long l = new Date().getTime() - date.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		dateLong[0] = day; // 天数
		dateLong[1] = hour; // 时
		dateLong[2] = min; // 分
		dateLong[3] = s; // 秒
		return dateLong;
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/base";
			String user = "root";
			String pass = "123456";
			conn = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) {
		// try {
		// long[] l = StringUtil.getDateChar(new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss").parse("2015-04-26 17:03:57"));
		// int day = Integer.parseInt(String.valueOf(l[0]));
		// int hour = Integer.parseInt(String.valueOf(l[1]));
		// int min = Integer.parseInt(String.valueOf(l[2]));
		// int s = Integer.parseInt(String.valueOf(l[3]));
		// long ls = 0;
		// System.out.println("day=" + day + "hour=" + hour + "min=" + min +
		// "s=" + s);
		// if (day > 0) {
		//
		// ls += day * 86400;
		// }
		// if (hour > 0) {
		// ls += hour * 3600;
		// }
		// if (min > 0) {
		// ls += min * 60;
		// }
		// if (s > 0) {
		// ls += s;
		// }
		// System.out.println(ls);
		// long sl = 259200 - ls;
		// System.out.println("sss=" + sl / 86400 + "天" + sl / 3600 + "小时" + sl
		// / 60 + "分" + sl + "秒");
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// Connection conn=getConnection();
		// String sql="select * from pub_users";
		// PreparedStatement stmt;
		// try {
		// stmt = conn.prepareStatement(sql);
		// ResultSet rs=stmt.executeQuery(sql);
		// ResultSetMetaData data=rs.getMetaData();
		// while(rs.next()){
		// for(int i = 1 ; i<= data.getColumnCount() ; i++){
		// //获得所有列的数目及实际列数
		// int columnCount=data.getColumnCount();
		// //获得指定列的列名
		// String columnName = data.getColumnName(i);
		// //获得指定列的列值
		// String columnValue = rs.getString(i);
		// //获得指定列的数据类型
		// int columnType=data.getColumnType(i);
		// //获得指定列的数据类型名
		// String columnTypeName=data.getColumnTypeName(i);
		// //所在的Catalog名字
		// String catalogName=data.getCatalogName(i);
		// //对应数据类型的类
		// String columnClassName=data.getColumnClassName(i);
		// //在数据库中类型的最大字符个数
		// int columnDisplaySize=data.getColumnDisplaySize(i);
		// //默认的列的标题
		// String columnLabel=data.getColumnLabel(i);
		// //获得列的模式
		// String schemaName=data.getSchemaName(i);
		// //某列类型的精确度(类型的长度)
		// int precision= data.getPrecision(i);
		// //小数点后的位数
		// int scale=data.getScale(i);
		// //获取某列对应的表名
		// String tableName=data.getTableName(i);
		// // 是否自动递增
		// boolean isAutoInctement=data.isAutoIncrement(i);
		// //在数据库中是否为货币型
		// boolean isCurrency=data.isCurrency(i);
		// //是否为空
		// int isNullable=data.isNullable(i);
		// //是否为只读
		// boolean isReadOnly=data.isReadOnly(i);
		// //能否出现在where中
		// boolean isSearchable=data.isSearchable(i);
		// System.out.println(columnCount);
		// System.out.println("获得列"+i+"的字段名称:"+columnName);
		// System.out.println("获得列"+i+"的字段值:"+columnValue);
		// System.out.println("获得列"+i+"的类型,返回SqlType中的编号:"+columnType);
		// System.out.println("获得列"+i+"的数据类型名:"+columnTypeName);
		// System.out.println("获得列"+i+"所在的Catalog名字:"+catalogName);
		// System.out.println("获得列"+i+"对应数据类型的类:"+columnClassName);
		// System.out.println("获得列"+i+"在数据库中类型的最大字符个数:"+columnDisplaySize);
		// System.out.println("获得列"+i+"的默认的列的标题:"+columnLabel);
		// System.out.println("获得列"+i+"的模式:"+schemaName);
		// System.out.println("获得列"+i+"类型的精确度(类型的长度):"+precision);
		// System.out.println("获得列"+i+"小数点后的位数:"+scale);
		// System.out.println("获得列"+i+"对应的表名:" + tableName);
		// System.out.println("获得列"+i+"是否自动递增:"+isAutoInctement);
		// System.out.println("获得列"+i+"在数据库中是否为货币型:"+isCurrency);
		// System.out.println("获得列"+i+"是否为空:"+isNullable);
		// System.out.println("获得列"+i+"是否为只读:"+isReadOnly);
		// System.out.println("获得列"+i+"能否出现在where中:"+isSearchable);
		// }
		// }
		// } catch (SQLException e) {
		// System.out.println("数据库连接失败");
		// }
		// System.out.println(StringUtil.getId());
//		System.out.println(StringUtil.fileSize(150364));
//		File d=new File("D:ceshi.txt");
//		try {
//			OutputStream w=new FileOutputStream(d);
//			w.write("OK".getBytes());
//			Writer w1=new FileWriter(d);
//			w1.write("OK");
//			w1.flush();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		EncodedResource encodedResource = new EncodedResource("/WEN-INF/familyBasis.properties", "utf-8");
		 Map<String , Object> data = Maps.newHashMap();
	        data.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);  
	        data.put(BaseHint.MESSAGE, BaseHint.FREQUENT_OPERATION);  
			System.out.println(JSONUtils.toJSONObject(data));
	}

	/**
	 * 判断身份证是否是真实的
	 * 
	 * @param arrIdCard
	 * @return
	 */
	public static boolean isIdCard(String arrIdCard) {
		int sigma = 0;
		Integer[] a = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		String[] w = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		for (int i = 0; i < 17; i++) {
			int ai = Integer.parseInt(arrIdCard.substring(i, i + 1));
			int wi = a[i];
			sigma += ai * wi;
		}
		int number = sigma % 11;
		String check_number = w[number];
		if (!arrIdCard.substring(17).equals(check_number)) {
			return false;
		} else {
			return true;
		}
	}
	/*
	 * 获取前beforeDays天时间 dateString:2015-01-07 beforeDays:7
	 * 
	 * @return 5015-01-14
	 */

	public static Date getDate(String dateString, int beforeDays) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date inputDate = dateFormat.parse(dateString);

		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);

		int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear + beforeDays);

		return cal.getTime();

	}

	/**
	 * 获取月份差 start:2015-01-07 end :2015-02-07
	 * 
	 * @param start
	 * @param end
	 * @return 1
	 */
	public static int getMonthCount(Date start, Date end) {
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
		temp.add(Calendar.DATE, 1);

		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

		if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month + 1;
		} else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month;
		} else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
			return year * 12 + month;
		} else {
			return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
		}
	}

	public static String fileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	/**
	 * 前后台比较
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isFrontOrBack(String url) {
		return url.indexOf("admin") != -1;
	}

	public static String bCryptPasswordEncoder(String pwd) {
		return new BCryptPasswordEncoder(11).encode(pwd);
	}
	public static String getEncoding(String str) {      
		String gb="GB2312";
		String iso="ISO-8859-1";
		String utf="UTF-8";
		String gbk="GBK";
          try {
        	  if (str.equals(new String(str.getBytes(gb), gb))) {  
        		  str= new String(str.getBytes(gb),utf);   
			   }else if(str.equals(new String(str.getBytes(iso), iso))){
				   str= new String(str.getBytes(iso),utf);   
			   }else if(str.equals(new String(str.getBytes(gbk), gbk))){
				   str=new String(str.getBytes(gbk),utf);   
			   }else if(str.equals(new String(str.getBytes(utf),utf))){
			   }else{
				   str="";
			   }
			} catch (UnsupportedEncodingException e) {
			}     
          return str;
	   }      
	/**
	 * 获取链接的后缀名
	 * 
	 * @return
	 */
	// public static String parseSuffix(String url) {
	//
	// Matcher matcher = pattern.matcher(url);
	//
	// String[] spUrl = url.toString().split("/");
	// int len = spUrl.length;
	// String endUrl = spUrl[len - 1];
	// String[] enUrlArray=endUrl.split("\\.");
	// if(matcher.find()) {
	// String[] spEndUrl = endUrl.split("\\?");
	// return spEndUrl[0].split("\\.")[spEndUrl.length-1];
	// }
	// return enUrlArray[enUrlArray.length-1];
	// }
}