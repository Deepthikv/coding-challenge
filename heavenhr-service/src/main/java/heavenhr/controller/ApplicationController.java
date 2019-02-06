package heavenhr.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import heavenhr.common.Endpoints;
import heavenhr.model.Application;
import heavenhr.response.ApplicationStatusResponse;
import heavenhr.response.Response;
import heavenhr.service.ApplicationService;

/**
 * Application controller for application requests
 * 
 * @author Deepthi KV
 *
 */
@RestController
public class ApplicationController {

	/**
	 * application service
	 */
	@Autowired
	private ApplicationService applicationService;

	/**
	 * Creates a new application. For creating a new application,an offer is
	 * mandatory.
	 * 
	 * @param application
	 * @return
	 */
	@PostMapping(value = Endpoints.APPLICATION_NEW, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> newApplication(@Valid @RequestBody(required = false) Application application) {
		applicationService.createApplication(application);
		Response message = Response.successApplication();
		return new ResponseEntity<Response>(message, message.getHttpstatus());
	}

	/**
	 * Updates the application status
	 * 
	 * @param application
	 * @return
	 */
	@PutMapping(Endpoints.UPDATE_APPLICATION_STATUS)
	public ResponseEntity<?> updateApplicationStatus(@RequestBody Application application) {
		Application updateApplicationStatus = applicationService.updateApplicationStatus(application.getEmail(),
				application.getStatus());
		if (updateApplicationStatus == null) {
			Response message = Response.couldNotUpdateStatusEmailExists();
			return new ResponseEntity<Response>(message, message.getHttpstatus());
		}
		return new ResponseEntity<Response>(
				ApplicationStatusResponse.updateSuccess(updateApplicationStatus.getStatus()), HttpStatus.OK);
	}

	/**
	 * Find an application based on the email.
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping(value = Endpoints.APPLICATION_PATH
			+ "{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findApplicationByEmail(@PathVariable String email) {
		Application findApplicationByEmail = applicationService.findApplicationByEmail(email);
		if (findApplicationByEmail == null) {
			Response responseMessage = Response.noApplicationForEmailFound();
			return new ResponseEntity<Response>(responseMessage, responseMessage.getHttpstatus());
		}
		return new ResponseEntity<Application>(findApplicationByEmail, HttpStatus.OK);
	}

	/**
	 * Retrieves all the applications 
	 * 
	 * @return
	 */
	@RequestMapping(value = Endpoints.APPLICATION_PATH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getApplications() {
		List<Application> applications = applicationService.getAllApplications();
		if (applications.isEmpty()) {
			Response message = Response.noApplicationsFound();
			return new ResponseEntity<Response>(Response.noApplicationsFound(), message.getHttpstatus());
		}
		return new ResponseEntity<List<Application>>(applications, HttpStatus.OK);
	}

}
