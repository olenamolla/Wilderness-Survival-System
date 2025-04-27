package wss.item;

import wss.player.Player;

/**
 * Represents a Water Bonus item that increases the player's water level when collected.
 * Extends the abstract Item class.
 */
public class WaterBonus extends Item {

    /** The amount of water the player gains when collecting this bonus */
    private int amount;

    /**
     * Constructs a WaterBonus with a default water amount.
     * By default, water bonuses are not repeating (can be configured if needed).
     */
    public WaterBonus() {
        super("Water Bonus", false); // Name and repeating set here
        this.amount = 10; // Default water gain amount
    }

    /**
     * Constructs a WaterBonus with a specified water amount and repeatability.
     * 
     * @param amount How much water the player gains
     * @param repeating Whether this bonus can be collected multiple times
     */
    public WaterBonus(int amount, boolean repeating) {
        super("Water Bonus", repeating);
        this.amount = amount;
    }

    /**
     * Applies the water bonus to the player.
     * Increases the player's current water by the bonus amount.
     *
     * @param player The player collecting the water bonus
     */
    @Override
    public void applyTo(Player player) {
        System.out.println("[WaterBonus] Applying water bonus of " + amount + " to player " + player.getName());
        player.increaseWater(amount); // Player will have a method like increaseWater(int)
    }

    /**
     * Returns the amount of water this bonus provides.
     * @return Water amount
     */
    public int getAmount() {
        return amount;
    }
}
