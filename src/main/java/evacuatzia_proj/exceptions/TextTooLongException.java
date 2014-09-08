package evacuatzia_proj.exceptions;

public class TextTooLongException extends EvacuatziaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4960856624444660504L;

	public TextTooLongException() {
		super();
	}

	public TextTooLongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TextTooLongException(String message, Throwable cause) {
		super(message, cause);
	}

	public TextTooLongException(String message) {
		super(message);
	}

	public TextTooLongException(Throwable cause) {
		super(cause);
	}

}
