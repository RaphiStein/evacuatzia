package evacuatzia_proj.exceptions;

public class IllegalEventCapacity extends EvacuatziaCheckedExpection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6257323884446131832L;

	public IllegalEventCapacity() {
		super();
	}

	public IllegalEventCapacity(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalEventCapacity(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalEventCapacity(String message) {
		super(message);
	}

	public IllegalEventCapacity(Throwable cause) {
		super(cause);
	}

}
