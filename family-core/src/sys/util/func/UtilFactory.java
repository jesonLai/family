package sys.util.func;


import sys.springframework.security.service.impl.SpringContextHolderServiceImpl;
import sys.util.ExcelInOut;
import sys.util.Interface.SysDate;
import sys.util.impl.ExcelInOutImpl;
import sys.util.impl.SysDateImpl;

public final class UtilFactory {
	private static SpringContextHolderServiceImpl springContextHolderService=new SpringContextHolderServiceImpl();
	private static ExcelInOut excelInOut=new ExcelInOutImpl();

	public static SpringContextHolderServiceImpl getSpringContext(){
		return springContextHolderService;
	}
	/**
	 * 日期函数
	 */
	public static SysDate getSysDate(){
		return new SysDateImpl();
	}
	/**
	 * excel
	 */
	public static ExcelInOut getSysExcel(){
		return excelInOut;
	}

}
