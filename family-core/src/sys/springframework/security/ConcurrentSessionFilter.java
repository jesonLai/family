package sys.springframework.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import sys.util.StringUtil;
import sys.util.func.UtilFactory;

public class ConcurrentSessionFilter extends GenericFilterBean {
    protected final Log _Log = LogFactory.getLog(getClass());
    private SessionRegistry sessionRegistry;
    private String expiredUrl;
    private LogoutHandler[] handlers = { new SecurityContextLogoutHandler() };
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public ConcurrentSessionFilter(SessionRegistry sessionRegistry) {
	Assert.notNull(sessionRegistry, "SessionRegistry required");
	this.sessionRegistry = sessionRegistry;
    }

    public ConcurrentSessionFilter(SessionRegistry sessionRegistry, String expiredUrl) {
	Assert.notNull(sessionRegistry, "SessionRegistry required");
	Assert.isTrue((expiredUrl == null) || (UrlUtils.isValidRedirectUrl(expiredUrl)),
		expiredUrl + " isn't a valid redirect URL");

	this.sessionRegistry = sessionRegistry;
	this.expiredUrl = expiredUrl;
    }

    public void afterPropertiesSet() {
	_Log.debug(">>>>>>>>>>>>>>>>>>>afterPropertiesSet()<<<<<<<<<<<<<<<<");
	Assert.notNull(this.sessionRegistry, "SessionRegistry required");
	Assert.isTrue((this.expiredUrl == null) || (UrlUtils.isValidRedirectUrl(this.expiredUrl)),
		this.expiredUrl + " isn't a valid redirect URL");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
	    throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) res;
	// String path = request.getContextPath();
	// String basePath = request.getScheme() + "://" +
	// request.getServerName() + ":" + request.getServerPort() + path;

	HttpSession session = request.getSession(false);
	// PubUser
	// userContext=UtilFactory.getSpringContext().getUserInfoContext().getUser();
	String loginType = UtilFactory.getSpringContext().getUserInfoContext().getFrontOrBack();
	if (session != null) {
	    SessionInformation info = this.sessionRegistry.getSessionInformation(session.getId());

	    if (info != null) {
		if (info.isExpired()) {
		    doLogout(request, response);

		    String targetUrl = determineExpiredUrl(request, info);

		    if (targetUrl != null) {
			this.redirectStrategy.sendRedirect(request, response, targetUrl);

			return;
		    }

		    response.getWriter().print(
			    "This session has been expired (possibly due to multiple concurrent logins being attempted as the same user).");

		    response.flushBuffer();

		    return;
		}

		this.sessionRegistry.refreshLastRequest(info.getSessionId());
	    }

	}
	chain.doFilter(request, response);
    }

    protected String determineExpiredUrl(HttpServletRequest request, SessionInformation info) {
	_Log.debug(">>>>>>>>>>>>>>>>>>>determineExpiredUrl()<<<<<<<<<<<<<<<<");
	String url = request.getRequestURI();
	_Log.debug(">>>>>>>>>>>>>>>>>>>url[" + url + "]<<<<<<<<<<<<<<<<");
	if (StringUtil.isFrontOrBack(url))
	    this.expiredUrl = "/admin/login.html";
	else
	    this.expiredUrl = "login.html";
	return this.expiredUrl;
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) {
	_Log.debug(">>>>>>>>>>>>>>>>>>>doLogout()<<<<<<<<<<<<<<<<");
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	for (LogoutHandler handler : this.handlers)
	    handler.logout(request, response, auth);
    }

    public void setLogoutHandlers(LogoutHandler[] handlers) {
	Assert.notNull(handlers);
	this.handlers = handlers;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
	this.redirectStrategy = redirectStrategy;
    }
}
