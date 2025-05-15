package wss.trader;

import java.util.Random;

/**
 * RegularTrader generates fair trades and accepts any counteroffer
 * where the value given is at least equal to the value requested.
 * They aim for balanced trades but have some flexibility.
 */
public class RegularTrader extends Trader {
    private static final double FAIRNESS_MARGIN = 0.2; // 20% flexibility in either direction
    private static final Random random = new Random();

    public RegularTrader(int tradesRemaining, Range foodRange, Range waterRange, Range goldRange) {
        super(tradesRemaining, foodRange, waterRange, goldRange);
        // Regular traders use balanced request ranges
        this.requestFoodRange = new Range(2, 4);
        this.requestWaterRange = new Range(2, 4);
        this.requestGoldRange = new Range(2, 4);
    }

    @Override
    public TradeOffer offerTrade() {
        if (tradesRemaining <= 0) return null;
        tradesRemaining--;

        // Generate base values
        int foodOffered = foodRange.getRandomValue();
        int waterOffered = waterRange.getRandomValue();
        int goldOffered = goldRange.getRandomValue();

        // Request balanced amounts
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
        int traderValue = currentOffer.getRequestedValue();

        // Regular trader accepts if the offer is within their fairness margin
        double ratio = (double)playerValue / traderValue;
        return ratio >= (1.0 - FAIRNESS_MARGIN);
    }
}
