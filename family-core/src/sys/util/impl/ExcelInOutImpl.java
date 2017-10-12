package sys.util.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import sys.util.ExcelInOut;

/**
 * Excel导出、导入
 * Create Date:2009-12-13
 * Author:chery
 */
public class ExcelInOutImpl implements ExcelInOut{
	/******************************************************************************* 
     * String 				file_name		--文件名称
     * String 				title_name		--Excel的标题名称
     * int					title_len		--标题列数 	
     * String				left_info[]		--Excel左侧信息 【表单以上】
     * String 				right_info[]	--Excel右侧信息 【表单以上】
     * String 				buttom_info[]	--Excel底部信息 【表单底部】			
     * String               table_head
     * 						说明:	table_head[i]["字段名","中文名","英文名","显示占用宽度","显示位置"]
     * 								table_head[i][4]=0--左;1--中;2--右
     * ArrayList			list[Iterator]	--需要导出的信息 
     *******************************************************************************/
    @SuppressWarnings({ "deprecation", "unchecked" })
	public int outExcel(  String    file_name, 
	                      String    title_name,
	                      int		title_len,				
	                      String    left_info[],			
	                      String    right_info[],			
	                      String    bottom_info,			
	                      String    table_head[][],
	                      ArrayList list){
//    	HttpServletResponse response = ServletActionContext.getResponse();
//    	
//    	//初始化参数
//        int rst = 0;
//        try{
//            String fname = "";
//            //取Excel文件名
//            if(file_name == null || file_name.length() == 0) file_name = "";
//            fname = "C:/TFile/"+file_name + ".xls";
//            
//            //验证文件是否存在
//            if(!new File("C:/TFile/").isDirectory())  new File("C:/TFile/").mkdirs();
//            
//            //文件对象
//            WritableWorkbook workbook = Workbook.createWorkbook(new File(fname));            
//
//            //工作薄对象
//            WritableSheet sheet = workbook.createSheet(file_name,0);
//            
//            //表样式设置
//            int length = 0;
//            String table_style="1";
//            if(table_style.equalsIgnoreCase("1")){
//                length = title_len*20;
//            }else{
//                length = 138;
//            }    
//                       
//            //字型设置：18==>标题、14==>左侧信息、右侧信息、底部信息
//            //WritableFont(字型字体,字型大小,字型风格,是否斜体,下划线,字型颜色)
//            WritableFont wfcP = new WritableFont(WritableFont.createFont("宋体"), 18, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
//            WritableFont wfc1 = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
//            WritableCellFormat wcfFCP = new WritableCellFormat(wfcP);
//            
//            //标题 Label(行数,列数,内容对象,单元格对象)
//            //计算标题位置
//            int len = title_len/2;
//            Label labelCFCP = new Label(len,0,title_name,wcfFCP);
//            sheet.addCell(labelCFCP);
//            
//            //左侧信息、右侧信息
//            WritableCellFormat wcf_info = new WritableCellFormat(wfc1);
//            if(left_info!=null){
//                for(int i=0; i<left_info.length; i++){
//                	Label label_left 	= new Label(0,i+1,left_info[i], wcf_info);
//                	Label label_right 	= new Label(title_len-1,i+1,right_info[i],wcf_info);
//                	sheet.addCell(label_left);
//                	sheet.addCell(label_right);
//                }
//            }
//            
//            //字型设置
//            WritableFont fontL = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
//            
//            //居左
//            WritableCellFormat cellL = new WritableCellFormat(fontL);
//            cellL.setAlignment(Alignment.LEFT);
//            cellL.setVerticalAlignment(VerticalAlignment.CENTRE);
//            cellL.setBorder(Border.ALL, BorderLineStyle.THIN);
//            
//            //字型设置
//            WritableFont fontC = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
//           
//            //居中
//            WritableCellFormat cellC = new WritableCellFormat(fontC);
//            cellC.setAlignment(Alignment.CENTRE);
//            cellC.setVerticalAlignment(VerticalAlignment.CENTRE);
//            cellC.setBorder(Border.ALL, BorderLineStyle.THIN);
//            
//            //字型设置
//            WritableFont fontR = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
//            
//            //居右
//            WritableCellFormat cellR = new WritableCellFormat(fontR);
//            cellR.setAlignment(Alignment.RIGHT);
//            cellR.setVerticalAlignment(VerticalAlignment.CENTRE);
//            cellR.setBorder(Border.ALL, BorderLineStyle.THIN);
//            
//            //将表头循环导入文件中
//            int unit=0;
//            for(int i=0;i<table_head.length;i++){
//            	//标签
//                Label label = new Label(i,4,table_head[i][1],cellC);
//                sheet.addCell(label);
//                unit+=Integer.parseInt(table_head[i][3]);
//            }
//            
//            //将数据循环导入文件中
//            int count=left_info.length+1;
////            list.remove(0);
//            for(Iterator it = list.iterator();it.hasNext();){
//                Hashtable hash = (Hashtable)it.next();     
//                count++;
//            	for(int k=0;k<table_head.length;k++){                	
//                    String value = (String)hash.get(table_head[k][0]);
//                    if(value == null || value.length()==0)
//                        value = "";
//                    try{
//                        Number labelNF = null;
//                        switch(Integer.parseInt(table_head[k][4])){
//	                        case 0: // '\0'
//	                        	//Number(专栏对象,行数,Double型Value,单元格对象)
//	                            labelNF = new Number(k,count,Integer.parseInt(value),cellL);
//	                            break;
//	
//	                        case 1: // '\001'
//	                            labelNF = new Number(k,count,Integer.parseInt(value),cellC);
//	                            break;
//	
//	                        case 2: // '\002'
//	                            labelNF = new Number(k,count,Integer.parseInt(value),cellR);
//	                            break;
//	
//	                        default:
//	                            labelNF = new Number(k,count,Integer.parseInt(value),cellL);
//	                            break;
//                        }
//                        sheet.addCell(labelNF);
//                    }catch(Exception e1){
//                    	//标签
//                        Label label;
//                        switch(Integer.parseInt(table_head[k][4])){
//	                        case 0: // '\0'
//	                            label = new Label(k, count, value, cellL);
//	                            break;
//	
//	                        case 1: // '\001'
//	                            label = new Label(k, count, value, cellC);
//	                            break;
//	
//	                        case 2: // '\002'
//	                            label = new Label(k, count, value, cellR);
//	                            break;
//	
//	                        default:
//	                            label = new Label(k, count, value, cellL);
//	                            break;
//                        }
//                        sheet.addCell(label);
//                    }
//                }
//            }
//                
//            //循环设置专栏宽度
//            for(int i=0; i<table_head.length; i++){
//            	//setColumnView(将被格式化的专栏对象,专栏宽度)
//            	sheet.setColumnView(i,Integer.parseInt(table_head[i][3])*(length /unit));
//            }
//            //压入底部信息
//            Label label_bottom = new Label(0,(2+left_info.length)+list.size(),bottom_info, wcf_info);
//            sheet.addCell(label_bottom);
//            
//            //导入,并释放内存
//            workbook.write();
//            workbook.close();
//            
//            //写入数据
//            File t_file = new File(fname);
//            long l = t_file.length();
//            InputStream in = new FileInputStream(t_file);
//            if(in != null){
//                String fs = t_file.getName();
//                response.reset();
//                response.setContentType("application/x-msdownload");
//                String s = "attachment; filename=" + toUtf8String(fs);
//                response.setHeader("Content-Disposition", s);
//                response.setContentLength((int)l);
//                byte b[] = new byte[1024];
//                for(int len1 = 0; (len1 = in.read(b)) > 0;){
//                	response.getOutputStream().write(b, 0, len1);
//                }              
//                //释放内存	
//                in.close();
//            }
//            //删除服务器上的文件
//            t_file.delete();
//        }catch(Exception e){
//        	e.printStackTrace();
//            rst = 1;
//            return rst;
//        }
//        return rst;
    	return 0;
    }
    
