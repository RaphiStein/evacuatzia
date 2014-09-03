package evacuatzia_proj.exceptions.missingParam;

import evacuatzia_proj.exceptions.EvacuatziaException;

public class GeometryException extends EvacuatziaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3649160705576671547L;

	public GeometryException() {
		super();
	}

	public GeometryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GeometryException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeometryException(String message) {
		super(message);
	}

	public GeometryException(Throwable cause) {
		super(cause);
	}

}
