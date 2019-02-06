package heavenhr.repository;

import java.util.List;

import heavenhr.model.Offer;

public interface IOfferRepository<E, P>  {

	

	/**
	 * @return
	 */
	List<Offer> getOffers();

	/**
	 * @param p
	 * @return
	 */
	Offer findOffer(String p);

	/**
	 * @param e
	 */
	void createOffer(Offer e);

}
