package heavenhr.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Resourcenot found exception.
 * 
 * @author  Deepthi KV
 *
 */
public class ResourceNotFoundException extends AbstractException {
	
	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 8569861943834136712L;
	/**
	 * NOT FOUND status
	 */
	private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public ResourceNotFoundException(String message) {
		super(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), message);
	}

	
	

	

	/**
	 * Gets the http status
	 */
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}

}
