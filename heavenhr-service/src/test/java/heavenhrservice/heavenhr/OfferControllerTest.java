package heavenhrservice.heavenhr;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import heavenhr.HeavenhrApplication;
import heavenhr.common.Messages;
import heavenhr.common.Endpoints;
import heavenhr.exceptions.AbstractException;
import heavenhr.exceptions.ResourceNotFoundException;
import heavenhr.model.Application;
import heavenhr.model.Offer;
import heavenhr.repository.ApplicationPersistentData;
import heavenhr.repository.OfferRepository;
import heavenhr.response.FailureResponse;
import heavenhr.response.ApplicationResponse;
import heavenhr.response.Response;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HeavenhrApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerTest extends AbstractTestClass {

	/**
	 * Headers
	 */
	HttpHeaders headers = new HttpHeaders();

	
	@LocalServerPort
	private int port;

	@Before
	public void setup() {

	}



	@Test
	public void createJobOfferSuccess() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle("sw877654");
		String appNewpath = Endpoints.OFFER_NEW;
		HttpEntity<Offer> entity = new HttpEntity<Offer>(offer, headers);
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.POST);
		Response message = Response.successOffer();
		String body = response.getBody();
		String json = toJson(message);
		JSONAssert.assertEquals(json, body, false);

	}

	

	@Test
	public void jobTitleNull() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle(null);
		HttpEntity<Offer> entity = new HttpEntity<Offer>(offer, headers);
		String appNewpath = Endpoints.OFFER_NEW;
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.POST);
		FailureResponse failureResponseMessage = new FailureResponse(HttpStatus.BAD_REQUEST,
				"Validation Error", Messages.EMPTY_JOBTITLE);
		String expectedResponseMessage = toJson(failureResponseMessage);
		String responseContent = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);
	}

	@Test
	public void jobTitleEmpty() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle("");
		HttpEntity<Offer> entity = new HttpEntity<Offer>(offer, headers);
		String appNewpath = Endpoints.OFFER_NEW;
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.POST);

		FailureResponse failureResponseMessage = new FailureResponse(HttpStatus.BAD_REQUEST,
				"Validation Error", Messages.EMPTY_JOBTITLE);
		String expectedResponseMessage = toJson(failureResponseMessage);
		String responseContent = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);
	}

	
	@Test
	public void jobTitleBlank() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle(" ");
		HttpEntity<Offer> entity = new HttpEntity<Offer>(offer, headers);
		String appNewpath = Endpoints.OFFER_NEW;
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.POST);
		FailureResponse failureResponseMessage = new FailureResponse(HttpStatus.BAD_REQUEST,
				"Validation Error", Messages.EMPTY_JOBTITLE);
		String expectedResponseMessage = toJson(failureResponseMessage);
		String responseContent = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);
	}

	/**
	 * Test case for retrieving all the job offers
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAllOffers() throws Exception {

		Offer offer = new Offer();
		offer.setJobTitle("SW100");
		HttpEntity<Offer> entity = new HttpEntity<Offer>(null, headers);

		List<Offer> offers = new OfferRepository().getOffers();
		offers.add(MockData.mockOffer());
		offers.add(MockData.mockOffer("sw200"));

		String appNewpath = Endpoints.OFFER_PATH;
		String createURLWithPort = createURLWithPort(appNewpath);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort, HttpMethod.GET, entity,
				String.class);

		String responseContent = response.getBody();
		String expectedResponseMessage = toJson(offers);
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);
	}

	
	@Test
	public void getSingleOfferByJobTitle() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle("SW100");
		
		getOffers().add(offer);
	getOffers().add(MockData.mockOffer("sw200"));

		String appNewpath = Endpoints.OFFER_PATH;
		String createURLWithPort = createURLWithPort(appNewpath + "SW100");
		HttpEntity<Offer> entity = new HttpEntity<Offer>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort, HttpMethod.GET, entity,
				String.class);
		String responseContent = response.getBody();
		String expectedResponseMessage = toJson(offer);
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);

	}

	
	@Test
	public void offerNoFoundException() throws Exception {

		Offer offer = new Offer();
		offer.setJobTitle("SW100");
		getOffers().add(offer);
		getOffers().add(MockData.mockOffer("sw200"));

		String appNewpath = Endpoints.OFFER_PATH;
		String createURLWithPort = createURLWithPort(appNewpath + "SW333");
		HttpEntity<Offer> entity = new HttpEntity<Offer>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort, HttpMethod.GET, entity,
				String.class);

		ResourceNotFoundException ex = new ResourceNotFoundException(Messages.OFFER_NOT_EXISTS);

		FailureResponse responseMessage = convertExceptionToFailureMessage(ex);
		String responseContent = response.getBody();
		String expectedResponseMessage = toJson(responseMessage);
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);

	}
	
	public static FailureResponse convertExceptionToFailureMessage(AbstractException ex) {
		return new FailureResponse(ex.getHttpStatus(), "Server error", ex.getMessage());
	}

	
	@Test
	public void numbrOfApplicationsEmpty() throws Exception {
		HttpEntity<Offer> entity = new HttpEntity<Offer>(null, headers);
		String appNewpath = Endpoints.NUMBER_OF_APPLICATIONS + "1234";
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.GET);
		Offer mockOffer = MockData.mockOffer();
		ApplicationPersistentData applicationDB = ApplicationPersistentData.getInstance();
		List<Application> all = applicationDB.getApplicationsByOffer(mockOffer.getJobTitle());
		String expectedResponseMessage = toJson(ApplicationResponse.getMessage(all));
		String responseContent = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);
	}

	
	@Test
	public void numbrOfApplicationsNotEmpty() throws Exception {
		MockData.clearApplicationDBContents();
		MockData.clearOfferDBContents();
		Offer mockOffer = MockData.mockOffer();
		getOffers().add(mockOffer);
		Application application = MockData.prepareData();
		application.setOffer(mockOffer);
		ApplicationPersistentData applicationDB = ApplicationPersistentData.getInstance();
		applicationDB.save(application);
		List<Application> all = applicationDB.getApplicationsByOffer(mockOffer.getJobTitle());

		HttpEntity<Offer> entity = new HttpEntity<Offer>(null, headers);
		String appNewpath = Endpoints.NUMBER_OF_APPLICATIONS + "1234";
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.GET);
		String expectedResponseMessage = toJson(ApplicationResponse.getMessage(all));
		String responseContent = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);

	}

	@Override
	public int port() {
		return port;
	}
	
	private List<Offer> getOffers(){
		return new OfferRepository().getOffers();
	}
}
