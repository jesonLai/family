package sys.springframework.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import sys.util.func.UtilFactory;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    protected final Log _Log = LogFactory.getLog(getClass());
    private String defaultFrontTargetUrl; // 前台登录成功的跳转地址
    private String defaultBackTargetUrl; // 后台登录成功的跳转地址

    // set方法 spring调用
    public void setDefaultFrontTargetUrl(String defaultFrontTargetUrl) {
	this.defaultFrontTargetUrl = defaultFrontTargetUrl;
    }

    public void setDefaultBackTargetUrl(String defaultBackTargetUrl) {
	this.defaultBackTargetUrl = defaultBackTargetUrl;
    }

    public LoginSuccessHandler() {
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	    Authentication authentication) throws IOException, ServletException {
	_Log.debug(">>>>>>>>>>>>>>>>>>>onAuthenticationSuccess()<<<<<<<<<<<<<<<<");
	String loginType = UtilFactory.getSpringContext().getUserInfoContext().getFrontOrBack(); // 获取登录状态，这个值是在前面判断登录入口放入session的
	_Log.debug(">>>>>>>>>>>>>>>>>>>loginType[" + loginType + "]<<<<<<<<<<<<<<<<");
	_Log.debug(">>>>>>>>>>>>>>>>>>>defaultBackTargetUrl[" + this.defaultBackTargetUrl + "]<<<<<<<<<<<<<<<<");
	if ("back".equals(loginType)) {
	    setDefaultTargetUrl(this.defaultBackTargetUrl); // 如果是后台，前后台跳转地址设置到defaultFailureUrl
	} else if ("front".equals(loginType)) {
	    setDefaultTargetUrl(this.defaultFrontTargetUrl); // 如果是前台，与后台类似
	}
	handle(request, response, authentication);
	clearAuthenticationAttributes(request);

    }
}
