package sys.springframework.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationProvider extends  AbstractUserDetailsAuthenticationProvider {

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		 System.out.println("CustomAuthenticationProvider.additionalAuthenticationChecks() is called!");
	}

	@Override
	protected UserDetails retrieveUser(String arg0, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		 System.out.println("CustomAuthenticationProvider.retrieveUser() is called!");
		return null;
	}
	
}
