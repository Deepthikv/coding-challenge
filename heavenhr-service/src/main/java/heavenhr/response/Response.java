package heavenhr.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import heavenhr.common.Messages;

/**
 * This class is used for the response messages with status and status code.
 * 
 * @author  Deepthi KV
 *
 */
public class Response {
	
	/**
	 * http status
	 */
	@JsonIgnoreProperties
	private HttpStatus httpstatus;

	/**
	 * property message
	 */
	private String message;

	/**
	 * property code
	 */
	private String code;

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param status
	 * @param statuscode
	 */
	public Response(String message, HttpStatus status) {
		this.message = message;
		this.httpstatus = status;
		this.code = "" + status.value();
	}

	/**
	 * Sets the message
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the message
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the code
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the code
	 * 
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * gets the http status
	 * 
	 * @return
	 */
	public HttpStatus getHttpstatus() {
		return httpstatus;
	}

	/**
	 * Gets the status
	 * 
	 * @return status
	 */
	public String getStatus() {
		return getHttpstatus().getReasonPhrase();
	}

	/**
	 * Response message contains the http status and no applications for email id
	 * message.
	 * 
	 * @return response message
	 */
	public static Response noApplicationForEmailFound() {
		return new Response(Messages.NO_APPLICATION_FOUND, HttpStatus.NOT_FOUND);
	}

	/**
	 * Response message contains the http status and no applications found message.
	 * 
	 * @return response message
	 */

	public static Response noApplicationsFound() {
		return new Response(Messages.NO_APPLICATIONS_FOUND, HttpStatus.NOT_FOUND);
	}

	/**
	 * Response message contains the http status and reason for not updated the
	 * status
	 * 
	 * @return response message
	 */
	public static Response couldNotUpdateStatusEmailExists() {
		return new Response(Messages.STATUS_NOT_UPDATED_EMAIL_EXISTS, HttpStatus.NOT_FOUND);
	}

	/**
	 * Response message contains the http status and status empty message.
	 * 
	 * @return response message
	 */
	public static Response statusEmpty() {
		return new Response(Messages.STATUS_NULL, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Response message contains the http status and message after creating the
	 * application successfully.
	 * 
	 * @return response message
	 */
	public static Response successApplication() {
		return new Response(Messages.SUCCESS_APPLICATION, HttpStatus.CREATED);
	}

	/**
	 * Response message contains the http status and message after creating the
	 * offer successfully.
	 * 
	 * @return response message
	 */
	public static Response successOffer() {
		return new Response(Messages.SUCCESS_OFFER, HttpStatus.CREATED);
	}
}
