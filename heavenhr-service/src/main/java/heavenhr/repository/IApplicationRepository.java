package heavenhr.repository;

import java.util.List;

import heavenhr.model.Application;
import heavenhr.model.ApplicationStatus;

/**
 * Interface for the Application repository
 * 
 * @author  Deepthi KV
 *
 * @param <E> Entity to be perisited
 * @param <P> Primary key type such as i=Integer,String etc
 */
public interface IApplicationRepository<E, P>  {

	/**
	 * Updates the application status
	 * 
	 * @param email
	 * @param applicationStatus
	 * @return updated application
	 */
	Application updateApplicationStatus(String email, ApplicationStatus applicationStatus);
	
	/**
	 * Gets all the applications.
	 * @return
	 */
	public List<Application> getAllApplications();

	/**
	 * @param emailId
	 * @return
	 */
	Application findApplication(String emailId);

	/**
	 * @param application
	 */
	void saveApplication(Application application);

}
