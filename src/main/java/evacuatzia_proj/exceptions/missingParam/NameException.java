package evacuatzia_proj.exceptions.missingParam;

import evacuatzia_proj.exceptions.EvacuatziaException;

public class NameException extends EvacuatziaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 382760094250026046L;

	public NameException() {
		super();
	}

	public NameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NameException(String message, Throwable cause) {
		super(message, cause);
	}

	public NameException(String message) {
		super(message);
	}

	public NameException(Throwable cause) {
		super(cause);
	}

}
