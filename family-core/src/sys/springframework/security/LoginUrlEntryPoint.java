package sys.springframework.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import sys.util.StringUtil;
import sys.util.func.UtilFactory;

public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {
    protected final Log _Log = LogFactory.getLog(getClass());

    public LoginUrlEntryPoint(String loginFormUrl) {
	super(loginFormUrl);
    }

    public void commence(HttpServletRequest request, HttpServletResponse response,
	    AuthenticationException authException) throws IOException, ServletException {
	_Log.debug(">>>>>>>>>>>>>>>>>>>commence()<<<<<<<<<<<<<<<<");
	String url = request.getRequestURI();
	_Log.debug(">>>>>>>>>>>>>>>>>>>url[" + url + "]<<<<<<<<<<<<<<<<");
	String frontOrBack = "";
	if (StringUtil.isFrontOrBack(url)) {
	    frontOrBack = "back";
	} else {
	    frontOrBack = "front";
	}
	UtilFactory.getSpringContext().getUserInfoContext().setFrontOrBack(frontOrBack);
	_Log.debug(">>>>>>>>>>>>>>>>>>>frontOrBack[" + frontOrBack + "]<<<<<<<<<<<<<<<<");
	super.commence(request, response, authException);
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
	    AuthenticationException exception) {
	String url = request.getRequestURI();
	_Log.debug(">>>>>>>>>>>>>>>>>>>url[" + url + "]<<<<<<<<<<<<<<<<");
	return StringUtil.isFrontOrBack(url) ? request.getServletContext().getInitParameter("back_Login_Page")
		: getLoginFormUrl();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
	_Log.debug(">>>>>>>>>>>>>>>>>>>afterPropertiesSet()<<<<<<<<<<<<<<<<");

    }
}
