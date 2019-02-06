package heavenhr.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import heavenhr.common.Endpoints;
import heavenhr.model.Application;
import heavenhr.model.Offer;
import heavenhr.response.ApplicationResponse;
import heavenhr.response.Response;
import heavenhr.service.OfferService;

/**
 * Offer controller for offer requests.
 * 
 * @author Deepthi KV
 *
 */
@RestController
public class OfferController {

	/**
	 * offer service
	 */
	@Autowired
	private OfferService offerService;

	/**
	 * Creates the job offer	
	 * 
	 * @param offer
	 * @return Response entity with success message
	 */
	@PostMapping(Endpoints.OFFER_NEW)
	public ResponseEntity<?> createOffer(@Valid @RequestBody Offer offer) {
		offerService.createOffer(offer);
		Response message = Response.successOffer();
		return new ResponseEntity<Response>(message, message.getHttpstatus());
	}

	/**
	 * Retrieves a single the job offer based on the job title
	 *  
	 * @return set of offers
	 */
	@RequestMapping(value = Endpoints.OFFER_PATH + "{jobTitle}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Offer getOffer(@PathVariable String jobTitle) {
		return offerService.getOffers(jobTitle);

	}

	/**
	 * Retrieves all the offers 
	 * 
	 * @return set of offers
	 */
	@GetMapping(value = Endpoints.OFFER_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Offer> getOffers() {
		return offerService.getOffers();

	}

	/**
	 * Gets all the applications applied for the job offer. 
	 * 
	 * @param jobTitle
	 * @return list of applications
	 */
	@RequestMapping(value = Endpoints.NUMBER_OF_APPLICATIONS
			+ "{jobTitle}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> numberOfApplications(@PathVariable String jobTitle) {
		List<Application> applications = offerService.getApplications(jobTitle);
		ApplicationResponse message = ApplicationResponse.getMessage(applications);
		return new ResponseEntity<ApplicationResponse>(message, message.getHttpstatus());
	}

}
