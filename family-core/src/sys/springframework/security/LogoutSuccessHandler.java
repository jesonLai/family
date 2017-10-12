package sys.springframework.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import sys.util.func.UtilFactory;

public class LogoutSuccessHandler implements LogoutHandler {
    protected final Log _Log = LogFactory.getLog(getClass());

    public LogoutSuccessHandler() {
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
	_Log.debug(">>>>>>>>>>>>>>>>>>>logout()<<<<<<<<<<<<<<<<");
	_Log.debug("CustomLogoutSuccessHandler.logout() is called!" + request.getRequestURI() + "=="
		+ UtilFactory.getSpringContext().getUserInfoContext().getFrontOrBack());
	// HttpServletRequest request = (HttpServletRequest) req;
	// HttpServletResponse response = (HttpServletResponse) res;

	// String loginType =
	// UtilFactory.getSpringContext().getUserInfoContext().getFrontOrBack();
	// //获取登录状态，这个值是在前面判断登录入口放入session的
	// if ("back".equals(loginType)) {
	// urlLogoutSuccessHandler.setDefaultTargetUrl(this.backLogoutSuccessUrl);
	// } else {
	// urlLogoutSuccessHandler.setDefaultTargetUrl(this.frontLogoutSuccessUrl);
	// }
	// logoutSuccessHandler = urlLogoutSuccessHandler;
	// //前临时变量赋值给LogoutSuccessHandler，供后面代码调用
	// if (requiresLogout(request, response)) {
	// Authentication auth = SecurityContextHolder.getContext()
	// .getAuthentication();
	//
	// if (logger.isDebugEnabled()) {
	// logger.debug("Logging out user '" + auth + "' and transferring to
	// logout destination");
	// }
	//
	// for (LogoutHandler handler : handlers) {
	// handler.logout(request, response, auth);
	// }
	//
	// logoutSuccessHandler.onLogoutSuccess(request, response, auth);

    }
}
