package wss.trader;

/**
 * ImpatientTrader makes normal offers but gets annoyed quickly.
 * They're more likely to accept slightly unfair deals when they're close to their limit.
 * Will reject trade after 3 failed counteroffers.
 */
public class ImpatientTrader extends Trader {

    private int counterOffersSeen;
    private final int maxCounterOffers = 3;
    private static final double ACCEPTANCE_THRESHOLD = 0.8; // Base threshold for accepting trades
    private static final double PATIENCE_BONUS = 0.1; // Additional threshold per remaining counter offer
    private Range requestFoodRange;
    private Range requestWaterRange;
    private Range requestGoldRange;

    public ImpatientTrader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        super(tradesRemaining, foodRange, waterRange, goldRange);
        this.counterOffersSeen = 0;
        // Impatient traders use slightly better request ranges but are more flexible
        this.requestFoodRange = new Range(2, 5);
        this.requestWaterRange = new Range(2, 5);
        this.requestGoldRange = new Range(2, 5);
    }

    @Override
    public TradeOffer offerTrade() {
        if (tradesRemaining <= 0) return null;
        tradesRemaining--;

        // Generate base values
        int foodOffered = foodRange.getRandomValue();
        int waterOffered = waterRange.getRandomValue();
        int goldOffered = goldRange.getRandomValue();

        // Request slightly more than offering but less than GreedyTrader
        int foodRequested = requestFoodRange.getRandomValue();
        int waterRequested = requestWaterRange.getRandomValue();
        int goldRequested = requestGoldRange.getRandomValue();

        this.currentOffer = new TradeOffer(
            foodOffered, waterOffered, goldOffered,
            foodRequested, waterRequested, goldRequested
        );
        return currentOffer;
    }

    @Override
    public boolean acceptTrade(TradeOffer playerCounter) {
        if (currentOffer == null) return false;

        if (counterOffersSeen >= maxCounterOffers) {
            System.out.println("[ImpatientTrader] No longer interested. Too many counteroffers.");
            return false;
        }

        int playerValue = playerCounter.getOfferedValue();
        int traderValue = currentOffer.getRequestedValue();

        // Calculate acceptance threshold based on remaining patience
        double currentThreshold = ACCEPTANCE_THRESHOLD + 
            (PATIENCE_BONUS * (maxCounterOffers - counterOffersSeen));

        // Accept if the offer meets the current threshold
        if ((double)playerValue / traderValue >= currentThreshold) {
            return true;
        } else {
            counterOffersSeen++;
            System.out.println("[ImpatientTrader] Offer rejected (" + counterOffersSeen + "/" + maxCounterOffers + 
                "). Need at least " + (int)(currentThreshold * 100) + "% of requested value.");
            return false;
        }
    }
}
