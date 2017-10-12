package sys.springframework.security.util;

import javax.servlet.http.HttpServletResponse;
/**
 * 扩展状态码
 * 440：会话失效
 * @author lxr
 *
 */
public interface MyHttpServletResponse extends HttpServletResponse {
	  public static final int SC_HTTP_SESSION_FAILURE= 440;
}
