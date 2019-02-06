package heavenhr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import heavenhr.model.Application;
import heavenhr.model.ApplicationStatus;

/**
 * This interface related to application functionalities
 * 
 * @author Deepthi KV
 *
 */
@Service
public interface ApplicationService {

	/**
	 * Apply for a specific job offer
	 * 
	 * @param application
	 * @return the applied application
	 */
	public Application createApplication(Application application);

	/**
	 * Retrieves all the applications
	 * 
	 * @return set of applications
	 */
	public List<Application> getAllApplications();

	/**
	 * Retrieves the count of the total applications.
	 * 
	 * @return
	 */
	public int count();

	/**
	 * Retrieves an application based on email
	 * 
	 * @param email
	 * @return application
	 */
	public Application findApplicationByEmail(String email);

	/**
	 * Updates an application's status
	 * 
	 * @param email
	 * @param applicationStatus
	 * @return application
	 */
	public Application updateApplicationStatus(String email, ApplicationStatus applicationStatus);

}
