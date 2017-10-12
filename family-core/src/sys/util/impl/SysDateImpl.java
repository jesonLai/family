package sys.util.impl;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import sys.util.Interface.SysDate;

public class SysDateImpl extends Date implements SysDate{
	private static final long serialVersionUID = 1L;

	public SysDateImpl(){}
	
    /**********************************************************************************************
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     * @param strDate
     * @return
     *********************************************************************************************/
    public Date strToDate(String strDate) {
     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
     ParsePosition pos = new ParsePosition(0);
     Date strtodate = formatter.parse(strDate, pos);
     return strtodate;
    }
        
    /********************************************************************************************
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     * @param nowdate
     * @param delay
     * @return
     ********************************************************************************************/
    public String getNextDay(String nowdate, String delay) {
     	try{
     		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
     		String mdate = "";
     		Date d = strToDate(nowdate);
     		long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
     		d.setTime(myTime * 1000);
     		mdate = format.format(d);
     		return mdate;
     	}catch(Exception e){
     		return "";
     	}
    }
    
	/*********************************************************************************************
	 * 验证日期是否在合理范围内
	 * @param sdate
	 * @return
	 ********************************************************************************************/
	public boolean CheckDateValidity(String sdate){
		if(sdate!=null && sdate.length()>0){
			if(sdate.substring(0, 10).equals("2004-01-01")) return true;
		}
		String now_date = CurDatetToString();
		String sub = SubTwoDay(sdate,now_date);
		if(sub==null || sub.length()==0) return false;
		float f_sub = Float.parseFloat(sub.trim());
		if(f_sub>0 && f_sub>180) return false;
		else if(f_sub<0 && f_sub<-179) return false;
		return true;
	}
	/**********************************************************************************************
	* 检查字符串是否为日期格式yyyy-mm-dd	
	* 参数：	String sdate
	* 返回：	true-是,false-否
	***********************************************************************************************/
    public boolean CkeckDateStr(String sdate){
    	if (sdate.length() == 0) return true;
    	String ymd[]=sdate.split("-");
    	if (ymd.length != 3) return false;
    	  	
    	char ch;
    	for (int i=0 ; i<ymd.length ; i++){
    		for (int j=0 ; j<ymd[i].length() ; j++){
    			ch = ymd[i].charAt(j);
                if (ch < '0' || ch > '9') return false;
    		}
    	}    	
    	if (ymd[0].length() != 4) return false;
    	int mm=Integer.parseInt(ymd[1]);
    	int dd=Integer.parseInt(ymd[2]);
    	if (mm<1 || mm>12) return false;
    	if (dd<1 || dd>31) return false;
    	
    	return true;        
    }
    /**********************************************************************************************
	* 检查字符串是否为时间格式yyyy-mm-dd hh:mm:ss	
	* 参数：	String dtime
	* 返回：	true-是,false-否
	***********************************************************************************************/
    public boolean CheckDateTimeStr(String dtime){
        if (dtime.length() == 0) return true;
        
        if (dtime == null || dtime.length() != 19) return false;
        String splite[] = dtime.split(" ");
        if (splite == null || splite.length != 2)  return false;
        if (!CkeckDateStr(splite[0])) return false;        
        
        String hhmmss[] = splite[1].split(":");
        if (hhmmss == null || hhmmss.length != 3) return false;
        if (Integer.parseInt(hhmmss[0]) > 23) return false;
        if (Integer.parseInt(hhmmss[1]) > 60) return false;
        if (Integer.parseInt(hhmmss[2]) > 60) return false;
        return true;
    }
    /**********************************************************************************************
	* 检查字符串是否为时间格式yyyy-mm
	* 参数：	String yyyy_mm
	* 返回：	true-是,false-否
	***********************************************************************************************/
    public boolean CheckYYYYMM(String yyyy_mm){
    	if (yyyy_mm==null || yyyy_mm.length()!=7) return false;
    	String splite[] = yyyy_mm.split("-");
    	if (splite == null || splite.length != 2) return false;
        if (Integer.parseInt(splite[1])<1 || Integer.parseInt(splite[1])>12) return false; 
        if (Integer.parseInt(splite[0])<=2004) return false;        
        
        return true;
    }
    /**********************************************************************************************
	* 检查字符串是否为时间格式yyyy-mm-dd hh	
	* 参数：	String dtime
	* 返回：	true-是,false-否
	***********************************************************************************************/
    public boolean CheckDateHHStr(String dtime){
        if (dtime.length() == 0) 					return true;
        if (dtime == null || dtime.length() != 13) 	return false;
        
        String splite[] = dtime.split(" ");
        if (splite == null || splite.length != 2) 	return false;
        if (!CkeckDateStr(splite[0]))             	return false;
        if (Integer.parseInt(splite[1]) > 23) 		return false;
        return true;
    }
    /**********************************************************************************************
	* 检查字符串是否为时间格式yyyy-mm-dd hh:mm	
	* 参数：	String dtime
	* 返回：	true-是,false-否
	***********************************************************************************************/
    public boolean CheckDateHHMMStr(String dtime){
        if (dtime.length() == 0)  					return true;
        if (dtime == null || dtime.length() != 16) 	return false;
        String splite[] = dtime.split(" ");
        if (splite == null || splite.length != 2)  	return false;
        if (!CkeckDateStr(splite[0]))            	return false;
        
        String hhmmss[] = splite[1].split(":");
        if (hhmmss == null || hhmmss.length != 2)	return false;
        if (Integer.parseInt(hhmmss[0]) > 23)      	return false;
        if (Integer.parseInt(hhmmss[1]) > 60)      	return false;
        return true;
    }
    /**********************************************************************************************
	* 取当前日期的年、月、日、时、分、秒	
	* 参数： 
	* 返回：	int[6]
	* Wed Dec 05 14:10:55 CST 2007
	***********************************************************************************************/
    public int[] GetDateInt(){
        int intdate[] = new int[6]; 
        
        String month[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String dstr[]=new Date().toString().split(" ");
        String hhmmss[]=dstr[3].split(":");
        
        intdate[0] = Integer.parseInt(dstr[5]);        
        for (int i=0 ; i<month.length ; i++){
        	if (month[i].equalsIgnoreCase(dstr[1])){
        		intdate[1]=i+1;
        		break;
        	}
        }        
        intdate[2] = Integer.parseInt(dstr[2]);
        
        intdate[3] = Integer.parseInt(hhmmss[0]);        
        intdate[4] = Integer.parseInt(hhmmss[1]);
        intdate[5] = Integer.parseInt(hhmmss[2]);
        return intdate;
    }
    /**********************************************************************************************
	* 将字符串转为日期	
	* 参数：	String stringdate
	* 返回：	Date
	***********************************************************************************************/
    public Date StringToDate(String stringdate){
        Date dt = null;
        if (CkeckDateStr(stringdate)){
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            try {
				dt=df.parse(stringdate);
			}catch (ParseException e) {
				e.printStackTrace();
			}            
        }else{
            dt = new Date();            
        }       
        return dt;
    }
    /**********************************************************************************************
	* 按yyyy-mm-dd格式取当前日期	
	* 参数：
	* 返回：	String
	***********************************************************************************************/
    public String CurDatetToString(){
    	return CurDateTimeToString().substring(0,10);
    }
    /**********************************************************************************************
	* 按yyyy-mm格式取当前日期的上个月
	* 参数：
	* 返回：	String
	***********************************************************************************************/
    public String LastMonth(){    	
    	int intdate[]=GetDateInt();   	
    	
		if (intdate[1]<=1){
			intdate[1]=12;
			intdate[0]--;
		}else{
			intdate[1]--;
		}
		String year_month = Integer.toString(intdate[0])+"-";
		if (intdate[1]<10) year_month +="0";
		year_month += Integer.toString(intdate[1]);
		
		return year_month;		
    }
    /**********************************************************************************************
	* 按yyyy-mm-dd hh:mm:ss 格式取当前时间	
	* 参数：
	* 返回：	String
	* Wed Dec 05 14:10:55 CST 2007
	***********************************************************************************************/
    public String CurDateTimeToString(){        
        String month[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String dstr[]=new Date().toString().split(" ");
        
        String strdate=dstr[5]+"-";           
        for (int i=0 ; i<month.length ; i++){
        	if (month[i].equalsIgnoreCase(dstr[1])){
        		if (i<9) strdate +="0";
        		strdate += Integer.toString(i+1)+"-";
        		break;	
        	}
        }        
        strdate += dstr[2];   
        strdate +=" "+dstr[3];        
        return strdate;
    }
    /**********************************************************************************************
	* 按yyyy-mm-dd hh 格式取当前时间	
	* 参数：
	* 返回：	String
	***********************************************************************************************/
    public String CurDateHHToString(){    	
    	return CurDateTimeToString().substring(0,13);    	
    }
    /**********************************************************************************************
	* 按yyyy-mm-dd hh:mm 格式取当前时间	
	* 参数：
	* 返回：	String
	***********************************************************************************************/
    public String CurDateHHMMToString(){
    	return CurDateTimeToString().substring(0,16);        
    }
    
    /**********************************************************************************************
	* 按yyyy-mm 格式取当前年月	
	* 参数：
	* 返回：	String
	***********************************************************************************************/
    public String CurDateToYYYYMM(){
    	return CurDateTimeToString().substring(0,7);        
    }
    /**********************************************************************************************
	* 按yy-mm 格式取当前年月	
	* 参数：
	* 返回：	String
	***********************************************************************************************/
    public String CurDateToYYMM(){
    	return CurDateTimeToString().substring(2,7).replace("-", "");        
    }
    
    /**********************************************************************************************
	* 按yyyy 格式取当前年份	
	* 参数：
	* 返回：	String
	***********************************************************************************************/
    public String getCurYear(){
    	return CurDateTimeToString().substring(0,4);        
    }
    
    /**********************************************************************************************
	* 按yyyy-mm-dd 取本月1号至本月最后一天
	* 参数：
	* 返回：	String[]: [0]-本月1日  [1]-本月最后一天
	***********************************************************************************************/
    public String[] CurDateToLastMonthDays(){
        int intdate[] = GetDateInt();
        
        String last_month_days[] = new String[2];
        last_month_days[0] = Integer.toString(intdate[0]) + "-";
        
        if(intdate[1] < 10) last_month_days[0] += "0";
        last_month_days[0] += Integer.toString(intdate[1]);
        
        last_month_days[1] = last_month_days[0];
        last_month_days[0] += "-01";
        last_month_days[1] += "-" + Integer.toString(GetMonthDaysInt(intdate[0], intdate[1]));
        return last_month_days;
    }
    /**********************************************************************************************
	* 按yyyy-mm-dd 取上月1号至上月最后一天
	* 参数：
	* 返回：	String[]: [0]-上月1日  [1]-上月最后一天
	***********************************************************************************************/
    public String[] LastMonthDays(){
        int intdate[] = GetDateInt();
        if(intdate[1] == 1){
            intdate[1] = 12;
            intdate[0]--;
        }else{
            intdate[1]--;
        }
        
        String last_month_days[] = new String[2];
        last_month_days[0] = Integer.toString(intdate[0]) + "-";
        if(intdate[1] < 10) last_month_days[0] += "0";
        last_month_days[0] += Integer.toString(intdate[1]);
            
        last_month_days[1] = last_month_days[0];
        last_month_days[0] += "-01";
        last_month_days[1] += "-" + Integer.toString(GetMonthDaysInt(intdate[0], intdate[1]));
        return last_month_days;
    }
    
    /**********************************************************************************************
	* 按yyyy-mm-dd 取给定月份1号至上月最后一天
	* 参数：	String yearMont  
	* 返回：	String[]: [0]-上月1日  [1]-上月最后一天
	***********************************************************************************************/
    public String[] MonthStartEndDate(String yearMonth){
    	String ym[]=yearMonth.split("-");
    	
        int intdate[] = new int[2];
        intdate[0]=Integer.parseInt(ym[0]);
        intdate[1]=Integer.parseInt(ym[1]);
        
        if(intdate[1] == 1){
            intdate[1] = 12;
            intdate[0]--;
        }else{
            intdate[1]--;
        }
        
        String last_month_days[] = new String[2];
        last_month_days[0] = Integer.toString(intdate[0]) + "-";
        if(intdate[1] < 10) last_month_days[0] += "0";
        last_month_days[0] += Integer.toString(intdate[1]);
            
        last_month_days[1] = last_month_days[0];
        last_month_days[0] += "-01";
        last_month_days[1] += "-" + Integer.toString(GetMonthDaysInt(intdate[0], intdate[1]));
        return last_month_days;
    }
    
    /**********************************************************************************************
	* 将日期按yyyy-mm-dd hh:mm转换为字符串	
	* 参数：	Date dd
	* 返回：	String
	***********************************************************************************************/
    public String DateToString(Date dd){        
    	 String strdate=new SimpleDateFormat("yyyy-MM-dd hh:mm").format(dd);       
        return strdate;
    }
    /**********************************************************************************************
   	* 将日期按yyyy-mm-dd hh:mm转换为字符串	
   	* 参数：	Date dd
   	* 返回：	String
   	***********************************************************************************************/
       public String DateToStringyyyymmdd(Date dd){        
       	 String strdate=new SimpleDateFormat("yyyy-MM-dd").format(dd);       
           return strdate;
       }
    /**********************************************************************************************
	* 将日期	yyyy-mm-d、yyyy-m-d、yyyy-m-dd转为yyyy-mm-dd格式
	* 参数：	String stringdate
	* 返回：	String
	***********************************************************************************************/
    public String DsToFds(String stringdate){
    	return DsToYYYYMMDDHHMMSS(stringdate+" 00:00:00").substring(0,10);
    }
    /**********************************************************************************************
	* 将日期	yyyy-mm-d、yyyy-m-d、yyyy-m-dd转为yyyy-mm-dd格式
	* 参数：	String stringdate
	* 返回：	String
	***********************************************************************************************/
    public String DsToYYYYMMDD(String stringdate){
    	return DsToYYYYMMDDHHMMSS(stringdate+" 00:00:00").substring(0,10);        
    }
    /**********************************************************************************************
	* 将日期	yyyy-mm-d、yyyy-m-d、yyyy-m-dd转为yyyy-mm-dd格式
	* 参数：	String stringdate
	* 返回：	String
	***********************************************************************************************/
    public String DsToYYYYMMDD2(String stringdate){
    	String mmddyy[]=stringdate.split("-");
        
        String str=mmddyy[0]+"-";
        if (mmddyy[1].length()==1) str+="0";
        str+=mmddyy[1]+"-";
        if (mmddyy[2].length()==1) str+="0";
        str+=mmddyy[2];
        
        return str;    	
    }    
    /**********************************************************************************************
	* 将时间格式化为yyyy-mm-dd hh 格式
	* 参数：	String stringdate
	* 返回：	String
	***********************************************************************************************/
    public String DsToYYYYMMDDHH(String stringdate){
    	return DsToYYYYMMDDHHMMSS(stringdate+":00:00").substring(0,13);
    }
    /**********************************************************************************************
	* 将时间格式化为yyyy-mm-dd hh:mm 格式
	* 参数：	String stringdate
	* 返回：	String
	***********************************************************************************************/
    public String DsToYYYYMMDDHHMM(String stringdate){        
        return DsToYYYYMMDDHHMMSS(stringdate+":00").substring(0,16);
    }
    /**********************************************************************************************
	* 将时间格式化为yyyy-mm-dd hh:mm:ss 格式
	* 参数：	String stringdate
	* 返回：	String
	***********************************************************************************************/
    public String DsToYYYYMMDDHHMMSS(String stringdate){
        String rstring = "";
        
        if (!CheckDateTimeStr(stringdate)){
        	rstring = CurDateTimeToString();
        	return rstring;
        }        
        String yymmddhhmmss[] = stringdate.split(" ");
        if (yymmddhhmmss == null || yymmddhhmmss.length != 2){
        	rstring = CurDateTimeToString();
        	return rstring;
        }        
        String yymmdd[] = yymmddhhmmss[0].split("-");
        if (yymmdd == null || yymmdd.length != 3){
        	rstring = CurDateTimeToString();
        	return rstring;
        } 
        String hhmmss[] = yymmddhhmmss[1].split(":");
        if (hhmmss == null || hhmmss.length != 3){
        	rstring = CurDateTimeToString();
            return rstring;
        }   
        
        int year 	= Integer.parseInt(yymmdd[0]);
        int month 	= Integer.parseInt(yymmdd[1]);
        int day 	= Integer.parseInt(yymmdd[2]);
        rstring = Integer.toString(year) + "-";
        if (month < 10) rstring += "0";
        rstring += Integer.toString(month) + "-";
        if (day < 10)  rstring += "0";
        rstring += Integer.toString(day);
        
        int hh = Integer.parseInt(hhmmss[0]);
        int mm = Integer.parseInt(hhmmss[1]);
        int ss = Integer.parseInt(hhmmss[2]);
        rstring += " ";
        if (hh < 10) rstring += "0";
        rstring += Integer.toString(hh) + ":";
        if (mm < 10) rstring += "0";
        rstring += Integer.toString(mm) + ":";
        if(ss < 10) rstring += "0";
        rstring += Integer.toString(ss);
    
        return rstring;
    }
    /**********************************************************************************************
	* 验证格式为yyyy-mm-dd日期的合法性质
	* 参数：	String stringdate
	* 返回：	true-合法,false-非法
	***********************************************************************************************/
    public boolean StringDsValid(String stringdate){
        if(!CkeckDateStr(stringdate)) return false;
        
        String yymmdd[]=stringdate.split("-");
        int yy = Integer.parseInt(yymmdd[0]);
        int mm = Integer.parseInt(yymmdd[1]);
        int dd = Integer.parseInt(yymmdd[2]);
        
        if(mm < 1 || mm > 12) return false;
        if(mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10 || mm == 12){
            if(dd < 1 || dd > 31) return false;
        }else if (dd < 1 || dd > 30) return false;
        
        if (mm==2){
        	if ((yy/4)* 4 == yy && dd>29) return false;
        	else if (dd>28) return false;        	
        }        
        return true;
    }
    /**********************************************************************************************
	* 验证格式为mm/dd/yyyy日期的合法性质
	* 参数：	String stringdate
	* 返回：	true-合法,false-非法
	***********************************************************************************************/
    public boolean MmddyyyyDsValid(String stringdate){
       String mmddyy[]=stringdate.split("/");
       
       String str=mmddyy[2]+"-";
       if (mmddyy[0].length()==1) str+="0";
       str+=mmddyy[0]+"-";
       if (mmddyy[1].length()==1) str+="0";
       str+=mmddyy[1];
       
       return StringDsValid(str);    	
    }
    /**********************************************************************************************
	* 将格式为mm/dd/yyyy转为yyyy/mm/dd
	* 参数：	String stringdate
	* 返回：	String
	***********************************************************************************************/
    public String MmddyyyDsToFds(String stringdate){
        if (!MmddyyyyDsValid(stringdate)){
            return CurDatetToString();
        }else{
            String mmddyyyystr[] = stringdate.split("/");
            stringdate = mmddyyyystr[2] + "/" + mmddyyyystr[1] + "/" + mmddyyyystr[0];
            return DsToFds(stringdate);
        }
    }
    /**********************************************************************************************
	* 日期加天数返回日期
	* 参数：	String 	curDate[[YYYY-MM-DD]]
	* 		int		adddays
	* 返回：	String
	***********************************************************************************************/
    public String AddDate(String curDate, int adddays){
    	String ymd[]=curDate.split("-");
        int yy 	= Integer.parseInt(ymd[0]);
        int mm 	= Integer.parseInt(ymd[1]);
        int dd 	= Integer.parseInt(ymd[2]);
        
        dd += adddays;
        int month_days=31;
        while (true){
        	if(dd<=0)
        		{
        		  mm--;
        		  if(mm==0){
        			mm=12;
        			yy--;
        		  }
        		}
        	switch (mm){
        		case 1:	case 3:	case 5:	case 7:	case 8:	case 10:case 12:
        			month_days=31;
        			break;
        		case 4:	case 6:	case 9:	case 11:
        			month_days=30;
        			break;
        		case 2:
        			month_days=28;
        			if(yy%400==0 || (yy%100!=0 && yy%4==0)) month_days=29;        			
        			break;
        	}
        	if(dd<=0){
        		dd+=month_days;        	
        	}
        	else if (dd > month_days){
        		dd -= month_days;
        		mm++;
        		if (mm>12){
        			yy++;
        			mm=1;
        		}
        	}
          	else{
        		break;
        	}
        }
        curDate = Integer.toString(yy)+"-";
        if (mm < 10) curDate += "0";
        curDate += Integer.toString(mm)+"-";
        if (dd < 10) curDate += "0";        
        curDate += Integer.toString(dd);
        return curDate;
    }
        
    /**********************************************************************************************
	* 日期加年
	* 参数：	String 	curDate[[YYYY-MM-DD]]
	* 		int		addyear
	* 返回：	String
	***********************************************************************************************/
    public String AddYear(String curDate, int addyear){
    	String ymd[]=curDate.split("-");
        int yy 	= Integer.parseInt(ymd[0]);       
        yy+=addyear;            
        curDate = Integer.toString(yy)+"-";    
        curDate += ymd[1]+"-";         
        curDate += ymd[2];
        return curDate;
    }
    /**********************************************************************************************
	* 日期加月
	* 参数：	String 	curDate[YYYY-MM-DD]
	* 		int		addmonth
	* 返回：	String
	***********************************************************************************************/
    public String AddMonth(String curDate, int addmonth){
    	String ymd[]=curDate.split("-");
        int yy 	= Integer.parseInt(ymd[0]);
        int mm 	= Integer.parseInt(ymd[1]);
        int dd 	= Integer.parseInt(ymd[2]);
        
        mm += addmonth;
        while (mm>12){
        	mm-=12;
        	yy++;
        }        
        while(mm<=0){
        	mm+=12;
        	yy--;
        }
        int month_days=31;
        switch (mm){
			case 1: case 3:case 5:	case 7:	case 8:	case 10:case 12:
				month_days=31;
				break;
			case 4:	case 6:	case 9:	case 11:
				month_days=30;
				break;
			case 2:
				month_days=28;
				if(yy%400==0 || (yy%100!=0 && yy%4==0)) month_days=29;
				break;
        }
        if (dd>month_days){
        	dd=month_days;        	
        }
        curDate = Integer.toString(yy)+"-";
        if (mm < 10) curDate += "0";
        curDate += Integer.toString(mm)+"-";
        if (dd < 10) curDate += "0";        
        curDate += Integer.toString(dd);
        return curDate;
    }
    /**********************************************************************************************
	* 日期减天数返回日期[[YYYY-MM-DD]]
	* 参数：	String 	curDate
	* 		int		subdays
	* 返回：	String
	***********************************************************************************************/
    public String SubDate(String curDate, int subdays){
    	String ymd[]=curDate.split("-");
        int yy 	= Integer.parseInt(ymd[0]);
        int mm 	= Integer.parseInt(ymd[1]);
        int dd 	= Integer.parseInt(ymd[2]);
        
        dd -= subdays;
        int month_days=31;
        while (dd<1){        	        		
        	mm--;
        	if (mm<1){
        		yy--;
        		mm=12;
        	}        	
        	switch (mm){
        		case 1:	case 3:	case 5:	case 7:	case 8:	case 10:case 12:
        			month_days=31;
        			break;
        		case 4:	case 6:	case 9:	case 11:
        			month_days=30;
        			break;
        		case 2:
        			month_days=28;
        			if( (yy/4)*4 == yy) month_days=29;
        			break;
        	}        	
        	dd += month_days;
        }
        curDate = Integer.toString(yy)+"-";
        if (mm < 10) curDate += "0";
        curDate += Integer.toString(mm)+"-";
        if (dd < 10) curDate += "0";        
        curDate += Integer.toString(dd);
        return curDate;
    }
    /**********************************************************************************************
	* 将yyyy-mm减月数
	* 参数：	String 	curDate[YYYY-MM]
	* 		int		submonth
	* 返回：	String
	***********************************************************************************************/
    public String SubMonth(String curDate, int submonth){
    	String ymd[]=curDate.split("-");
        int yy 	= Integer.parseInt(ymd[0]);
        int mm 	= Integer.parseInt(ymd[1]);        
        mm-=submonth;
        while (mm<1){
        	yy--;
        	mm+=12;
        }        
        while (mm>12){
        	yy++;
        	mm-=12;
        }
        curDate = Integer.toString(yy)+"-";
        if (mm < 10) curDate += "0";
        curDate += Integer.toString(mm);
        
        return curDate;
    }    
    
    /**********************************************************************************************
	* 新增方法：2007-12-12
	* 将yyyy-mm减月数
	* 参数：	String 	curMonth
	* 		int		submonth
	* 返回：	String
	***********************************************************************************************/
    public String LastMonth(String curMonth, int submonth){
    	String ymd[]=curMonth.split("-");
        int yy 	= Integer.parseInt(ymd[0]);
        int mm 	= Integer.parseInt(ymd[1]);        
        mm-=submonth;
        while (mm<1){
        	yy--;
        	mm+=12;
        }        
        curMonth = Integer.toString(yy)+"-";
        if (mm < 10) curMonth += "0";
        curMonth += Integer.toString(mm);
        
        return curMonth;
    }    
    
    /**********************************************************************************************
	* 两个日期之间的间隔天数：
	* @param sj1 : 开始日期
	* @param sj2 : 结束日期
	* @return：	String
	***********************************************************************************************/
    public String SubTwoDay(String sj1, String sj2) {
    	SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
    	long day = 0;
    	try {
    	   java.util.Date date = myFormatter.parse(sj1);
    	   java.util.Date mydate = myFormatter.parse(sj2);
    	   day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
    	} catch (Exception e) {
    	   return "";
    	}
    	return Long.toString(day);
    }
    
    /**********************************************************************************************
	* 两个日期之间的间隔小时： 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟 
	* @param st1 : 开始日期
	* @param st2 : 结束日期
	* @return String
	***********************************************************************************************/
    public String SubTwoHour(String st1, String st2) {
    	String[] kk = null;
    	String[] jj = null;
    	kk = st1.split(":");
    	jj = st2.split(":");
    	if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
    	   return "0";
    	else {
    	   double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
    	   double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
    	   if ((y - u) > 0)
    		   return Double.toString(y-u);
    	   else
    		   return "0";
    	}
    }
    
    
    /**********************************************************************************************
	* 检测给出的字符串是否为浮点数
	* 参数：	String 	fstr
	* 		int		len
	* 返回：	true-浮点数,false-非浮点数
	***********************************************************************************************/
    public boolean CheckFloat(String fstr, int len){
//      if (fstr == null || fstr.length() > len || fstr.length() == 0) 		return false;     
        if(fstr == null || fstr.length() == 0) return false;
        
        char ch = fstr.charAt(0);
        if (ch != '-' && ch != '+' && ch != '.' && (ch > '9' || ch < '0')) 	return false;
        if ((ch == '-' || ch == '+' || ch == '.') && fstr.length() <= 1) 	return false;
        boolean point_flag = false;       
        if (ch == '.') point_flag = true;
        
        //2010-12-16 chery 
        //修改如下：将原有的数据长度判断 改为 去掉小数点后进行判断（根据数据库的数据存储）
        int count = fstr.length();	
        
        for(int i = 1; i < fstr.length(); i++){
            ch = fstr.charAt(i);
            if (ch != '.' && (ch > '9' || ch < '0')) return false;
            if (ch == '.'){
                if (point_flag) return false;
                point_flag = true;
                count--;
            }
        }
        if(count>len) return false;
        return true;
    }
    /**********************************************************************************************
	* 检测给出的字符串是否为整数
	* 参数：	String 	fstr
	* 		int		len
	* 返回：	true-整数,false-非整数
	***********************************************************************************************/
    public boolean CheckInt(String istr, int len){
        if(istr == null || istr.length() > len || istr.length() == 0) return false;
        char ch = istr.charAt(0);
        if (ch != '-' && ch != '+' && (ch > '9' || ch < '0')) 	return false;
        if ((ch == '-' || ch == '+') && istr.length() <= 1)  	return false;
        for (int i = 1; i < istr.length(); i++){
            ch = istr.charAt(i);
            if (ch > '9' || ch < '0') return false;
        }
        return true;
    }
    /**********************************************************************************************
    * 取年、月的天数
    * 参数：	int yy
    * 		int	mm
    * 返回：	String    
    ***********************************************************************************************/
    public String GetMonthDays(int yy, int mm){
        String days = "31";
        if (mm == 4 || mm == 6 || mm == 9 || mm == 11) days = "30";
        if (mm == 2){
            days = "28";
            if ((yy / 4) * 4 == yy) days = "29";
        }
        return days;
    }
    /**********************************************************************************************
    * 取年、月的天数
    * 参数：	int yy
    * 		int	mm
    * 返回：	int    
    ***********************************************************************************************/
    public int GetMonthDaysInt(int yy, int mm){
    	return Integer.parseInt(GetMonthDays(yy,mm));
    }
    /**********************************************************************************************
    * 取年、月的天数
    * 参数：	String yyyymm    
    * 返回：	int    
    ***********************************************************************************************/
    public int GetMonthDaysIntStr(String yyyymm){
        String str[] = yyyymm.split("-");
        int yy = Integer.parseInt(str[0]);
        int mm = Integer.parseInt(str[1]);
        return GetMonthDaysInt(yy, mm);
    }
    /**********************************************************************************************
    * 根据字符串日期 yyyy-mm-dd 取 星期1－7
    * 参数：	String     
    * 返回：	int    
    ***********************************************************************************************/
    public int GetWeekDays(String date_str){
        int week_day = 0;
        Date dd = StringToDate(date_str);
        
        String week_date[]={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        String dstr[]=dd.toString().split(" ");
        
        for (int i=0 ; i<week_date.length ; i++){
        	if (dstr[0].equalsIgnoreCase(week_date[i])){
        		week_day=i;
        		break;
        	}
        }        
        if(week_day == 0) week_day = 7;
        return week_day;
    }
    /**********************************************************************************************
    * 验证yyyy-mm的合法性
    * 参数：	String year_month    
    * 返回：	true-合法,false-非法    
    ***********************************************************************************************/
    public boolean ValidYearMnonth(String year_month){
    
        int mm = 0;
        if (year_month == null || year_month.length() != 7) return false;
        int in = year_month.indexOf("-");
        if (in == -1 || in != 4) return false;
        mm = Integer.parseInt(year_month.substring(5,7));
        return mm >= 1 && mm <= 12;
    }
    /**********************************************************************************************
    * 根据当前年月取去年的年月
    * 参数：	String year_month    
    * 返回：	String    
    * 说明：	2007-11==>2006-12 
    ***********************************************************************************************/
    public String GetLastYearMonth(String year_month){
        String yymm[] = year_month.split("-");
        int yy = Integer.parseInt(yymm[0]);
        int mm = Integer.parseInt(yymm[1]);
        if (mm < 12){
            yy--;
            mm++;
        }else{
            mm = 1;
        }
        String last_year_month = Integer.toString(yy) + "-";
        if(mm < 10) last_year_month += "0";           
        last_year_month += Integer.toString(mm);
        return last_year_month;
    }
    /**********************************************************************************************
    * 验证给出的字符串是否为邮件地址
    * 参数：	String email_address    
    * 返回：	true-合法,false-非法    
    ***********************************************************************************************/
    public boolean CheckEmailValid(String email_address){
        if (email_address == null) return false;
        int len = email_address.length();
        int in1 = email_address.indexOf(64);
        int in2 = email_address.indexOf(46);
        if (in1 == -1) return false;
        if (len < 6)   return false;
        if (in1 == len || in2 == len)  return false;
        if (in1 >= in2) return false;
        for(int i = 0; i < len; i++){
            char ch = email_address.charAt(i);
            if ((ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z') && (ch < '0' || ch > '9') && ch != '@' && ch != '.')
                return false;
        }
        return true;
    }
    
    /**********************************************************************************************
     * 计算给出的日期所在的周及该周星期一和星期天的日期
     * 参数：String yyyy_mm_dd
     * 返回：Hashtable [int,String,String] 
     * *******************************************************************************************/
    @SuppressWarnings("unchecked")
	public Hashtable GetWeekNoAndDate(String year_month_day){
    	int weekday = GetWeekDays(year_month_day);
		String monday_date = new String();
		String sunday_date = new String();
		if(weekday == 1){
			monday_date = year_month_day;
			sunday_date = AddDate(year_month_day,7);
		}else if(weekday == 7){
			monday_date = SubDate(year_month_day,7);
			sunday_date = year_month_day;
		}else{
			int min =weekday -1;
			int max =7-weekday;
			monday_date = SubDate(year_month_day,min);
			sunday_date = AddDate(year_month_day,max);
		}
		Hashtable resH=new Hashtable();
		resH.put("WeekNo", Integer.toString(weekday));
		resH.put("Monday", monday_date);
		resH.put("Sunday", sunday_date);
		
		return resH;
    }
    /**********************************************************************************************
     * 根据YYYY-MM-DD 格式化为 YYYY年MM月-日
     **********************************************************************************************/
    public String GetCurrentYMD(String YMDate){
    	String newDate ="";
    	if(YMDate!=null && YMDate.length()>=10){
        	String[] result = YMDate.split("-");
        	newDate = result[0]+"年"+result[1]+"月"+result[2]+"日";    		
    	}
    	return newDate;
    }
    /*********************************************************************************************
     * 根据YYYY-MM-DD 得到 星期几
     *********************************************************************************************/
	public String GetWeekDay(String YMDate){
		String weekDay = "";
		String[][] comparedata = {{"1","一"},{"2","二"},{"3","三"},{"4","四"},{"5","五"},{"6","六"},{"7","日"}};
		String str_week = Integer.toString(GetWeekDays(YMDate));
		for(int i=0; i<comparedata.length; i++){
			if(comparedata[i][0].equalsIgnoreCase(str_week)){
				weekDay="星期"+comparedata[i][1];
				break;
			}
		}
		return weekDay;
	}
	
    /**********************************************************************************
     * 取今天以前一个星期的日期范围：
     * 返回：String[2]:[0]-开始日期－7,[1]-结束日期    
     ***********************************************************************************/
     public String[] get_week_date(){
         String week_date[] = new String[2];      
         week_date[1]=CurDatetToString();         //end date
         week_date[0]=SubDate(week_date[1],7);    //start date 
         return week_date;
     }
     
	 /**********************************************************************************
	  * 取输入年份的所有月份
	  * @param year
	  * @return
	  **********************************************************************************/
    @SuppressWarnings("unchecked")
	public ArrayList getMonths(String year){
    	 ArrayList resultA = new ArrayList();
    	 if(year==null || year.length()==0){
    		 return resultA;
    	 }
    	 for(int i=1; i<=12; i++){
    		 Hashtable monthH = new Hashtable();
    		 monthH.put("month", Integer.toString(i));
    		 if(i<10){
    			 monthH.put("year_month", year+"-0"+Integer.toString(i));	 
    		 }else{
    			 monthH.put("year_month", year+"-"+i);
    		 }
    		 resultA.add(monthH);
    	 }
    	 return resultA;
     }

     
}
