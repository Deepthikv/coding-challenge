package heavenhr.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import heavenhr.common.Messages;
import heavenhr.exceptions.PersistentException;
import heavenhr.exceptions.ConflictException;
import heavenhr.exceptions.ResourceNotFoundException;
import heavenhr.model.Application;
import heavenhr.model.ApplicationStatus;
import heavenhr.model.Offer;

/**
 * Repository class for Application
 * 
 * @author  Deepthi KV
 *
 */
@Repository
public class ApplicationRepository implements IApplicationRepository<Application, String> {

	/**
	 * Application db
	 */
	private ApplicationPersistentData applicationDB = ApplicationPersistentData.getInstance();
	
	/**
	 * Logger for logging
	 */
	private static final Logger logger = LoggerFactory.getLogger(ApplicationRepository.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Application> getAllApplications() {
		return applicationDB.getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Application findApplication(String emailId) {
		return applicationDB.getApplication(emailId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveApplication(Application application) {
		Offer offer = application.getOffer();
		if (OfferPersistentData.getInstance().getOffers().contains(offer)) {
			try {
				applicationDB.save(application);
			} catch (PersistentException e) {
				throw new ConflictException(Messages.DUPLICATE_EMAIL);
			}
		} else {
			throw new ResourceNotFoundException(Messages.PARENT_OFFER_EXCEPTION);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Application updateApplicationStatus(String email, ApplicationStatus applicationStatus) {
		Application application = findApplication(email);
		if (application != null) {
			application.setStatus(applicationStatus);
			logger.info(Messages.UPDATED_STATUS);
			return application;

		}
		return application;

	}

	
}