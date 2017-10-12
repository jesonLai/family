package sys.admin.action;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import sys.BaseHint;


public class BaseAction implements BaseHint{
	protected static Hashtable<String,Object> ht;
	static{
		 ht=new Hashtable<String,Object>();
		 ht.put("result",RESULT_FALSE);
	}
	public static String getBasePath(HttpServletRequest request,String filePath){
		 String path = request.getContextPath();
		 String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"+filePath;
		return basePath;
	}
	public static String getBasePath(HttpServletRequest request){
		 String path = request.getContextPath();
		 String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		return basePath;
	}
	
	
	
}
