package wss.trader;

/**
 * GreedyTrader makes offers heavily in their own favor.
 * Rarely accepts counteroffers unless they’re extremely generous.
 */
public class GreedyTrader extends Trader {

    public GreedyTrader() {
        // Very narrow, selfish ranges
        this.foodOfferRange = new Range(0, 1);   // Offers very little
        this.waterOfferRange = new Range(0, 1);
        this.goldOfferRange = new Range(0, 1);

        this.foodRequestRange = new Range(2, 4); // Demands a lot
        this.waterRequestRange = new Range(2, 4);
        this.goldRequestRange = new Range(2, 5);
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
        // Calculate player-to-trader value and reverse
        int offered = offer.getFoodOffered() + offer.getWaterOffered() + offer.getGoldOffered() * 2;
        int requested = offer.getFoodRequested() + offer.getWaterRequested() + offer.getGoldRequested() * 2;

        // Greedy: accept only if player gives 2x more than they ask
        return offered >= requested * 2;
    }
}
