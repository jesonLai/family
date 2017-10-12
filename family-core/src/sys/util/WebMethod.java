package sys.util;

import javax.servlet.http.HttpServletRequest;

public class WebMethod {
	/**
	 * 判断请求是否为AJAX
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
	    return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
	}
}
