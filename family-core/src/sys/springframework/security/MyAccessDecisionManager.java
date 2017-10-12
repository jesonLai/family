package sys.springframework.security;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 负责权限的控制，如果请求的url在权限集合中有这个url对应的值，则放行 注：如果数据库中没有对这个url定义访问的权限，默认是会被放行的
 * 
 * @author lxr
 *
 */
public class MyAccessDecisionManager implements AccessDecisionManager {
    protected final Log _Log = LogFactory.getLog(getClass());
    // In this method, need to compare authentication with configAttributes.
    // 1, A object is a URL, a filter was find permission configuration by this
    // URL, and pass to here.
    // 2, Check authentication has attribute in permission configuration
    // (configAttributes)
    // 3, If not match corresponding authentication, throw a
    // AccessDeniedException.

    // 这个方法在url请求时才会调用，服务器启动时不会执行这个方法，前提是需要在<http>标签内设置 <custom-filter>标签
    /*
     * 参数说明： 1、configAttributes 装载了请求的url允许的角色数组
     * 。这里是从MySecurityMetadataSource里的loadResourceDefine方法里的atts对象取出的角色数据赋予给了configAttributes对象
     * 2、authentication 装载了从数据库读出来的角色
     * 数据。这里是从MyUserDetailsService里的loadUserByUsername方法里的auths对象的值传过来给
     * authentication 对象
     * 
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
	    throws AccessDeniedException, InsufficientAuthenticationException {
	_Log.debug(">>>>>>>>>>>>>>>>>>>decide()<<<<<<<<<<<<<<<<");
	/*
	 * authentication装载了用户的信息数据，其中有角色。
	 * 是MyUserDetailsService里的loadUserByUsername方法的userdetail对象传过来的
	 * userdetail一共有7个参数（下面打印出来的数据可对应一下security的User类，这个类可以看到有寻7个参数），
	 * 最后一个是用来保存角色数据的，如果角色为空，将无权访问页面。 看到下面的打印数据， Granted Authorities:
	 * ROLE_ADMIN，ROLE_ADMIN 就是角色了。 如果显示Not granted any
	 * authorities，则说明userdetail的最后一个参数为空，没有传送角色的值过来
	 * 
	 * 打印出的数据： auth:org.springframework.security.authentication.
	 * UsernamePasswordAuthenticationToken@bf5fbace: Principal:
	 * org.springframework.security.core.userdetails.User@c20: Username: aa;
	 * Password: [PROTECTED]; Enabled: true; AccountNonExpired: true;
	 * credentialsNonExpired: true; AccountNonLocked: true; Granted
	 * Authorities: ROLE_ADMIN; Credentials: [PROTECTED]; Authenticated:
	 * true; Details: org.springframework.security.web.authentication.
	 * WebAuthenticationDetails@fffc7f0c: RemoteIpAddress: 127.0.0.1;
	 * SessionId: 0952B3F9F18222DCCD0ECE39D039F900; Granted Authorities:
	 * ROLE_ADMIN
	 */

	if (configAttributes == null) {
	    return;
	}
	// System.out.println(object.toString()); //object is a URL.

	Iterator<ConfigAttribute> ite = configAttributes.iterator();
	while (ite.hasNext()) {

	    ConfigAttribute ca = ite.next();
	    String needRole = ((SecurityConfig) ca).getAttribute();

	    for (GrantedAuthority ga : authentication.getAuthorities()) {
		if (needRole.equals(ga.getAuthority()))
		    return;
	    }
	}
	throw new AccessDeniedException("没有权限");

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {

	return true;
	// return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {

	return true;
	// return false;
    }

}