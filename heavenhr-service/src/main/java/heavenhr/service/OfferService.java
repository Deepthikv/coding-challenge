package heavenhr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import heavenhr.model.Application;
import heavenhr.model.Offer;

/**
 * Interface related to application functionalities* 
 * 
 * @author Deepthi KV
 
 */
@Service
public interface OfferService {
	
	/**	 
	 * Creates a job offer.
	 * 
	 * @param offer
	 * @return offer 
	 */
	public Offer createOffer(Offer offer);

	/**
	 * Retrieves a single job offer based on jobtitle
	 * 
	 * @param jobTitle
	 * @return offer
	 */
	public Offer findOfferByJobTitle(String jobTitle);

	/**
	 * Returns the number of applications per jobTitles.
	 * 
	 * @param jobTitle
	 * @return the number of applications
	 */
	public int numberOfApplications(String jobTitle);

	/**
	 * Retrieves all the job offers
	 * 
	 * @return set of job offers
	 */
	public List<Offer> getOffers();	

	/**
	 * Retrieves all the applications applied for a job offer. 
	 * 
	 * @param jobTitle 
	 * @return set of applications 
	 */

	public List<Application> getApplications(String jobTitle);

	/**
	 * Retrieves a single job offer based on jobtitle
	 * 
	 * @param jobTitle
	 * @return offer
	 */
	public Offer getOffers(String jobTitle);

}
