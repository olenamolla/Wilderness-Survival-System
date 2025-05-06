package wss.trader;

/**
 * RegularTrader generates fair trades and accepts any counteroffer
 * where the value given is at least equal to the value requested.
 * Never gets annoyed.
 */
public class RegularTrader extends Trader {

    public RegularTrader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        super(tradesRemaining, foodRange, waterRange, goldRange);
    }

    @Override
    public boolean acceptTrade(TradeOffer playerCounter) {
        if (currentOffer == null) return false;

        int playerValue = playerCounter.getOfferedValue();
        int traderValue = currentOffer.getRequestedValue();

        return playerValue >= traderValue;
    }
}
