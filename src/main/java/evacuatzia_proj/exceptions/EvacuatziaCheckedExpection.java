package evacuatzia_proj.exceptions;

public class EvacuatziaCheckedExpection extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1475995639254889810L;

	public EvacuatziaCheckedExpection() {
		super();
	}

	public EvacuatziaCheckedExpection(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EvacuatziaCheckedExpection(String message, Throwable cause) {
		super(message, cause);
	}

	public EvacuatziaCheckedExpection(String message) {
		super(message);
	}

	public EvacuatziaCheckedExpection(Throwable cause) {
		super(cause);
	}

}
