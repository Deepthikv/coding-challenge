package heavenhr.exceptions;

import org.springframework.http.HttpStatus;

/**
 * If any resource is already exists in the server, then ConflictException
 * is thrown.
 * 
 * @author Deepthi KV
 *
 */
public class ConflictException extends AbstractException {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = -2879426558258895628L;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public ConflictException(String message) {
		super(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), message);
	}	

	public HttpStatus getHttpStatus() {
		return HttpStatus.CONFLICT;
	}

}
