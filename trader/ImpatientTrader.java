package wss.trader;

/**
 * ImpatientTrader behaves reasonably at first,
 * but gets annoyed quickly after repeated counteroffers.
 */
public class ImpatientTrader extends Trader {

    public ImpatientTrader() {
        // Normal ranges — fair but not too generous
        this.foodOfferRange = new Range(1, 3);
        this.waterOfferRange = new Range(1, 3);
        this.goldOfferRange = new Range(0, 2);

        this.foodRequestRange = new Range(1, 2);
        this.waterRequestRange = new Range(1, 2);
        this.goldRequestRange = new Range(0, 2);

        this.patience = 2; // Only 2 bad counters before quitting
    }

    @Override
    public TradeOffer generateOffer() {
        return new TradeOffer(
            foodOfferRange.getRandomValue(),
            waterOfferRange.getRandomValue(),
            goldOfferRange.getRandomValue(),
            foodRequestRange.getRandomValue(),
            waterRequestRange.getRandomValue(),
            goldRequestRange.getRandomValue()
        );
    }

    @Override
    public boolean evaluateCounterOffer(TradeOffer offer) {
        if (isAnnoyed()) {
            System.out.println("[ImpatientTrader] Ugh. I'm done here.");
            return false;
        }

        int offered = offer.getFoodOffered() + offer.getWaterOffered() + offer.getGoldOffered() * 2;
        int requested = offer.getFoodRequested() + offer.getWaterRequested() + offer.getGoldRequested() * 2;

        // Accept if the offer is slightly better or even
        boolean accept = offered >= requested;
        if (!accept) {
            incrementFrustration(); // Get more annoyed
        }
        return accept;
    }
}