    //中文转码
    private static String toUtf8String(String s) {
    	StringBuffer sb = new StringBuffer();
    	for (int i=0;i<s.length();i++) {
    	    char c = s.charAt(i);
    	    if (c >= 0 && c <= 255) {
    	    	sb.append(c);
    	    }else {
	    		byte[] b;
	    		try {
	    		    b = Character.toString(c).getBytes("utf-8");
	    		}catch (Exception ex) {
	    		    System.out.println(ex);
	    		    b = new byte[0];
	    		}
	    		for (int j = 0; j < b.length; j++) {
	    		    int k = b[j];
	    		    if (k < 0) k += 256;
	    		    sb.append("%" + Integer.toHexString(k).toUpperCase());
	    		}
    	    }
    	}
    	return sb.toString();
     }
    
	/**
	 * Excel导入信息
	 * String filePath	:	文件完整路径名   
	 * int    worksheet	:	读取的工作表，0为第一张工作表
	 * String start		:	开始读的行数
	 * int    length	:	读取的列数长度，从第一列开始
	 * String link		:	连接各个字段值的字符例如：，@   
	 * int maxRows		:	读取的最多行数   
	 * @return ArrayList[String]
	 */
    @SuppressWarnings("unchecked")
	public ArrayList readData(String filePath, int worksheet, int start,    
                              int length, String link, final int maxRows) throws IOException{    
    	
        ArrayList arrayList = new ArrayList();
        
        //判断文件是否存在
        if(filePath!=null && filePath.length()>0){
        	try {    
                File file = new File(filePath);    
                if (!file.exists()){    
                    System.out.println("Excel文件路径错误");   
                    return arrayList;    
                }    
            }catch (Exception e){        
                return arrayList;
            }	
        }
         
        FileInputStream fis = null;    
        try{    
            fis = new FileInputStream(filePath);    
            POIFSFileSystem fs = new POIFSFileSystem(fis);    
            HSSFWorkbook wb = new HSSFWorkbook(fs);    
            HSSFSheet sheet = wb.getSheetAt(worksheet);    
            //不存在worksheet    
            if (sheet == null){    
                System.out.println("Excel文件工作表不存在");    
                return arrayList;
            }    
            //所有的行数    
            int times = 0;    
            for (int i = start; i <= sheet.getLastRowNum()-1; i++){    
                //用于单元格内容为空时  
                String division = "";    
                //整行数据都不为空时，存储这一行的数据    
                StringBuffer rowValue = new StringBuffer("");    
                times++;    
                //超过读的行数    
                if (times > maxRows){    
                    break;    
                }    
                HSSFRow row = sheet.getRow(i);    
                //行值不为空    
                if (row != null){    
                    //所有的列数    
                    for (int j = 0; j <length; j++){    
                        HSSFCell cell = row.getCell((short) j);    
                        if (cell == null){        
                            rowValue.append(division);   
                            if(j<length-1)
                            	rowValue.append(link);      
                            continue;    
                        }    
                        switch (cell.getCellType()){    
                            //单元格内容是数字    
                            case HSSFCell.CELL_TYPE_NUMERIC:    
                                rowValue.append(new Double(cell.    
                                        getNumericCellValue()).intValue());     
                                break;    
                                //单元格内容是字符串    
                            case HSSFCell.CELL_TYPE_STRING:    
                                rowValue.append(cell.getStringCellValue());    
                                break;    
                            default:   
                                rowValue.append(division);    
                                break;   
                        }  
                        if(j<length-1)
                        	rowValue.append(link); 
                    }    
                    arrayList.add(rowValue.toString());    
                } 
            }    
        }catch (IOException e){     
            throw new IOException("IO Exception was found");    
        }finally{    
            // 关闭流    
            if (fis != null){    
                fis.close();    
            }    
        }    
        return arrayList;    
    }  
    
    
	/**
	 * Excel导入信息[特殊处理:如果有空值则以字符串"AAAA"替代]
	 * String filePath	:	文件完整路径名   
	 * int    worksheet	:	读取的工作表，0为第一张工作表
	 * String start		:	开始读的行数
	 * int    length	:	读取的列数长度，从第一列开始
	 * String link		:	连接各个字段值的字符例如：，@   
	 * int maxRows		:	读取的最多行数   
	 * @return ArrayList[String]
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList readExcelData(InputStream filePath, int worksheet, int start,    
                              int length, String link, final int maxRows,Object[][] obj) throws IOException{    
    	
        ArrayList arrayList =Lists.newArrayList();
        
        try{    
            POIFSFileSystem fs = new POIFSFileSystem(filePath);    
            HSSFWorkbook wb = new HSSFWorkbook(fs);    
            HSSFSheet sheet = wb.getSheetAt(worksheet);    
            //不存在worksheet    
            if (sheet == null){    
                System.out.println("Excel文件工作表不存在");    
                return arrayList;
            }    
            //所有的行数    
            for (int i = start; i <= sheet.getLastRowNum(); i++){    
                HSSFRow row = sheet.getRow(i);    
                //行值不为空    
                if (row != null){  
                	Map map=Maps.newLinkedHashMap();
                    //所有的列数    
                    for (int j = 0; j <length; j++){    
                    	Object object="";
                        HSSFCell cell = row.getCell(j);
                        if(cell!=null){
	                        switch (cell.getCellType()){    
	                            //单元格内容是数字    
	                            case HSSFCell.CELL_TYPE_NUMERIC:    
	                            	object=new Integer((int) cell.getNumericCellValue());
	                                break;    
	                                //单元格内容是字符串    
	                            case HSSFCell.CELL_TYPE_STRING:    
	                            	object=new String(cell.getStringCellValue());    
	                                break;    
	                            default:   
	                                break;   
	                        }
                        }
                        map.put(obj[j][0], object);
                    }    
                    arrayList.add(map);    
                } 
            }    
        }catch (IOException e){     
            throw new IOException("IO Exception was found");    
        }finally{    
            // 关闭流    
            if (filePath != null){    
            	filePath.close();    
            }    
        }    
        return arrayList;    
    }  
    public ArrayList readExcelDataS(InputStream filePath, int worksheet, int start,    
            int length, String link, final int maxRows) throws IOException{    

		ArrayList arrayList =Lists.newArrayList();
		try{    
		POIFSFileSystem fs = new POIFSFileSystem(filePath);    
		HSSFWorkbook wb = new HSSFWorkbook(fs);    
		HSSFSheet sheet = wb.getSheetAt(worksheet);    
		//不存在worksheet    
		if (sheet == null){    
		System.out.println("Excel文件工作表不存在");    
		return arrayList;
		}    
		//所有的行数    
		for (int i = start; i <= sheet.getLastRowNum(); i++){    
		HSSFRow row = sheet.getRow(i);    
		//行值不为空    
		if (row != null){  
			Vector v=new Vector();
		  //所有的列数    
		  for (int j = 0; j <length; j++){    
		  	Object object="";
		      HSSFCell cell = row.getCell(j);
		      if(cell!=null){
		          switch (cell.getCellType()){    
		              //单元格内容是数字    
		              case HSSFCell.CELL_TYPE_NUMERIC:    
		              	object=new Double(cell.getNumericCellValue());
		                  break;    
		                  //单元格内容是字符串    
		              case HSSFCell.CELL_TYPE_STRING:    
		              	object=new String(cell.getStringCellValue());    
		                  break;    
		              default:   
		                  break;   
		          }
		      }
		      v.addElement(object);
		  }    
		  arrayList.add(v);    
		} 
		}    
		}catch (IOException e){     
		throw new IOException("IO Exception was found");    
		}finally{    
		// 关闭流    
		if (filePath != null){    
		filePath.close();    
		}    
		}    
		return arrayList;    
	} 
    public static void main(String[] args) {
//    	String filePath="D:/2.xls";
//		try {
//			Object[][] eUserinfo={
//					{"userName","姓名","user_name"},
//					{"fatherName","父亲名称","user_info_id"},
//					{"userAge","年龄","user_age"},
//					{"userIdentityCard","身份证号","user_identityCard"},
//					{"userSex","性别","user_sex"},
//					{"userQq","QQ号码","user_qq"},
//					{"userWeixin","微信号码","user_weixin"},
//					{"userEmail","邮箱地址","user_email"},
//					{"userPhone","手机号码","user_phone"},
//					{"userBornDate","出生日期","user_born_date"},
//					{"maritalStatus","婚姻状况","marital_status"},
//					{"spouseName","配偶名称","spouse_name"},
//					{"homeAddress","家庭地址","home_address"},
//					{"tribalRegion","宗族地区","tribal_region"},
//					{"celebrityFlag","是否名人","celebrity_flag"},
//					{"userEnabled","是否禁用","user_enable"},
////					{"headImageFolder","头像","head_image_folder"},
////					{"userFlag","状态","user_flag"},
//					{"userAccount","登录账号","user_account"},
//					{"userPassword","登录账号","user_password"},
//					{"userDesc","个人简介","user_desc"},
//					{"remarks","备注","remarks"},
//			};
//			List<Map<String,Object>> list=new ExcelInOutImpl().readExcelData(new FileInputStream(filePath), 0, 1, eUserinfo.length, ",", 100, eUserinfo);
//			UserInfo userInfo=new UserInfo();
//			UserInfo fatherUserInfo=new UserInfo();
//			PubUser pubUser=new PubUser();
//			Class clnUserInfo=userInfo.getClass();
//			Field[] fields=clnUserInfo.getDeclaredFields();
//			for (Map<String,Object> map : list) {
//				for (int i = 0; i < eUserinfo.length; i++) {
//					Object fieldVal=map.get(eUserinfo[i][0]);
//					for (int j = 0; j < fields.length; j++) {
//						fields[j].setAccessible(true);
//						String type=String.valueOf(fields[j].getType());
//						String field=fields[j].getName();
//						//族员信息
//						if(eUserinfo[i][0].equals(field)){
//							if(type.endsWith("Integer")||type.endsWith("int")){
//								fields[j].set(userInfo, Integer.parseInt(StringUtil.isEmpty(fieldVal)?"0":String.valueOf(fieldVal)));
//							}else if(type.endsWith("Date")){
//								fields[j].set(userInfo, StringUtil.isEmpty(fieldVal)?null:String.valueOf(fieldVal));
//							}else{
//								fields[j].set(userInfo, String.valueOf(fieldVal));
//							}
//						}
//						//族员父亲
//						if("fatherName".equals(field)){
//							fatherUserInfo.setUserName(String.valueOf(fieldVal));
//						}
//						//账号
//						if("userAccount".equals(field)){
//							pubUser.setUserAccount(String.valueOf(fieldVal));
//						}
//						if("userPassword".equals(field)){
//							pubUser.setUserPassword(String.valueOf(fieldVal));
//						}
//						
//					}
//				}
//				System.out.println(map);
//			}
			
//		} catch (IOException | IllegalArgumentException | IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
