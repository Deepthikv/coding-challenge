package heavenhrservice.heavenhr;

import java.util.List;

import heavenhr.model.Application;
import heavenhr.model.Offer;
import heavenhr.repository.ApplicationPersistentData;
import heavenhr.repository.OfferRepository;

public class MockData {
	
	public static void clearApplicationDBContents() {
		ApplicationPersistentData.getInstance().getAll().clear();
	}

	/**
	 * Creates the offer object in DB
	 */
	public static void createOffer() {
		getOfferRepository().createOffer(mockOffer());		
	}

	/**
	 * Clear the contents of Offer DB
	 */
	public static void clearOfferDBContents() {
		getOffers().clear();
	}
	
	public static Offer mockOffer() {
		return mockOffer("1234");
		
	}
	
	public static Offer mockOffer(String jobTitle) {
		Offer offer = new Offer();
		offer.setJobTitle(jobTitle);
		return offer;
	}
	
	public static void commonTestData() {
		clearApplicationDBContents();
		clearOfferDBContents();
		createOffer();
	}
	
	public static void clearAllDBContents() {
		clearApplicationDBContents();
		clearOfferDBContents();
		
	}
	
	public static Application prepareData() {
		Application mockedApplication = mockApplication();
		mockedApplication.setOffer(mockOffer());
		return mockedApplication;
	}

	public static Application mockApplication() {
		return mockApplication("a@b.com");
	}

	

	public static Application mockApplication(String email) {
		Application application = new Application();
		application.setEmail(email);
		return application;
	}
	
	private static List<Offer> getOffers(){
		return getOfferRepository().getOffers();
	}
	
	private static OfferRepository getOfferRepository(){
		return new OfferRepository();
	}
	

}
