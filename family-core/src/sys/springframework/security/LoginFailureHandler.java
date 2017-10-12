package sys.springframework.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import sys.util.func.UtilFactory;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    protected final Log _Log = LogFactory.getLog(getClass());

    private String defaultFailureUrl;
    private boolean forwardToDestination = false;
    private boolean allowSessionCreation = true;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private String defaultBackTargetUrl; // 定义后台登录验证失败跳转地址
    private String defaultFrontTargetUrl; // 定义前台登录验证失败跳转地址
    // 定义set方法

    public void setDefaultFrontTargetUrl(String defaultFrontTargetUrl) {
	this.defaultFrontTargetUrl = defaultFrontTargetUrl;
    }

    public void setDefaultBackTargetUrl(String defaultBackTargetUrl) {
	this.defaultBackTargetUrl = defaultBackTargetUrl;
    }

    public LoginFailureHandler() {
    }

    public LoginFailureHandler(String defaultFailureUrl) {
	setDefaultFailureUrl(defaultFailureUrl);
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	    AuthenticationException exception) throws IOException, ServletException {

	saveException(request, exception);
	setFailureTarget(request); // 调用前后台判断方法
	_Log.debug(">>>>>>>>>>>>>>>>>>>forwardToDestination[" + forwardToDestination + "]<<<<<<<<<<<<<<<<");
	if (forwardToDestination) {
	    logger.debug("Forwarding to " + defaultFailureUrl);
	    request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	} else {
	    logger.debug("Redirecting to " + defaultFailureUrl);
	    redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
	}
    }

    protected final void setFailureTarget(HttpServletRequest request) {
	_Log.debug(">>>>>>>>>>>>>>>>>>>setFailureTarget()<<<<<<<<<<<<<<<<");
	String loginType = UtilFactory.getSpringContext().getUserInfoContext().getFrontOrBack(); // 获取登录状态，这个值是在前面判断登录入口放入session的
	_Log.debug(">>>>>>>>>>>>>>>>>>>loginType[" + loginType + "]<<<<<<<<<<<<<<<<");
	if ("back".equals(loginType)) {
	    setDefaultFailureUrl(this.defaultBackTargetUrl); // 如果是后台，前后台跳转地址设置到defaultFailureUrl
	} else {
	    setDefaultFailureUrl(this.defaultFrontTargetUrl); // 如果是前台，与后台类似
	}
    }
    //
    // protected final void saveException(HttpServletRequest
    // request,AuthenticationException exception) {
    // if (forwardToDestination) {
    // request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
    // exception);
    // } else {
    // HttpSession session = request.getSession(false);
    //
    // if (session != null || allowSessionCreation) {
    // request.getSession().setAttribute(
    // WebAttributes.AUTHENTICATION_EXCEPTION, exception);
    // }
    // }
    // }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
	// Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), "'"
	// + defaultFailureUrl + "' is not a valid redirect URL");
	this.defaultFailureUrl = defaultFailureUrl;
    }

    protected boolean isUseForward() {
	return forwardToDestination;
    }

    public void setUseForward(boolean forwardToDestination) {
	this.forwardToDestination = forwardToDestination;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
	this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
	return redirectStrategy;
    }

    protected boolean isAllowSessionCreation() {
	return allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
	this.allowSessionCreation = allowSessionCreation;
    }

}
