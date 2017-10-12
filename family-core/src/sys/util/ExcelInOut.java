package sys.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Excel导出、导入
 * Create Date:2009-12-13
 * Author:chery
 */
public interface ExcelInOut {

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
	@SuppressWarnings("unchecked")
	public int outExcel(  String    file_name, 
	                      String    title_name,
	                      int		title_len,				
	                      String    left_info[],			
	                      String    right_info[],			
	                      String    bottom_info,			
	                      String    table_head[][],
	                      ArrayList list);
	
	/**
	 * Excel导入信息
	 * String filePath	:	文件完整路径名   
	 * int    worksheet	:	读取的工作表，0为第一张工作表
	 * String start		:	开始读的行数
	 * int    length	:	读取的列数长度，从第一列开始
	 * String link		:	连接各个字段值的字符例如：，@   
	 * int maxRows	:	读取的最多行数   
	 * @return ArrayList[String]
	 */
    @SuppressWarnings("unchecked")
	public ArrayList readData(String filePath, int worksheet, int start,    
                              int length, String link, final int maxRows) throws IOException;
    
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
    @SuppressWarnings("unchecked")
	public ArrayList readExcelData(InputStream filePath, int worksheet, int start,    
                              int length, String link, final int maxRows,Object[][] obj) throws IOException;
    @SuppressWarnings("unchecked")
	public ArrayList readExcelDataS(InputStream filePath, int worksheet, int start,    
                              int length, String link, final int maxRows) throws IOException;
    
    
}
