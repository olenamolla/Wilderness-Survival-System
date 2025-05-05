package wss.trader;

import java.util.Random;

/**
 * Abstract Trader class — base for all Trader personalities.
 * Defines shared structure and behavior.
 */
public abstract class Trader {

    protected Range foodOfferRange;
    protected Range waterOfferRange;
    protected Range goldOfferRange;

    protected Range foodRequestRange;
    protected Range waterRequestRange;
    protected Range goldRequestRange;

    protected int frustration = 0;
    protected int patience = 3; // default before becoming annoyed

    protected Random random = new Random();

    // Generate initial offer to the player
    public abstract TradeOffer generateOffer();

    // Evaluate a counter-offer from the player
    public abstract boolean evaluateCounterOffer(TradeOffer offer);

    // Called when the player makes a counteroffer
    public void incrementFrustration() {
        frustration++;
    }

    public boolean isAnnoyed() {
        return frustration >= patience;
    }

    // Shared helper for random offer generation
    protected int randomBetween(Range range) {
        return range.getRandomValue();
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }
}
