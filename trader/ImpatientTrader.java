package wss.trader;

/**
 * ImpatientTrader makes normal offers but gets annoyed quickly.
 * Will reject trade after 3 failed counteroffers.
 */
public class ImpatientTrader extends Trader {

    private int counterOffersSeen;
    private final int maxCounterOffers = 3;

    public ImpatientTrader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        super(tradesRemaining, foodRange, waterRange, goldRange);
        this.counterOffersSeen = 0;
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

        if (playerValue >= traderValue) {
            return true; // Accept fair or better counter
        } else {
            counterOffersSeen++;
            System.out.println("[ImpatientTrader] Offer rejected (" + counterOffersSeen + "/" + maxCounterOffers + ")");
            return false;
        }
    }
}
