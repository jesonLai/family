package sys.springframework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sys.model.PubUser;
import sys.model.PubUsersRole;
import sys.set.service.UsersService;
import sys.util.StringUtil;

//

/**
 * 用户登录验证的类
 * 
 * @author lxr
 *
 */
public class MyUserDetailsService extends AbstractUserDetailsAuthenticationProvider implements UserDetailsService {
    protected final Log _Log = LogFactory.getLog(getClass());
    @Resource
    UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
	_Log.debug(">>>>>>>>>>>>>>>>>>>loadUserByUsername()username[" + username + "]<<<<<<<<<<<<<<<<");
	PubUser users = usersService.findOneByUserAccount(username);
	if (StringUtil.isEmpty(users.getUserAccount()))
	    throw new AccessDeniedException("账号或密码错误！");
	/*
	 * 不要使用GrantedAuthorityImpl，官网说这个已过期了，
	 * SimpleGrantedAuthority代替GrantedAuthorityImpl，赋予一个角色（即权限）
	 * 
	 */
	List<PubUsersRole> list = users.getPubUsersRoles();
	for (PubUsersRole pubUsersRole : list) {
	    auths.add(new SimpleGrantedAuthority(pubUsersRole.getPubRole().getRoleSysName()));
	}
	boolean enables = true;
	boolean accountNonExpired = true;
	boolean credentialsNonExpired = true;
	boolean accountNonLocked = true;

	// 封装成spring security的User
	User userdetail = new User(users.getUserAccount(), users.getUserPassword(), enables, accountNonExpired,
		credentialsNonExpired, accountNonLocked, auths);
	return userdetail;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken arg1)
	    throws AuthenticationException {
	// TODO Auto-generated method stub
	_Log.debug(
		">>>>>>>>>>>>>>>>>>>additionalAuthenticationChecks()userDetails[" + userDetails + "]<<<<<<<<<<<<<<<<");
    }

    @Override
    protected UserDetails retrieveUser(String arg0, UsernamePasswordAuthenticationToken arg1)
	    throws AuthenticationException {
	// TODO Auto-generated method stub
	_Log.debug(">>>>>>>>>>>>>>>>>>>retrieveUser()arg0[" + arg0 + "]<<<<<<<<<<<<<<<<");
	return null;
    }
}