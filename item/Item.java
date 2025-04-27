package wss.item;

import wss.player.Player;

/**
 * Abstract base class representing an item that can be found on a map square.
 * Items can affect the player by giving bonuses like food, water, or gold.
 * Some items may be repeating, allowing the player to collect them multiple times.
 */

public abstract class Item {
    
    /** Name of the item (e.g., "Food Bonus", "Water Bonus") */
    protected String name;

    /** Whether the item is repeating */
    protected boolean repeating;

    /**
     * Constructs an item with a specified name and repeatability.
     * 
     * @param name The name of the item
     * @param repeating True if the item can be collected multiple times, false if one-time only
     */
    public Item(String name, boolean repeating) {
        this.name = name;
        this.repeating = repeating;
    }

    /**
     * Applies the effect of this item to the player.
     * 
     * Each subclass (FoodBonus, WaterBonus, GoldBonus) will implement how it affects the player.
     *
     * @param player The player who is collecting the item
     */
    public abstract void applyTo(Player player);


    /**
     * Returns the name of the item.
     * @return Item name
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the item is repeating.
     * @return True if repeating, false otherwise
     */
    public boolean isRepeating() {
        return repeating;
    }
}
