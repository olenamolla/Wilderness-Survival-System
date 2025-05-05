package wss.trader;

// GreedyTrader generates offers favoring themselves (gives low, demands high). 
// When evaluating counteroffers, accepts only if the Player’s offer is very favorable. 
// Doesn't quit but often rejects.

public class GreedyTrader extends Trader {

    public GreedyTrader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        super(tradesRemaining, foodRange, waterRange, goldRange);
    }

    /**
     *  Greedy Trader accepts the trade only if the offered value is greater.
     *
     * @param offer the TradeOffer being evaluated.
     * @return true if the trader gains more than they lose, false is opposite
     */
    @Override
    public boolean acceptTrade(TradeOffer offer) {
        int offerValue = offer.getFoodOffered() + offer.getWaterOffered() + offer.getGoldOffered();
        int requestValue = offer.getFoodRequested() + offer.getWaterRequested() + offer.getGoldRequested();
        return offerValue > requestValue;
    }
}