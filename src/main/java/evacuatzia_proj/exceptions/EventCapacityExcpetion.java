package evacuatzia_proj.exceptions;


public class EventCapacityExcpetion extends EvacuatziaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4384190996972668440L;

	public EventCapacityExcpetion() {
		super();
	}

	public EventCapacityExcpetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EventCapacityExcpetion(String message, Throwable cause) {
		super(message, cause);
	}

	public EventCapacityExcpetion(String message) {
		super(message);
	}

	public EventCapacityExcpetion(Throwable cause) {
		super(cause);
	}

}
