package wss.trader;

// import wss.trader.TradeOffer;

public abstract class Trader {
    protected int tradesRemaining;
    protected Range foodRange;
    protected Range waterRange;
    protected Range goldRange;

    public Trader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        this.tradesRemaining = tradesRemaining;
        this.foodRange = foodRange;
        this.waterRange = waterRange;
        this.goldRange = goldRange;
    }

    /**
     * Generates a trade offer based on each resource (type varies) range.
     * Decrements (-) tradesRemaining and returns null for none.
     */
    public TradeOffer offerTrade() {
        if (tradesRemaining <= 0) {
            return null;
        }
        tradesRemaining--;
        int fOff = foodRange.getRandomValue();
        int wOff = waterRange.getRandomValue();
        int gOff = goldRange.getRandomValue();
        int fReq = foodRange.getRandomValue();
        int wReq = waterRange.getRandomValue();
        int gReq = goldRange.getRandomValue();
        return new TradeOffer(fOff, wOff, gOff, fReq, wReq, gReq);
    }

    /**
     *  deciding whether to accept a counter-offer.
     * @param offer the player's TradeOffer
     * @return true if Trader accepts, false is opposite
     */
    public abstract boolean acceptTrade(TradeOffer offer);

    public int getTradesRemaining() {
        return tradesRemaining;
    }

    /**
     * @return a simple msg of remaining trades (for player)
     */
    public String getTradeStatus() {
        return "Trades remaining: " + tradesRemaining;
    }
}
