package wss.trader;

/**
 * GreedyTrader generates very selfish trade offers and
 * only accepts counteroffers that are heavily in their favor.
 */
public class GreedyTrader extends Trader {

    public GreedyTrader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        super(tradesRemaining, foodRange, waterRange, goldRange);
    }

    @Override
    public boolean acceptTrade(TradeOffer playerCounter) {
        if (currentOffer == null) return false;

        int playerValue = playerCounter.getOfferedValue();
        int traderValue = playerCounter.getRequestedValue();  // What the trader is giving away

        // GreedyTrader only accepts if they gain 2x more than they give
        return playerValue >= (traderValue * 2);
    }
}
