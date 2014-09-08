package evacuatzia_proj.exceptions;

public class EventTimePassed extends EvacuatziaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1103903273653288939L;

	public EventTimePassed() {
		super();
	}

	public EventTimePassed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EventTimePassed(String message, Throwable cause) {
		super(message, cause);
	}

	public EventTimePassed(String message) {
		super(message);
	}

	public EventTimePassed(Throwable cause) {
		super(cause);
	}

}
