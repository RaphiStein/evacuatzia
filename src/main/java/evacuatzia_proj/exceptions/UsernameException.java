package evacuatzia_proj.exceptions;

public class UsernameException extends EvacuatziaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4242087151921738708L;

	public UsernameException() {
		super();
	}

	public UsernameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UsernameException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameException(String message) {
		super(message);
	}

	public UsernameException(Throwable cause) {
		super(cause);
	}

}
