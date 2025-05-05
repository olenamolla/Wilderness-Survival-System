package wss.trader;

// RegularTrader offers trades using their ranges. 
//Accepts any reasonable counteroffer that fits their original ranges. 
//Does not quit or get mad.


public class RegularTrader extends Trader {

    public RegularTrader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        super(tradesRemaining, foodRange, waterRange, goldRange);
    }

    /**
     * A regular trader accepts the trade if the offered value is at least equal to what's requested.
     *
     * @param offer the TradeOffer being evaulated
     * @return true if the offer is fair or at least equal, false is opposite
     */
    @Override
    public boolean acceptTrade(TradeOffer offer) {
        int offerValue = offer.getFoodOffered() + offer.getWaterOffered() + offer.getGoldOffered();
        int requestValue = offer.getFoodRequested() + offer.getWaterRequested() + offer.getGoldRequested();
        return offerValue >= requestValue;
    }
}