package evacuatzia_proj.exceptions;

public class EvacuatziaException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -285542247909265009L;

	public EvacuatziaException() {
		super();
	}

	public EvacuatziaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EvacuatziaException(String message, Throwable cause) {
		super(message, cause);
	}

	public EvacuatziaException(String message) {
		super(message);
	}

	public EvacuatziaException(Throwable cause) {
		super(cause);
	}
	
}
