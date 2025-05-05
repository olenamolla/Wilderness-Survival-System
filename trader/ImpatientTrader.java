package wss.trader;

// ImpatientTrader makes normal offers. 
// Will quit trading if the Player makes too many counteroffers. 
// Accepts good counteroffers early but quits after being annoyed.

public class ImpatientTrader extends Trader {
    
    private int counterOffersSeen;
    private final int maxCounterOffers = 3; // Max counteroffers that Impatient player is willing to wait for

    public ImpatientTrader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        super(tradesRemaining, foodRange, waterRange, goldRange);
        this.counterOffersSeen = 0;
    }

    /**
     * Accepts if offer is fair or equal.
     * Otherwise -> more annoyance and high chance of the player to stop trading.
     *
     * @param offer the TradeOffer 
     * @return true if accepted, false is opposite
     */
    @Override
    public boolean acceptTrade(TradeOffer offer) {
        if (counterOffersSeen >= maxCounterOffers) {
            System.out.println("[ImpatientTrader] no longer wants to trade. Way too many counteroffers!");
            return false;
        }

        int offerValue = offer.getFoodOffered() + offer.getWaterOffered() + offer.getGoldOffered();
        int requestValue = offer.getFoodRequested() + offer.getWaterRequested() + offer.getGoldRequested();

        if (offerValue >= requestValue) {
            return true;
        } else {
            counterOffersSeen++;
            System.out.println("[ImpatientTrader] rejected offer #" + counterOffersSeen);
            return false;
        }
    }
}