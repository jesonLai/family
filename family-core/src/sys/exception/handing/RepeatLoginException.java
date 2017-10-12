package sys.exception.handing;

public class RepeatLoginException extends BusinessException {
	private static final long serialVersionUID = -8133962034461758744L;
	private String username;
	public RepeatLoginException(String message) {
		super(message);
	}
	public RepeatLoginException(String username, String message) {
		super(message);

		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}