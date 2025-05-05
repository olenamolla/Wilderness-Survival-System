package wss.trader;

/**
 * RegularTrader makes fair offers and accepts
 * any counteroffer that fits within its original ranges.
 */
public class RegularTrader extends Trader {

    public RegularTrader() {
        // Balanced offer and request ranges
        this.foodOfferRange = new Range(1, 3);
        this.waterOfferRange = new Range(1, 3);
        this.goldOfferRange = new Range(0, 2);

        this.foodRequestRange = new Range(1, 2);
        this.waterRequestRange = new Range(1, 2);
        this.goldRequestRange = new Range(0, 2);

        this.patience = Integer.MAX_VALUE; // Doesn't get mad
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
        // Accept if all offered/requested values are within our original bounds
        boolean foodOK = foodOfferRange.contains(offer.getFoodOffered()) &&
                         foodRequestRange.contains(offer.getFoodRequested());

        boolean waterOK = waterOfferRange.contains(offer.getWaterOffered()) &&
                          waterRequestRange.contains(offer.getWaterRequested());

        boolean goldOK = goldOfferRange.contains(offer.getGoldOffered()) &&
                         goldRequestRange.contains(offer.getGoldRequested());

        return foodOK && waterOK && goldOK;
    }
}
