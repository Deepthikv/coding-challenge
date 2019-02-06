package heavenhr.repository;

import java.util.ArrayList;
import java.util.List;

import heavenhr.model.Offer;

/**
 * The OfferDB run as a in memory db and does not save anywhere. It contains the
 * job offer related data.
 * 
 * @author Deepthi KV
 * 
 * 
 */

public class OfferPersistentData {
	
	private static OfferPersistentData INSTANACE = null;
	
	private OfferPersistentData(){
		
	}	
	
	public static OfferPersistentData getInstance(){
		if(INSTANACE==null){
			INSTANACE = new OfferPersistentData();
		}
		return INSTANACE;
	}
	
	/**
	 * List of offers
	 */
	private List<Offer> offers = new ArrayList<>();

	/**
	 * Gets all the offer objects
	 * 
	 * @return list of offer
	 */
	public List<Offer> getOffers() {
		return offers;
	}

	/**
	 * Saves the offer object
	 * 
	 * @param offer
	 */
	public void save(Offer offer) {
		getOffers().add(offer);
	}

	/**
	 * Retrieves a single offer based on the job title
	 * 
	 * @param jobTitle
	 * @return
	 */
	public Offer getOffer(String jobTitle) {
		return getOffers().stream().filter(offer -> offer.getJobTitle().equals(jobTitle)).findAny().orElse(null);
	}
}
