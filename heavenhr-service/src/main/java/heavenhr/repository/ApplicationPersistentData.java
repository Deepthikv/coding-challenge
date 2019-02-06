package heavenhr.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import heavenhr.exceptions.PersistentException;
import heavenhr.model.Application;
import heavenhr.model.ApplicationStatus;
import heavenhr.model.Offer;

/**
 * ApplicationPersistentData for persisting all the application related data.
 * 
 * @author Deepthi KV
 * 
 * 
 */

public class ApplicationPersistentData {
	
	// instance 
	private static ApplicationPersistentData INSTANACE = null;

	/**
	 * Constructor
	 */
	private ApplicationPersistentData() {

	}

	/**
	 * Gets the instance of the ApplicationPersistentData
	 * @return
	 */
	public static ApplicationPersistentData getInstance() {
		if (INSTANACE == null) {
			INSTANACE = new ApplicationPersistentData();
		}
		return INSTANACE;
	}

	/**
	 * List of applications
	 */
	private List<Application> applications = new ArrayList<>();

	/**
	 * application map which contains offer(Jobtitle) as key and application as
	 * value.
	 */
	private Map<String, List<Application>> applicationsMap = new HashMap<>();

	/**
	 * Returns all applications
	 * 
	 * @return all set of applications
	 */
	public List<Application> getAll() {
		List<Application> appset = new ArrayList<>();
		Iterator<Entry<String, List<Application>>> iterator = applicationsMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<Application>> next = iterator.next();
			List<Application> value = next.getValue();
			appset.addAll(value);
		}
		return applications;
	}

	public Map<String, List<Application>> getApplicationsMap() {
		return applicationsMap;
	}

	/**
	 * Saves an application to DB. If an application already exists per offer,
	 * then it throws DuplicateDataException
	 * 
	 * @param application
	 * @throws PersistentException
	 */
	public void save(Application application) throws PersistentException {
		Offer offer = application.getOffer();
		String primaryKey = offer.getJobTitle();
		List<Application> list = getApplicationsByOffer(primaryKey);
		if (list.isEmpty()) {
			list.add(application);
			applicationsMap.put(primaryKey, list);

		} else {
			if (list.contains(application)) {
				throw new PersistentException();
			}
			list.add(application);
		}
		application.setStatus(ApplicationStatus.APPLIED);
		applications.add(application);
	}

	/**
	 * Retrieves all the applications based on the email id. 
	 * 
	 * @param emailId
	 *            
	 * @return null if there is no application found.
	 */
	public Application getApplication(String emailId) {	
		return applications.stream().filter(application -> application.getEmail().equals(emailId)).findAny().orElse(null);
	}

	/**
	 * Get all applications based on Job title.
	 * 
	 * @param jobTitile
	 *            primary key
	 * @return set of applications found by jobtitle
	 */
	public List<Application> getApplicationsByOffer(String jobTitile) {
		List<Application> list = applicationsMap.get(jobTitile);
		if (list == null) {
			return new ArrayList<>();
		}
		return list;
	}

}
