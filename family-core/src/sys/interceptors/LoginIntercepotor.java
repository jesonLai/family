package sys.interceptors;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import sys.model.PubUser;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository
public class LoginIntercepotor extends HandlerInterceptorAdapter {
	private List<String> excludeListUris;
	

	public List<String> getExcludeListUris() {
		return excludeListUris;
	}


	public void setExcludeListUris(List<String> excludeListUris) {
		this.excludeListUris = excludeListUris;
	}


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PubUser userContext = UtilFactory.getSpringContext().getUserInfoContext().getUser();
		String requestUri= request.getRequestURI();
		for (String uri : excludeListUris) {
			if(requestUri.endsWith(uri)) return super.preHandle(request, response, handler);
		}
		if(StringUtil.isEmpty(userContext)){
					// 未登录
					PrintWriter out = response.getWriter();
					StringBuilder builder = new StringBuilder();
					builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
					builder.append("alert(\"页面过期，请重新登录\");");
					builder.append("window.top.location.href=\"");
					builder.append("sessionTimeOut.jsp\";</script>");
					out.print(builder.toString());
					out.close();
					return false;
				} else {
					// 添加日志
//					String operateContent = Constants.operateContent(uri);
//					if (null != operateContent) {
//						String url = uri.substring(uri.indexOf("background"));
//						String ip = request.getRemoteAddr();
//						Integer userId = ((SystemUserForm) obj).getId();
//						SystemLoggerForm form = new SystemLoggerForm();
//						form.setUserId(userId);
//						form.setIp(ip);
//						form.setOperateContent(operateContent);
//						form.setUrl(url);
//						this.systemLoggerService.edit(form);
//					}
//				}
//			}
		}

		Map paramsMap = request.getParameterMap();

//		for (Iterator<Map.Entry> it = paramsMap.entrySet().iterator(); it.hasNext();) {
//			Map.Entry entry = it.next();
//			Object[] values = (Object[]) entry.getValue();
//			for (Object obj : values) {
//				if (!DataUtil.isValueSuccessed(obj)) {
//					throw new RuntimeException("有非法字符：" + obj);
//				}
//			}
//		}

		return super.preHandle(request, response, handler);
	}

}
