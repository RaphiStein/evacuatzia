package evacuatzia_proj.exceptions;

public class EventFullException extends EvacuatziaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7001664694998867977L;

	public EventFullException() {
		super();
	}

	public EventFullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EventFullException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventFullException(String message) {
		super(message);
	}

	public EventFullException(Throwable cause) {
		super(cause);
	}

}
