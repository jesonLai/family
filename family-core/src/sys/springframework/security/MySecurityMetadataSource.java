package sys.springframework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import sys.model.PubAuthoritiesFunction;
import sys.model.PubAuthority;
import sys.model.PubFunction;
import sys.model.PubRole;
import sys.model.PubRolesAuthority;
import sys.springframework.security.util.AntUrlPathMatcher;
import sys.springframework.security.util.UrlMatcher;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

/**
 * 负责读取数据库中的url对应的权限 服务器启动顺序
 * 
 * @author lxr
 * 
 */
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

	protected final Log _Log = LogFactory.getLog(getClass());
	/*
	 * resourceMap用static声明了，为了避免用户每请求一次都要去数据库读取资源、权限，这里只读取一次，将它保存起来
	 */
	private Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();;
	/**
	 * 默认不开启状态
	 */
	private static final String LOG_OPEN_KEY = "FAMILY_LOG_OPEN_IS_ENABLE_OR_DISABLE";
	private static final boolean LOG_OPEN_VALUE = false;

	public void setResourceMap(Map<String, Collection<ConfigAttribute>> resourceMap){
		this.resourceMap = resourceMap;
	}

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();

	// 构造函数，因为服务器启动时会调用这个类，利用构造函数读取所有的url、角色
	public MySecurityMetadataSource() {
		_Log.debug(">>>>>>>>>>>>>>>>>>>Spring-Security init<<<<<<<<<<<<<<<<");
		_Log.debug(">>>>>>>>>>>>>>>>>>>user.dir[" + System.getProperty("user.dir") + "]<<<<<<<<<<<<<<<<");
		_Log.debug(">>>>>>>>>>>>>>>>>>>webName.root[" + System.getProperty("Test") + "]<<<<<<<<<<<<<<<<");
		if (resourceMap == null || resourceMap.size() == 0){
			loadResourceDefine();
		}
	}

	// 这个方法在url请求时才会调用，服务器启动时不会执行这个方法，前提是需要在<http>标签内设置 <custom-filter>标签
	// getAttributes这个方法会根据你的请求路径去获取这个路径应该是有哪些权限才可以去访问。
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException{
		_Log.debug(">>>>>>>>>>>>>>>>>>>getAttributes()--请求路径权限<<<<<<<<<<<<<<<<");
		// object getRequestUrl 是获取用户请求的url地址
		String url = ((FilterInvocation) object).getRequestUrl();
		// resourceMap保存了loadResourceDefine方法加载进来的数据
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()){
			// 取出resourceMap中读取数据库的url地址
			String resURL = ite.next();
			// 如果两个
			// url地址相同，那么将返回resourceMap中对应的权限集合，然后跳转到MyAccessDecisionManager类里的decide方法，再判断权限
			if (urlMatcher.pathMatchesUrl(resURL,url)){
				return resourceMap.get(resURL); // 返回对应的url地址的权限
				// ，resourceMap是一个主键为地址，值为权限的集合对象
			}
		}
		// 如果上面的两个url地址没有匹配，返回return
		// null，不再调用MyAccessDecisionManager类里的decide方法进行权限验证，代表允许访问页面
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes(){
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz){
		return true;
	}

	@SuppressWarnings("unchecked")
	public void loadResourceDefine(){
		_Log.debug(">>>>>>>>>>>>>>>>>>>loadResourceDefine()<<<<<<<<<<<<<<<<");
		EntityManagerFactory emf = UtilFactory.getSpringContext().getEntityManagerFactory();
		Query query = emf.createEntityManager().createNamedQuery("PubFunction.findAll");
		List<PubFunction> pfls = query.getResultList();// 功能
		for (PubFunction pubFunction:pfls){
			List<PubAuthoritiesFunction> pafls = pubFunction.getPubAuthoritiesFunctions();
			for (PubAuthoritiesFunction pubAuthoritiesFunction:pafls){
				PubAuthority p = pubAuthoritiesFunction.getPubAuthority();
				if (!StringUtil.isEmpty(p)){
					List<PubRolesAuthority> pa = p.getPubRolesAuthorities();
					for (PubRolesAuthority pubRolesAuthority:pa){
						PubRole pubRole = pubRolesAuthority.getPubRole();
						if (!StringUtil.isEmpty(pubRole) && !StringUtil.isEmpty(pubRole.getRoleSysName())){
							ConfigAttribute ca = new SecurityConfig(pubRole.getRoleSysName());
							//匿名者
							ConfigAttribute caAnonymous = new SecurityConfig("ROLE_ANONYMOUS");
							String url = pubFunction.getFunctionUrl();
							Collection<ConfigAttribute> value = new ArrayList<ConfigAttribute>();;
							if (resourceMap.containsKey(url)){
								value = resourceMap.get(url); // 取出这个url的权限集合
							}
							if (!value.contains(ca)){
								value.add(ca);
								resourceMap.put(url,value);
								value.add(ca);
								resourceMap.put(url,value);
							}
						}
					}
				}
			}
			// @SuppressWarnings("unchecked")
			// List<PubUsersRole> pubUsersRoleList=query.getResultList();//用户与角色
			// for (PubUsersRole pubUsersRole :pubUsersRoleList) {
			// //角色
			// ConfigAttribute ca = new
			// SecurityConfig(pubUsersRole.getPubRole().getRoleSysName());
			// //角色与权限
			// List<PubRolesAuthority> pubRolesAuthorityList =
			// pubUsersRole.getPubRole().getPubRolesAuthorities();
			// for (PubRolesAuthority pubRolesAuthority : pubRolesAuthorityList)
			// {
			// //权限与资源
			// List<PubAuthoritiesFunction> pubAuthoritiesResourceList =
			// pubRolesAuthority.getPubAuthority().getPubAuthoritiesFunctions();
			// for (PubAuthoritiesFunction pubAuthoritiesResource :
			// pubAuthoritiesResourceList) {
			// //功能模块
			// PubFunction pubResource =
			// pubAuthoritiesResource.getPubFunction();
			// // listUrl.add(pubResource.getResourceUrl());
			// // 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。
			// String url = pubResource.getFunctionUrl();
			// if (resourceMap.containsKey(url)) {
			// Collection<ConfigAttribute> value = resourceMap.get(url); //
			// 取出这个url的权限集合
			// value.add(ca);
			// resourceMap.put(url, value);
			// } else {
			// Collection<ConfigAttribute> atts = new
			// ArrayList<ConfigAttribute>();
			// atts.add(ca);
			// resourceMap.put(url, atts);
			// }
			//
			// }
			// }
		}
		if (_Log.isDebugEnabled()){
			_Log.debug(">>>>>>>>>>>>>>>>>>>resourceMap[" + resourceMap.values() + "]<<<<<<<<<<<<<<<<");
			_Log.debug(">>>>>>>>>>>>>>>>>>>resourceMap[" + resourceMap + "]<<<<<<<<<<<<<<<<");
		}
		_Log.debug(resourceMap);
	}
}
