package heavenhr.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import heavenhr.model.Application;

/**
 * This class is specifically used to send the response message when the request
 * is made for the number of applications and result of applications come as
 * empty.
 * 
 * @author Deepthi KV
 *
 */
public class EmptyResonse extends ApplicationResponse {

	/**
	 * message property
	 */
	private String message;

	/**
	 * Constructor
	 * 
	 * @param applications
	 * @param count
	 * @param message
	 */
	public EmptyResonse(List<Application> applications, int count, String message) {
		super(applications, count, HttpStatus.NOT_FOUND);
		this.message = message;
	}

	/**
	 * Gets the message
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

}
