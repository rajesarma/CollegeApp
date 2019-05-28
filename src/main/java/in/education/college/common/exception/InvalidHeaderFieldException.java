package in.education.college.common.exception;

public class InvalidHeaderFieldException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;


	public InvalidHeaderFieldException() {

	}

	public InvalidHeaderFieldException(String message) {
		this.setMessage(message);
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
