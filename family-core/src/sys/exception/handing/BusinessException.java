package sys.exception.handing;

/**
 * 业务异常处理
 * 
 * @author lxr
 *
 */
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 4638084027425284720L;
	public BusinessException() {
	}
	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	
}
