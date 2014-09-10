package evacuatzia_proj.exceptions.missingParam;

import evacuatzia_proj.exceptions.EvacuatziaException;

public class IdException extends EvacuatziaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2336463770021754558L;

	public IdException() {
		super();
	}

	public IdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IdException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdException(String message) {
		super(message);
	}

	public IdException(Throwable cause) {
		super(cause);
	}

}
