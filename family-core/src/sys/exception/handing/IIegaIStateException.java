package sys.exception.handing;

public class IIegaIStateException extends BusinessException {
	private static final long serialVersionUID = -8133962034461758744L;
	private String username;
	public IIegaIStateException(String message) {
		super(message);
	}
	public IIegaIStateException(String username, String message) {
		super(message);

		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}