package wss.trader;

public abstract class Trader {
    protected int tradesRemaining;
    protected Range foodRange;
    protected Range waterRange;
    protected Range goldRange;
    protected Range requestFoodRange;
    protected Range requestWaterRange;
    protected Range requestGoldRange;

    protected TradeOffer currentOffer;

    public Trader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        this.tradesRemaining = tradesRemaining;
        this.foodRange = foodRange != null ? foodRange : new Range(1, 3);
        this.waterRange = waterRange != null ? waterRange : new Range(1, 3);
        this.goldRange = goldRange != null ? goldRange : new Range(1, 3);
        this.requestFoodRange = new Range(2, 5);
        this.requestWaterRange = new Range(2, 5);
        this.requestGoldRange = new Range(2, 5);
    }

    /**
     * Generate and store a TradeOffer. Decrease tradesRemaining.
     */
    public TradeOffer offerTrade() {
        if (tradesRemaining <= 0) return null;
        tradesRemaining--;

        this.currentOffer = new TradeOffer(
            foodRange.getRandomValue(),
            waterRange.getRandomValue(),
            goldRange.getRandomValue(),
            foodRange.getRandomValue(),
            waterRange.getRandomValue(),
            goldRange.getRandomValue()
        );
        return currentOffer;
    }

    /**
     * Decide whether to accept a counter-offer.
     * Default logic: accept if player's offer >= our demand.
     */
    public boolean acceptTrade(TradeOffer counter) {
        if (currentOffer == null) return false;

        int offered = counter.getOfferedValue();
        int requested = currentOffer.getRequestedValue();

        return offered >= requested;
    }

    public int getTradesRemaining() {
        return tradesRemaining;
    }

    public String getTradeStatus() {
        return "Trades remaining: " + tradesRemaining;
    }

    public TradeOffer getCurrentOffer() {
        return currentOffer;
    }

    public String getName() {
        return this.getClass().getSimpleName(); // e.g., returns "RegularTrader"
    }
}
