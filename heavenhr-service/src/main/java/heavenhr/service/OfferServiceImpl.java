package heavenhr.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import heavenhr.common.Messages;
import heavenhr.exceptions.BadRequestException;
import heavenhr.exceptions.ConflictException;
import heavenhr.exceptions.ResourceNotFoundException;
import heavenhr.model.Application;
import heavenhr.model.Offer;
import heavenhr.repository.ApplicationPersistentData;
import heavenhr.repository.IOfferRepository;

/**
 * 
 * 
 * Service class dealt with all the services of Offer related functionality.
 * 
 * 
 * @author Deepthi KV
 * 
 * 
 */
@Service
public class OfferServiceImpl implements OfferService {

	@Autowired
	private IOfferRepository<Offer,String> offerRepository;
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public Offer createOffer(Offer offer) {
		if (offer == null) {
			throw new BadRequestException(Messages.NULL_OFFER);
		}
		if (getOffers().contains(offer)) {
			throw  new ConflictException(Messages.DUPLICATE_JOBTITLE);
		}
		offerRepository.createOffer(offer);		
		return offer;

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */

	@Override
	public List<Offer> getOffers() {
		return offerRepository.getOffers();
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Offer getOffers(String jobTitle) {
		Offer offer = findOfferByJobTitle(jobTitle);
		if (offer == null) {
			throw new ResourceNotFoundException(Messages.OFFER_NOT_EXISTS);
		}
		List<Application> applications = getApplications(offer.getJobTitle());
		Set<Application> set = new HashSet<>(applications);
		offer.setApplications(set);
		offer.setNoOfApplicagtions(set.size());
		return offer;

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Offer findOfferByJobTitle(String jobTitle) {
		Offer offer = offerRepository.findOffer(jobTitle);
		if(offer!=null) {
			return offer;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int numberOfApplications(String jobTitle) {
		List<Application> applications = getApplications(jobTitle);
		if (applications != null) {
			return applications.size();
		}
		return 0;
	}

	/**	 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<Application> getApplications(String jobTitle) {
		return ApplicationPersistentData.getInstance().getApplicationsByOffer(jobTitle);		
	}

}