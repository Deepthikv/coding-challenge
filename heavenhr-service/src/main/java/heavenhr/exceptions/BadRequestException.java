package heavenhr.exceptions;

import org.springframework.http.HttpStatus;

/**
 * BadRequestException when any request is in invalid
 * 
 * @author Deepthi KV
 *
 */
public class BadRequestException extends AbstractException {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = -6585055504560582225L;
	/**
	 * Bad request
	 */
	private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public BadRequestException(String message) {
		super(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), message);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Gets the https status BadRequest
	 */
	@Override
	public HttpStatus getHttpStatus() {
		return BAD_REQUEST;
	}

}
