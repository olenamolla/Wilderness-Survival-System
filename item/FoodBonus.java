package wss.item;

import wss.player.Player;

/**
 * Represents a Food Bonus item that increases the player's food level when collected.
 * Extends the abstract Item class.
 */

public class FoodBonus extends Item {
    
    /** The amount of food the player gains when collecting this bonus */
    private int amount;

    /**
     * Constructs a FoodBonus with a specified food amount.
     * By default, food bonuses are not repeating (can be configured if needed).
     */
    public FoodBonus() {
        super("Food Bonus", false); // Name and repeating set here
        this.amount = 10; // Default food gain amount (can adjust if needed)
    }

    /**
     * Constructs a FoodBonus with specified food amount and repeatability.
     * 
     * @param amount How much food the player gains
     * @param repeating Whether this bonus can be collected multiple times
     */
    public FoodBonus(int amount, boolean repeating) {
        super("Food Bonus", repeating);
        this.amount = amount;
    }

    /**
     * Applies the food bonus to the player.
     * Increases the player's current food by the bonus amount.
     *
     * @param player The player collecting the food bonus
     */
    @Override
    public void applyTo(Player player) {
        System.out.println("[FoodBonus] Applying food bonus of " + amount + " to player " + player.getName());
        player.increaseFood(amount); // Player will have a method like increaseFood(int)
    }

    /**
     * Returns the amount of food this bonus provides.
     * @return Food amount
     */
    public int getAmount() {
        return amount;
    }
}
