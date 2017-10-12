package sys.springframework.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import sys.model.PubUser;
import sys.springframework.security.util.MyHttpServletResponse;
import sys.util.StringUtil;
import sys.util.WebMethod;
import sys.util.func.UtilFactory;

public class MyAccessDeniedHandler implements AccessDeniedHandler {
    protected final Log _Log = LogFactory.getLog(getClass());
    private String errorPage;
    private String loginPage;
    private static final String ACCESS_DENIED_440 = "SPRING_SECURITY_440_EXCEPTION";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
	    AccessDeniedException accessDeniedException) throws IOException, ServletException, AuthenticationException {
	_Log.debug(">>>>>>>>>>>>>>>>>>>handle()<<<<<<<<<<<<<<<<");
	boolean isAjax = WebMethod.isAjaxRequest(request);
	PubUser userContext = UtilFactory.getSpringContext().getUserInfoContext().getUser();
	String url = request.getRequestURI();

	if (isAjax) {
	    // Message msg = MessageManager.exception(accessDeniedException);
	    // ControllerTools.print(response, msg);
	    request.setAttribute(ACCESS_DENIED_440, accessDeniedException);
	    response.setStatus(MyHttpServletResponse.SC_HTTP_SESSION_FAILURE);

	    // forward to error page.
	    // RequestDispatcher dispatcher =
	    // request.getRequestDispatcher("/admin/login.html");
	    // dispatcher.forward(request, response);
	} else if (!response.isCommitted()) {
	    if (errorPage != null) {
		if (StringUtil.isFrontOrBack(url)) {
		    request.getSession().setAttribute("SECURITY_LOGIN_EXCEPTION", accessDeniedException.getMessage());
		    errorPage = loginPage;
		}
		// Put exception into request scope (perhaps of use to a view)
		request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

		// Set the 403 status code.
		response.setStatus(MyHttpServletResponse.SC_FORBIDDEN);

		// forward to error page.
		RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
		dispatcher.forward(request, response);
	    } else {
		response.sendError(MyHttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
	    }
	} else {
	    throw new AuthenticationServiceException("未知请求!");
	}
	if (StringUtil.isEmpty(userContext)) {
	    response.setStatus(MyHttpServletResponse.SC_HTTP_SESSION_FAILURE);
	} else {
	    response.setStatus(MyHttpServletResponse.SC_FORBIDDEN);
	}
    }

    public void setErrorPage(String errorPage) {
	if ((errorPage != null) && !errorPage.startsWith("/")) {
	    throw new IllegalArgumentException("errorPage must begin with '/'");
	}

	this.errorPage = errorPage;
    }

    public void setLoginPage(String loginPage) {
	if ((loginPage != null) && !loginPage.startsWith("/")) {
	    throw new IllegalArgumentException("loginPage must begin with '/'");
	}

	this.loginPage = loginPage;
    }

}
