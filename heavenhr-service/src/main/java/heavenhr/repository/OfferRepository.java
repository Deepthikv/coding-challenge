package heavenhr.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import heavenhr.model.Offer;

/**
 * 
 * @author  Deepthi KV
 *
 */
@Repository
public class OfferRepository implements IOfferRepository<Offer, String> {

	private OfferPersistentData offerDB = OfferPersistentData.getInstance();

	@Override
	public List<Offer> getOffers() {
		return offerDB.getOffers();
	}

	@Override
	public Offer findOffer(String p) {
		return offerDB.getOffer(p);

	}
	@Override
	public void createOffer(Offer e) {
		offerDB.save(e);

	}
}
