package heavenhr.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Base exception whichi is a runtime exception for all the exceptions.
 * 
 * @author  Deepthi KV
 *
 */
public abstract class  AbstractException extends RuntimeException {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 5580005359567276590L;

	/**
	 * error code
	 */
	private int errorCode;

	/**
	 * error type
	 * 
	 */
	private String errorType;

	/**
	 * Constructor
	 * 
	 * @param errorCode
	 * @param errorType
	 * @param message
	 */
	public AbstractException(int errorCode, String errorType, String message) {
		super(message);
		this.errorCode = errorCode;
		this.errorType = errorType;
	}

	/**
	 * Gets the error code
	 * 
	 * @return error code
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code
	 * 
	 * @param errorCode
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Sets the error type
	 * 
	 * @param errorType
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * Gets the error type
	 * 
	 * @return
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * Gets the appropriate http status for each sub classes.
	 * 
	 * @return http status
	 */
	public abstract HttpStatus getHttpStatus() ;

}
