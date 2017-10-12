package sys.springframework.security;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.http.client.ClientHttpRequest;
import org.springframework.test.web.client.RequestMatcher;

public class CsrfSecurityRequestMatcher implements RequestMatcher {
	private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

	@Override
	public void match(ClientHttpRequest arg0) throws IOException, AssertionError {
		// TODO Auto-generated method stub

	}

	/**
	 * 需要排除的url列表
	 */
	private List<String> execludeUrls;

	public List<String> getExecludeUrls() {
		return execludeUrls;
	}

	public void setExecludeUrls(List<String> execludeUrls) {
		this.execludeUrls = execludeUrls;
	}
}
