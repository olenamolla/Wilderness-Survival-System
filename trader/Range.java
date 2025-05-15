package wss.trader;

import java.util.Random;

/**
 * Represents an inclusive range of integer values (min to max).
 * Used by Traders to control the amount of food, water, or gold they can offer or request.
 */

public class Range {
    
    /** Minimum value in the range (inclusive) */
    private int min;

    /** Maximum value in the range (inclusive) */
    private int max;

    private static final Random random = new Random();

    /**
     * Creates a new Range with a specified minimum and maximum.
     * 
     * @param min The minimum allowed value (inclusive)
     * @param max The maximum allowed value (inclusive)
     * @throws IllegalArgumentException if min > max
     */
    public Range(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("[Range] Error: min cannot be greater than max.");
        }
        // Ensure minimum value is at least 1 for meaningful trades
        this.min = Math.max(1, min);
        this.max = Math.max(this.min, max);
    }

    /**
     * Checks if a given value falls within this range (inclusive).
     * 
     * @param value The value to check
     * @return true if value is between min and max (inclusive), false otherwise
     */

    // When used:
    // - When the Player proposes a trade offer to the Trader.
    // - The Trader will use this method to check if the Player's offer/request is acceptable.
    // Player -> Trader 
    public boolean contains(int value) {
        return value >= min && value <= max;
    }

    /**
     * Returns a random integer between min and max (inclusive).
     * 
     * @return A random value within the range
     */
    // When used:
    // - When the Trader generates a new trade offer to propose to the Player.
    // Trader -> Player
    public int getRandomValue() {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Returns the minimum value of the range.
     * @return Minimum value
     */
    public int getMin() {
        return min;
    }

    /**
     * Returns the maximum value of the range.
     * @return Maximum value
     */
    public int getMax() {
        return max;
    }

}
