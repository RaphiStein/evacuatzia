package evacuatzia_proj.exceptions.missingParam;

import evacuatzia_proj.exceptions.EvacuatziaException;

public class DateException extends EvacuatziaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1090079339242637625L;

	public DateException() {
		super();
	}

	public DateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DateException(String message, Throwable cause) {
		super(message, cause);
	}

	public DateException(String message) {
		super(message);
	}

	public DateException(Throwable cause) {
		super(cause);
	}

}
