package heavenhrservice.heavenhr;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import heavenhr.HeavenhrApplication;
import heavenhr.common.Messages;
import heavenhr.common.Endpoints;
import heavenhr.exceptions.BadRequestException;
import heavenhr.exceptions.AbstractException;
import heavenhr.exceptions.ConflictException;
import heavenhr.exceptions.ResourceNotFoundException;
import heavenhr.model.Application;
import heavenhr.model.ApplicationStatus;
import heavenhr.model.Offer;
import heavenhr.repository.ApplicationPersistentData;
import heavenhr.repository.OfferPersistentData;
import heavenhr.response.ApplicationStatusResponse;
import heavenhr.response.FailureResponse;
import heavenhr.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HeavenhrApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ApplicationControllerTest extends AbstractTestClass {

	
	HttpHeaders headers = new HttpHeaders();


	@LocalServerPort
	private int port;

	
	@Test
	public void applicationIsNull() throws Exception {
		MockData.clearAllDBContents();
		getOffers().add(MockData.mockOffer());
		headers.setContentType(MediaType.APPLICATION_JSON);
		Application application = null;
		HttpEntity<Application> entity = new HttpEntity<Application>(application, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_NEW, entity, HttpMethod.POST);
		BadRequestException ex = new BadRequestException(Messages.MISSING_EMAIL);
		FailureResponse failureResponseMessage = convertExceptionToFailureMessage(ex);
		String expectedResponseMessage = toJson(failureResponseMessage);
		String actualMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);

	}

	
	@Test
	public void applicationOfferIsNull() throws Exception {
		MockData.clearAllDBContents();
		getOffers().add(MockData.mockOffer());
		headers.setContentType(MediaType.APPLICATION_JSON);
		Application application = new Application();
		application.setEmail("a@b.com");
		HttpEntity<Application> entity = new HttpEntity<Application>(application, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_NEW, entity, HttpMethod.POST);
		BadRequestException ex = new BadRequestException(Messages.APPLICATION_WITHOUT_EMPTY_OFFER);
		FailureResponse failureResponseMessage = convertExceptionToFailureMessage(ex);
		String expectedResponseMessage = toJson(failureResponseMessage);
		String actualMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);

	}

	
	@Test
	public void applyApplicationNoParentOfferException() throws Exception {
		MockData.clearAllDBContents();
		getOffers().add(MockData.mockOffer());
		headers.setContentType(MediaType.APPLICATION_JSON);

		String json = "{\"email\":\"abcd@gmail.com\",\"resumeTxt\": \"Java 4\",\"status\": null,\"offer\": {\"jobTitle\": \"SW1236\"}}";

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_NEW, entity, HttpMethod.POST);

		ResourceNotFoundException ex = new ResourceNotFoundException(Messages.PARENT_OFFER_EXCEPTION);
		FailureResponse failureResponseMessage = convertExceptionToFailureMessage(ex);

		String actualMessage = response.getBody();
		String expectedResponseMessage = toJson(failureResponseMessage);

		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);

	}

	
	@Test
	public void duplicateEmail() throws Exception {
		MockData.clearAllDBContents();
		MockData.createOffer();

		Application mockApplication = MockData.mockApplication();
		mockApplication.setOffer(MockData.mockOffer());
		ApplicationPersistentData db = getApplicationInstance();
		db.save(mockApplication);

		String json = "{\"email\":\"a@b.com\",\"resumeTxt\": \"Java 4\",\"status\": null,\"offer\": {\"jobTitle\": \"1234\"}}";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_NEW, entity, HttpMethod.POST);

		FailureResponse failureResponseMessage = convertExceptionToFailureMessage(new ConflictException(Messages.DUPLICATE_EMAIL));

		String expectedResponseMessage = toJson(failureResponseMessage);
		String actualMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);

	}


	@Test
	public void jobTitleEmpty() throws Exception {
		MockData.clearAllDBContents();
		String json = "{\"email\":\"a@b.com\",\"resumeTxt\": \"Java 4\",\"status\": null,\"offer\": {\"jobTitle\": \"\"}}";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_NEW, entity, HttpMethod.POST);
		String actualMessage = response.getBody();
		FailureResponse failureResponseMessage = new FailureResponse(HttpStatus.BAD_REQUEST,
				"Validation Error", Messages.EMPTY_JOBTITLE);
		String expectedResponseMessage = toJson(failureResponseMessage);
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);
	}

	
	@Test
	public void jobTitleBlank() throws Exception {
		MockData.clearAllDBContents();
		String json = "{\"email\":\"a@b.com\",\"resumeTxt\": \"Java 4\",\"status\": null,\"offer\": {\"jobTitle\": \" \"}}";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_NEW, entity, HttpMethod.POST);
		String actualMessage = response.getBody();

		FailureResponse failureResponseMessage = new FailureResponse(HttpStatus.BAD_REQUEST,
				"Validation Error", Messages.EMPTY_JOBTITLE);
		String expectedResponseMessage = toJson(failureResponseMessage);
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);
	}

	
	@Test
	public void applyApplicationSuccess() throws Exception {
		MockData.clearAllDBContents();

		getOffers().add(MockData.mockOffer("SW1236"));

		Application application = MockData.mockApplication();
		application.setOffer(MockData.mockOffer());
		headers.setContentType(MediaType.APPLICATION_JSON);

		String json = "{\"email\":\"abcd@gmail.com\",\"resumeTxt\": \"Java 4\",\"status\": null,\"offer\": {\"jobTitle\": \"SW1236\"}}";

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_NEW, entity, HttpMethod.POST);

		String expectedResponseMessage = toJson(Response.successApplication());
		String actualMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);

	}

	
	@Test
	public void findApplicationByEmail() throws Exception {
		MockData.clearApplicationDBContents();
		MockData.clearOfferDBContents();
		MockData.createOffer();
		Application mockedApplication = MockData.prepareData();
		getApplicationInstance().getAll().add(mockedApplication);

		HttpEntity<Application> entity = new HttpEntity<Application>(null, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_PATH + "a@b.com", entity,
				HttpMethod.GET);

		String expectedResponseMessage = toJson(mockedApplication);
		String actualResponseMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualResponseMessage, false);
	}

	
	@Test
	public void getApplications() throws Exception {
		MockData.clearApplicationDBContents();
		MockData.clearOfferDBContents();
		MockData.createOffer();
		Application mockedApplication1 = MockData.prepareData();
		Application mockedApplication2 = MockData.prepareData();
		mockedApplication2.setEmail("xyz@gmail.com");
		getApplicationInstance().getAll().add(mockedApplication1);
		ApplicationPersistentData applicationDB = getApplicationInstance();
		List<Application> all = applicationDB.getAll();
		all.add(mockedApplication2);
		HttpEntity<Application> entity = new HttpEntity<Application>(null, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_PATH, entity, HttpMethod.GET);

		String expectedResponseMessage = toJson(all);
		String actualResponseMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualResponseMessage, false);

	}

	
	@Test
	public void getApplicationsEmptyResult() throws Exception {
		MockData.clearApplicationDBContents();
		MockData.clearOfferDBContents();
		MockData.createOffer();

		HttpEntity<Application> entity = new HttpEntity<Application>(null, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_PATH, entity, HttpMethod.GET);
		String expectedResponseMessage = toJson(Response.noApplicationsFound());
		String actualResponseMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualResponseMessage, false);

	}

	@Test
	public void findApplicationByEmailNoResult() throws Exception {
		MockData.clearApplicationDBContents();
		MockData.clearOfferDBContents();
		MockData.createOffer();
		Application mockedApplication = MockData.prepareData();
		getApplicationInstance().getAll().add(mockedApplication);

		HttpEntity<Application> entity = new HttpEntity<Application>(null, headers);
		ResponseEntity<String> response = getResponse(Endpoints.APPLICATION_PATH + "kkk@b.com", entity,
				HttpMethod.GET);
		String expectedResponseMessage = toJson(Response.noApplicationForEmailFound());
		String actualResponseMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualResponseMessage, false);
	}

	
	@Test
	public void updateApplicationStatusSuccess() throws Exception {

		MockData.clearAllDBContents();
		MockData.createOffer();

		Application mockApplication = MockData.mockApplication();
		getApplicationInstance().getAll().add(mockApplication);

		Application mockApplication2 = MockData.mockApplication();
		mockApplication2.setStatus(ApplicationStatus.HIRED);

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Application> entity = new HttpEntity<Application>(mockApplication2, headers);
		ResponseEntity<String> response = getResponse(Endpoints.UPDATE_APPLICATION_STATUS, entity, HttpMethod.PUT);

		String expectedResponseMessage = toJson(
				ApplicationStatusResponse.updateSuccess(mockApplication2.getStatus()));
		String actualResponseMessage = response.getBody();
		assertEquals(expectedResponseMessage, actualResponseMessage);
	}

	
	@Test
	public void updateApplicationStatusNull() throws Exception {

		MockData.clearAllDBContents();
		MockData.createOffer();

		getApplicationInstance().getAll().add(MockData.mockApplication());

		String json = "{\"email\":\"a@b.com\",\"resumeTxt\": \"Java 4\",\"status\":null, \"offer\": {\"jobTitle\": \"SW100\"}}";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Endpoints.UPDATE_APPLICATION_STATUS, entity, HttpMethod.PUT);
		BadRequestException ex = new BadRequestException(Messages.STATUS_NULL);
		FailureResponse message = convertExceptionToFailureMessage(ex);

		String expectedResponseMessage = toJson(message);
		String actualResponseMessage = response.getBody();
		assertEquals(expectedResponseMessage, actualResponseMessage);
	}

	
	@Test
	public void updateApplicationStatusEmpty() throws Exception {

		MockData.clearAllDBContents();
		MockData.createOffer();
		getApplicationInstance().getAll().add(MockData.mockApplication());		
		String json = "{\"email\":\"a@b.com\",\"resumeTxt\": \"Java 4\",\"status\":null, \"offer\": {\"jobTitle\": \"SW100\"}}";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Endpoints.UPDATE_APPLICATION_STATUS, entity, HttpMethod.PUT);
		BadRequestException ex = new BadRequestException(Messages.STATUS_NULL);
		FailureResponse message = convertExceptionToFailureMessage(ex);

		String expectedResponseMessage = toJson(message);
		String actualResponseMessage = response.getBody();
		assertEquals(expectedResponseMessage, actualResponseMessage);
	}

	

	@Override
	public int port() {
		return port;
	}
	
	public static FailureResponse convertExceptionToFailureMessage(AbstractException ex) {
		return new FailureResponse(ex.getHttpStatus(), "Server error", ex.getMessage());
	}
	
	private List<Offer> getOffers(){
		return OfferPersistentData.getInstance().getOffers();
	}
	
	private ApplicationPersistentData getApplicationInstance(){
		return ApplicationPersistentData.getInstance();
	}

}
