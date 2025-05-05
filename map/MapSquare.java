package wss.map;

/**
 * Represents a single square on the game map.
 * Each square has:
 * - A terrain (like forest, desert, etc.)
 * - A list of items (food, water, gold bonuses)
 * - A trader (optional)
 */

import java.util.ArrayList;
import java.util.List;

import wss.item.Item;
import wss.trader.Trader;

public class MapSquare {
    // === Attributes ===

    private int x;
    private int y;
    /** The type of terrain in this square (e.g., Plains, Mountain, etc.) */
    private Terrain terrain;

    /** The list of items present in this square (can be empty). */
    private List<Item> items;

    /** A trader in this square (only one trader allowed at a time, can be null). */
    private Trader trader;


    // === Constructor ===

    /**
     * Creates a MapSquare with a given terrain.
     * Initially, it has no items and no trader.
     *
     * @param terrain The terrain type for this square.
     */
    public MapSquare(Terrain terrain, int x, int y) {
        this.terrain = terrain;
        this.items = new ArrayList<>(); // We use an ArrayList to store items because it's fast and easy to manage.
        this.trader = null; // No trader initially
        this.x = x;
        this.y = y;
        System.out.println("[MapSquare] New square created at (" + x + "," + y + ") with terrain: " + terrain.getTerrainType());
    }


    // === Methods ===

    /**
     * Checks if the player can enter this square.
     * We delegate the logic to the terrain object.
     * @return true if enterable, false otherwise
     */
    public boolean isEnterable() {
        // Delegation to terrain's rule about enterability.
        return terrain.isEnterable();
    }

    /**
     * Adds an item to this square.
     * @param item The item to add
     */
    public void addItem(Item item) {
        items.add(item);
        System.out.println("[MapSquare] Item added: " + item.getName());
    }

    /**
     * Removes an item from this square.
     * @param item The item to remove
     */
    public void removeItem(Item item) {
        if (items.remove(item)) {
            System.out.println("[MapSquare] Item removed: " + item.getName());
        } else {
            System.out.println("[MapSquare] Tried to remove item not found: " + item.getName());
        }
    }

    /**
     * Checks if there is a trader in this square.
     * @return true if trader exists, false otherwise
     */
    public boolean hasTrader() {
        return trader != null;
    }
    
    /**
     * Sets a trader into this square.
     * @param trader The trader to set
     */
    public void setTrader(Trader trader) {
        this.trader = trader;
        System.out.println("[MapSquare] Trader set: " + trader.getName());
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    /**
     * Gets the trader from this square.
     * @return The trader object, or null if none
     */
    public Trader getTrader() {
        return trader;
    }

    /**
     * Removes the trader from this square.
     */
    public void removeTrader() {
        if (trader != null) {
            System.out.println("[MapSquare] Trader removed: " + trader.getName());
        }
        trader = null;
    }

    // === Additional Getters ===

    public Terrain getTerrain() {
        return terrain;
    }

    public List<Item> getItems() {
        return items;
    }

}
