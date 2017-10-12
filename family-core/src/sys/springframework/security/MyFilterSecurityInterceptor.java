package sys.springframework.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 负责过滤url请求
 * 
 * @author lxr
 *
 */
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    protected final Log _Log = LogFactory.getLog(getClass());
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    /**
     * 解析权限配置
     */
    public void afterPropertiesSet() throws Exception {
	_Log.debug(">>>>>>>>>>>>>>>>>>>afterPropertiesSet()<<<<<<<<<<<<<<<<");
	if (_Log.isDebugEnabled()) {
	    _Log.debug(">>>>>>>>>>>>>>>>>>>FilterInvocationSecurityMetadataSource["
		    + filterInvocationSecurityMetadataSource.getAllConfigAttributes() + "]<<<<<<<<<<<<<<<<");
	}
	super.afterPropertiesSet();
    }

    @Override
    public void destroy() {
	_Log.debug(">>>>>>>>>>>>>>>>>>>destroy()<<<<<<<<<<<<<<<<");
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
	    throws IOException, ServletException {
	_Log.debug(">>>>>>>>>>>>>>>>>>>doFilter()<<<<<<<<<<<<<<<<");
	FilterInvocation fi = new FilterInvocation(arg0, arg1, arg2);
	invoke(fi);
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	authentication.getDetails();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
	_Log.debug(">>>>>>>>>>>>>>>>>>>init()filterConfig[" + filterConfig + "]<<<<<<<<<<<<<<<<");

    }

    @Override
    public Class<?> getSecureObjectClass() {
	return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
	return this.filterInvocationSecurityMetadataSource;
    }

    // 自定义的方法
    public void invoke(FilterInvocation fi) throws IOException, ServletException {

	_Log.debug(">>>>>>>>>>>>>>>>>>>invoke()FilterInvocation[" + fi.toString() + "]<<<<<<<<<<<<<<<<");
	InterceptorStatusToken token = super.beforeInvocation(fi);
	_Log.debug(">>>>>>>>>>>>>>>>>>>InterceptorStatusToken[" + token + "]<<<<<<<<<<<<<<<<");
	try {
	    fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
	} finally {
	    Object returnedObject = super.afterInvocation(token, null);
	    _Log.debug(">>>>>>>>>>>>>>>>>>>" + returnedObject + "<<<<<<<<<<<<<<<<");
	}
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
	return filterInvocationSecurityMetadataSource;
    }

    public void setSecurityMetadataSource(
	    FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
	_Log.debug(">>>>>>>>>>>>>>>>>>>filterInvocationSecurityMetadataSource[" + filterInvocationSecurityMetadataSource
		+ "]<<<<<<<<<<<<<<<<");
	this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
    }

}