package sys.springframework.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import sys.util.StringUtil;
import sys.util.func.UtilFactory;

public class LogoutFilter extends GenericFilterBean {
    protected final Log _Log = LogFactory.getLog(getClass());
    private String filterProcessesUrl = "/logout";
    private final List<LogoutHandler> handlers;
    private LogoutSuccessHandler logoutSuccessHandler;
    private SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler; // 作临时变量用

    private String frontLogoutSuccessUrl; // 前台登出成功跳转页面
    private String backLogoutSuccessUrl; // 后台登出成功跳转页面

    public LogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler[] handlers) {
	_Log.debug(">>>>>>>>>>>>>>>>>>>LogoutFilter()LogoutSuccessHandler/LogoutHandler。。。<<<<<<<<<<<<<<<<");
	_Log.debug(">>>>>>>>>>>>>>>>>>>logoutSuccessHandler[" + logoutSuccessHandler + "]<<<<<<<<<<<<<<<<");
	Assert.notEmpty(handlers, "LogoutHandlers are required");
	this.handlers = Arrays.asList(handlers);
	Assert.notNull(logoutSuccessHandler, "logoutSuccessHandler cannot be null");
	this.logoutSuccessHandler = logoutSuccessHandler;
    }

    public LogoutFilter(String frontLogoutSuccessUrl, String backLogoutSuccessUrl, LogoutHandler[] handlers) {
	_Log.debug(">>>>>>>>>>>>>>>>>>>frontLogoutSuccessUrl[" + frontLogoutSuccessUrl + "]<<<<<<<<<<<<<<<<");
	_Log.debug(">>>>>>>>>>>>>>>>>>>backLogoutSuccessUrl[" + backLogoutSuccessUrl + "]<<<<<<<<<<<<<<<<");
	Assert.notEmpty(handlers, "LogoutHandlers are required");
	this.handlers = Arrays.asList(handlers);
	_Log.debug(">>>>>>>>>>>>>>>>>>>LogoutFilter()String[" + frontLogoutSuccessUrl + "]/String["
		+ backLogoutSuccessUrl + "]/LogoutHandler。。。<<<<<<<<<<<<<<<<");
	Assert.isTrue(
		!StringUtils.hasLength(frontLogoutSuccessUrl) || UrlUtils.isValidRedirectUrl(frontLogoutSuccessUrl),
		frontLogoutSuccessUrl + " isn't a valid redirect URL");
	Assert.isTrue(!StringUtils.hasLength(backLogoutSuccessUrl) || UrlUtils.isValidRedirectUrl(backLogoutSuccessUrl),
		backLogoutSuccessUrl + " isn't a valid redirect URL");

	SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
	if (StringUtils.hasText(frontLogoutSuccessUrl) && StringUtils.hasText(backLogoutSuccessUrl)) {
	    this.frontLogoutSuccessUrl = frontLogoutSuccessUrl;
	    this.backLogoutSuccessUrl = backLogoutSuccessUrl;
	}
	this.urlLogoutSuccessHandler = urlLogoutSuccessHandler;// 赋值给临时变量，以便于后面判断是前台登录还是后台登录
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
	    throws IOException, ServletException {
	_Log.debug(">>>>>>>>>>>>>>>>>>>doFilter()--获取登录状态<<<<<<<<<<<<<<<<");
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) res;

	String loginType = UtilFactory.getSpringContext().getUserInfoContext().getFrontOrBack(); // 获取登录状态，这个值是在前面判断登录入口放入session的
	_Log.debug(">>>>>>>>>>>>>>>>>>>loginType[" + loginType + "]<<<<<<<<<<<<<<<<");
	if ("back".equals(loginType) || StringUtil.isFrontOrBack(request.getRequestURI())) {
	    urlLogoutSuccessHandler.setDefaultTargetUrl(this.backLogoutSuccessUrl);
	} else {
	    urlLogoutSuccessHandler.setDefaultTargetUrl(this.frontLogoutSuccessUrl);
	}
	logoutSuccessHandler = urlLogoutSuccessHandler; // 前临时变量赋值给LogoutSuccessHandler，供后面代码调用
	_Log.debug(">>>>>>>>>>>>>>>>>>>logoutSuccessHandler[" + logoutSuccessHandler + "]<<<<<<<<<<<<<<<<");
	_Log.debug(">>>>>>>>>>>>>>>>>>>requestURL[" + request.getRequestURL() + "]<<<<<<<<<<<<<<<<");
	if (requiresLogout(request, response)) {
	    _Log.debug(">>>>>>>>>>>>>>>>>>>退出系统<<<<<<<<<<<<<<<<");
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    if (logger.isDebugEnabled()) {
		logger.debug("Logging out user '" + auth + "' and transferring to logout destination");
	    }

	    for (LogoutHandler handler : handlers) {
		handler.logout(request, response, auth);
	    }

	    logoutSuccessHandler.onLogoutSuccess(request, response, auth);
	    // request.getSession(true);
	    return;
	}

	chain.doFilter(request, response);
    }

    protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
	String uri = request.getRequestURI();
	int pathParamIndex = uri.indexOf(';');

	if (pathParamIndex > 0) {
	    uri = uri.substring(0, pathParamIndex);
	}

	int queryParamIndex = uri.indexOf('?');

	if (queryParamIndex > 0) {
	    uri = uri.substring(0, queryParamIndex);
	}

	if ("".equals(request.getContextPath())) {
	    return uri.endsWith(filterProcessesUrl);
	}

	return uri.endsWith(request.getContextPath() + filterProcessesUrl);
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
	Assert.isTrue(UrlUtils.isValidRedirectUrl(filterProcessesUrl),
		filterProcessesUrl + " isn't a valid value for" + " 'filterProcessesUrl'");
	this.filterProcessesUrl = filterProcessesUrl;
    }

    protected String getFilterProcessesUrl() {
	return filterProcessesUrl;
    }
}
