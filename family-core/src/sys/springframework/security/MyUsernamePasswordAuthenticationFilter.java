package sys.springframework.security;
import static nl.captcha.Captcha.NAME;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import nl.captcha.Captcha;
import sys.model.PubMenu;
import sys.model.PubRole;
import sys.model.PubUser;
import sys.model.controller.UserInfoContext;
import sys.set.service.RolesService;
import sys.set.service.UsersService;
import sys.tool.string.StringUtils;
import sys.util.ClientPCInfo;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

/**
 * 自定义的登录类
 * 
 * @author lxr
 *
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	public static final String VALIDATE_CODE = NAME;
	public static final String USERNAME = "userAccount";
	public static final String PASSWORD = "userPassword";

	private int showCheckCode = 0;

	public int getShowCheckCode() {
		return showCheckCode;
	}

	public void setShowCheckCode(int showCheckCode) {
		this.showCheckCode = showCheckCode;
	}

	@Resource
	UsersService usersService;
	@Resource
	RolesService rolesService;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		long start=System.currentTimeMillis();
		if (!"POST".equals(request.getMethod())) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
		
		// 检测验证码
		 checkValidateCode(request);
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		// 验证用户账号与密码是否对应
		username = username.trim();
		PubUser users = usersService.findOneByUserAccount(username);
		HttpSession session = request.getSession();
		session = request.getSession(false);// false代表不创建新的session，直接获取当前的session
		session.setAttribute("USERNAME", username);
		if (StringUtil.isEmpty(users)) {
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "用户名或密码错误！");
			throw new AuthenticationServiceException("用户名或密码错误！");
		}else if(users.getPubUsersRoles().size()==0){
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "没有权限访问！");
			throw new AuthenticationServiceException("没有权限访问！");
		} else if(users.getuFlag()!=1){
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "账号正在审核中！");
			throw new AuthenticationServiceException("在审核中！");
		}else if (users.getUserPassword() == "" || users.getUserPassword() == null) {
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "用户名或密码错误！");
			throw new AuthenticationServiceException("用户名或密码错误！");
		} else if (!new BCryptPasswordEncoder().matches(password,users.getUserPassword())) {// password加密后再进行验证
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "用户名或密码错误！");
			throw new AuthenticationServiceException("用户名或密码错误！");
		} else {
			
		}
		setUser(users, request, session,true);
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,password);
		setDetails(request, authRequest);
		long end=System.currentTimeMillis();
		long handlerTime=end-start;
		logger.debug("执行时间:"+handlerTime);
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	public void setUser(PubUser users,HttpServletRequest request,HttpSession session,boolean bl){
		String browsers=ClientPCInfo.getAgentOf(request, "browsers");
		String clientIP=ClientPCInfo.getIpAddr(request);
		String clientSystem=ClientPCInfo.getAgentOf(request, "os");
		Map<String,String> clientAddress=ClientPCInfo.getAddresses("ip="+clientIP, "utf-8");
		String regionCityCounty=clientAddress.get("region")+clientAddress.get("city")+clientAddress.get("county");
		
		UserInfoContext userContext = UtilFactory.getSpringContext().getUserInfoContext();
		userContext.setUser(users);
		userContext.setUserSession(session);
		
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		userContext.setBasePath(basePath);
		userContext.setClientBrowser(browsers);
		userContext.setClientIP(clientIP);
		userContext.setClientSystem(clientSystem);
		userContext.setClientAddress(regionCityCounty);
		//获取用户的全部角色
		int xUId=users.getUId();
		EntityManagerFactory xEntityManagerFactory=UtilFactory.getSpringContext().getEntityManagerFactory();
		EntityManager xEntityManager=xEntityManagerFactory.createEntityManager();
		String sql="{call get_login_roles(?)}";
		Query  query =xEntityManager.createNativeQuery(sql,PubRole.class);
		query.setParameter(1, xUId);
		List<PubRole> pubRoles=query.getResultList();
		userContext.setPubRoles(pubRoles);
		//获取用户的全部菜单
		sql="{call get_login_menus(?)}";
		query =xEntityManager.createNativeQuery(sql,PubMenu.class);
		query.setParameter(1, xUId);
		List<PubMenu> PubMenus=query.getResultList();
		userContext.setPubMenus(PubMenus);
		
		users.setIp(clientIP);
		users.setBrowserName(browsers);
		users.setOsName(clientSystem);
		users.setLastLoginTime(new Date());
		users.setIpAddress(regionCityCounty);
		if(bl)usersService.save(users);
	}
	protected void checkValidateCode(HttpServletRequest request) {
		HttpSession session = request.getSession();

		Captcha sessionValidateCode = obtainSessionValidateCode(session);

		if (session.getAttribute("showCheckCode") == "1") {

			// 让上一次的验证码失效
			session.setAttribute(NAME, null);
			String validateCodeParameter = obtainValidateCodeParameter(request);

			// 判断输入的验证码和保存在session中的验证码是否相同，这里不区分大小写进行验证
			 if (StringUtils.isEmpty(validateCodeParameter)||!StringUtils.isEmpty(sessionValidateCode)&&!validateCodeParameter.equalsIgnoreCase(sessionValidateCode.getAnswer())){
				session = request.getSession(false);// false代表不创建新的session，直接获取当前的session
				session.setAttribute("SECURITY_LOGIN_EXCEPTION", "验证码错误");
				throw new AuthenticationServiceException("验证码错误！");
			 }
		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	protected Captcha obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? null : (Captcha)obj;
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}
}
