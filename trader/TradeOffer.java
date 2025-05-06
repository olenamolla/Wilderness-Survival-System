package wss.trader;

/**
 * Represents a trade offer during trading between a Player and a Trader.
 * 
 * Each offer contains:
 * - What the offerer is giving (food, water, gold)
 * - What the offerer is requesting in return (food, water, gold)
 */

public class TradeOffer {
    
    /** Amount of food being offered */
    private int foodOffered;

    /** Amount of water being offered */
    private int waterOffered;

    /** Amount of gold being offered */
    private int goldOffered;

    /** Amount of food being requested in return */
    private int foodRequested;

    /** Amount of water being requested in return */
    private int waterRequested;

    /** Amount of gold being requested in return */
    private int goldRequested;

    /**
     * Constructs a TradeOffer specifying both the offered and requested resources.
     *
     * @param foodOffered Amount of food being offered
     * @param waterOffered Amount of water being offered
     * @param goldOffered Amount of gold being offered
     * @param foodRequested Amount of food being requested
     * @param waterRequested Amount of water being requested
     * @param goldRequested Amount of gold being requested
     */
    public TradeOffer(int foodOffered, int waterOffered, int goldOffered,
                      int foodRequested, int waterRequested, int goldRequested) {
        this.foodOffered = foodOffered;
        this.waterOffered = waterOffered;
        this.goldOffered = goldOffered;
        this.foodRequested = foodRequested;
        this.waterRequested = waterRequested;
        this.goldRequested = goldRequested;
    }

    public int getFoodOffered() {
        return foodOffered;
    }

    public int getWaterOffered() {
        return waterOffered;
    }

    public int getGoldOffered() {
        return goldOffered;
    }

    public int getFoodRequested() {
        return foodRequested;
    }

    public int getWaterRequested() {
        return waterRequested;
    }

    public int getGoldRequested() {
        return goldRequested;
    }

    public int getOfferedValue() {
        return foodOffered + waterOffered + (goldOffered * 2);
    }
    
    public int getRequestedValue() {
        return foodRequested + waterRequested + (goldRequested * 2);
    }
    
    /**
     * Prints the details of the trade offer to the console.
     * Useful for debugging and showing the trade to the player.
     */
    public void printOffer() {
        System.out.println("[TradeOffer] Offer Details:");
        System.out.println("- Offering: " + foodOffered + " food, " + waterOffered + " water, " + goldOffered + " gold");
        System.out.println("- Requesting: " + foodRequested + " food, " + waterRequested + " water, " + goldRequested + " gold");
    }
}
