package wss.trader;

public abstract class Trader {
    protected int tradesRemaining;
    protected Range foodRange;
    protected Range waterRange;
    protected Range goldRange;

    protected TradeOffer currentOffer;

    public Trader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        this.tradesRemaining = tradesRemaining;
        this.foodRange = foodRange;
        this.waterRange = waterRange;
        this.goldRange = goldRange;
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
