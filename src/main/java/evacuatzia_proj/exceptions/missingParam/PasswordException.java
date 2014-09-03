package evacuatzia_proj.exceptions.missingParam;

import evacuatzia_proj.exceptions.EvacuatziaException;

public class PasswordException extends EvacuatziaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6291358148357441397L;

	public PasswordException() {
		super();
	}

	public PasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordException(String message) {
		super(message);
	}

	public PasswordException(Throwable cause) {
		super(cause);
	}

}
