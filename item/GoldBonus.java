package wss.item;

import wss.player.Player;

/**
 * Represents a Gold Bonus item that increases the player's gold when collected.
 * Extends the abstract Item class.
 */
public class GoldBonus extends Item {
    
    /** The amount of gold the player gains when collecting this bonus */
    private int amount;
    
    /**
     * Constructs a GoldBonus with a default gold amount.
     * By default, gold bonuses are not repeating (can be configured if needed).
     */
    public GoldBonus() {
        super("Gold Bonus", false); // Name and repeating set here
        this.amount = 5; // Default gold gain amount
    }

    /**
     * Constructs a GoldBonus with a specified gold amount and repeatability.
     * 
     * @param amount How much gold the player gains
     * @param repeating Whether this bonus can be collected multiple times
     */
    public GoldBonus(int amount, boolean repeating) {
        super("Gold Bonus", repeating);
        this.amount = amount;
    }

    /**
     * Applies the gold bonus to the player.
     * Increases the player's current gold by the bonus amount.
     *
     * @param player The player collecting the gold bonus
     */
    @Override
    public void applyTo(Player player) {
        System.out.println("[GoldBonus] Applying gold bonus of " + amount + " to player " + player.getName());
        player.increaseGold(amount); // WPlayer will have a method like increaseGold(int)
    }

    /**
     * Returns the amount of gold this bonus provides.
     * @return Gold amount
     */
    public int getAmount() {
        return amount;
    }


}
