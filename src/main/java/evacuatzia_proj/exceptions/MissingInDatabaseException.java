package evacuatzia_proj.exceptions;

public class MissingInDatabaseException extends EvacuatziaException {

	public MissingInDatabaseException() {
		super();
	}

	public MissingInDatabaseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MissingInDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingInDatabaseException(String message) {
		super(message);
	}

	public MissingInDatabaseException(Throwable cause) {
		super(cause);
	}

}
