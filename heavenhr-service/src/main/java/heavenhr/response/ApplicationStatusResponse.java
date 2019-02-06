package heavenhr.response;

import org.springframework.http.HttpStatus;

import heavenhr.common.Messages;
import heavenhr.model.ApplicationStatus;

/**
 * This class is specifically used for the Application status response message.
 * 
 * @author  Deepthi KV
 *
 */
public class ApplicationStatusResponse extends Response {

	/**
	 * application status
	 */
	private String applicationStatus;

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param applicationStatus
	 */
	public ApplicationStatusResponse(String message, String applicationStatus) {
		super(message, HttpStatus.OK);
		this.applicationStatus = applicationStatus;

	}

	/**
	 * sets the application
	 * 
	 * @param applicationStatus
	 */
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	/**
	 * Gets the application status
	 * 
	 * @return application status
	 */
	public String getApplicationStatus() {
		return applicationStatus;
	}

	/**
	 * ApplicationStatusResponseMessage contains the update success message
	 * 
	 * @param status
	 * @return ApplicationStatusResponseMessage
	 */
	public static ApplicationStatusResponse updateSuccess(ApplicationStatus status) {
		return new ApplicationStatusResponse(Messages.UPDATE_STATUS_SUCCESS, status.name());
	}

}
