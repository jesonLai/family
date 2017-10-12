package sys.interceptors;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import sys.model.PubUser;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;
//前后台拦截
public class FrontAndBackTargetUrl extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PubUser userContext = UtilFactory.getSpringContext().getUserInfoContext().getUser();
		String requestUri= request.getRequestURI();
//		标识前后台
		if (requestUri.indexOf("admin") != -1) 
			request.getSession().setAttribute("loginType", "back");	
		else 
			request.getSession().setAttribute("loginType", "front");

		return super.preHandle(request, response, handler);
	}
}
