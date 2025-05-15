package wss.trader;

/**
 * GreedyTrader focuses primarily on gold trades and is less aggressive with food and water.
 * They try to maximize their gold profit and are willing to wait for the best deal.
 */
public class GreedyTrader extends Trader {
    private static final double MIN_PROFIT_MARGIN = 1.5; // Minimum profit margin they want
    private static final double COUNTER_OFFER_MARGIN = 2.0; // Required margin for counter offers
    private static final double GOLD_MARGIN = 2.5; // Higher margin for gold trades

    public GreedyTrader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        super(tradesRemaining, foodRange, waterRange, goldRange);
        // Greedy traders are less aggressive with food and water, but very aggressive with gold
        this.requestFoodRange = new Range(1, 2);  // Minimal food requests
        this.requestWaterRange = new Range(1, 2); // Minimal water requests
        this.requestGoldRange = new Range(4, 8);  // Aggressive gold requests
    }

    @Override
    public TradeOffer offerTrade() {
        if (tradesRemaining <= 0) return null;
        tradesRemaining--;

        // Generate base values
        int foodOffered = foodRange.getRandomValue();
        int waterOffered = waterRange.getRandomValue();
        int goldOffered = goldRange.getRandomValue();

        // Request minimal food/water but lots of gold
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

        int playerValue = playerCounter.getOfferedValue();
        int traderValue = playerCounter.getRequestedValue();  // What the trader is giving away

        // Calculate value ratios for each resource type
        double foodRatio = (double)playerCounter.getFoodOffered() / playerCounter.getFoodRequested();
        double waterRatio = (double)playerCounter.getWaterOffered() / playerCounter.getWaterRequested();
        double goldRatio = (double)playerCounter.getGoldOffered() / playerCounter.getGoldRequested();

        // Accept if:
        // 1. Overall value is good (playerValue >= traderValue * COUNTER_OFFER_MARGIN)
        // 2. Gold ratio is especially good (goldRatio >= GOLD_MARGIN)
        // 3. Food and water ratios are at least fair (>= 1.0)
        return playerValue >= (traderValue * COUNTER_OFFER_MARGIN) &&
               goldRatio >= GOLD_MARGIN &&
               foodRatio >= 1.0 &&
               waterRatio >= 1.0;
    }
}
