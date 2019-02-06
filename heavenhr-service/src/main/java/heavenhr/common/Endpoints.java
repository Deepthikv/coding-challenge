package heavenhr.common;

/**
 * End points for the applications
 * 
 * @author Deepthi KV
 *
 */
public interface Endpoints {

	/**
	 * Base path for the path
	 */
	String BASE_PATH = "/api/v1/";

	/**
	 * Application related to offer
	 */
	String APPLICATION_PATH = BASE_PATH + "applications/";

	/**
	 * Path related to offer
	 */
	String OFFER_PATH = BASE_PATH + "offers/";

	/**
	 * New application
	 */
	String APPLICATION_NEW = APPLICATION_PATH + "new";

	/**
	 * updates the status
	 */
	String UPDATE_APPLICATION_STATUS = APPLICATION_PATH + "/update/status";

	/**
	 * new offer
	 */
	String OFFER_NEW = OFFER_PATH + "new";

	/**
	 * number of applications
	 */
	String NUMBER_OF_APPLICATIONS = OFFER_PATH + "applications/";

}
