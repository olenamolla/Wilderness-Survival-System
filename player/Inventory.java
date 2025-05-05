package wss.player;


/**
 * Represents a player's inventory in the Wilderness Survival System.
 * Manages food, water, and gold resources, including max capacities.
 */
public class Inventory {
    // === Resource Fields ===
    private int food;
    private int water;
    private int gold;

    // === Max Capacity Fields ===
    private int maxFood;
    private int maxWater;
    private int maxGold;


    public Inventory(int maxFood, int maxWater, int maxGold) {
        this.maxFood = maxFood;
        this.maxWater = maxWater;
        this.maxGold = maxGold;
    
        this.food = maxFood;   // start fully stocked
        this.water = maxWater;
        this.gold = maxGold;;         
    }
    /**
     * Adds food to the inventory. Does not exceed maxFood limit.
     * @param amount Amount of food to add
     */
    public void addFood(int amount) {
        food = Math.min(food + amount, maxFood);
    }

    /**
     * Adds water to the inventory. Does not exceed maxWater limit.
     * @param amount Amount of water to add
     */
    public void addWater(int amount) {
        water = Math.min(water + amount, maxWater);
    }

    /**
     * Adds gold to the inventory. Does not exceed maxGold limit.
     * @param amount Amount of gold to add
     */
    public void addGold(int amount) {
        gold = Math.min(gold + amount, maxGold);
    }


    /**
     * Attempts to spend food from the inventory.
     * @param amount Amount of food to spend
     * @return true if successful, false if not enough food
     */
    public boolean spendFood(int amount) {
        if (food >= amount) {
            food -= amount;
            return true;
        }
        return false;
    }

    /**
     * Attempts to spend water from the inventory.
     * @param amount Amount of water to spend
     * @return true if successful, false if not enough water
     */
    public boolean spendWater(int amount) {
        if (water >= amount) {
            water -= amount;
            return true;
        }
        return false;
    }

    /**
     * Attempts to spend gold from the inventory.
     * @param amount Amount of gold to spend
     * @return true if successful, false if not enough gold
     */
    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }

    // === Getters ===

    /**
     * Returns current food level.
     * @return current food
     */
    public int getFood() {
        return food;
    }

    /**
     * Returns current water level.
     * @return current water
     */
    public int getWater() {
        return water;
    }

    /**
     * Returns current gold level.
     * @return current gold
     */
    public int getGold() {
        return gold;
    }


    // === Setters for Max Values ===

    /**
     * Sets the max food capacity.
     * @param value maximum food
     */
    public void setMaxFood(int value) {
        maxFood = value;
    }

    /**
     * Sets the max water capacity.
     * @param value maximum water
     */
    public void setMaxWater(int value) {
        maxWater = value;
    }

    /**
     * Sets the max gold capacity.
     * @param value maximum gold
     */
    public void setMaxGold(int value) {
        maxGold = value;
    }

    // === Getters for Max Values ===
    public int getMaxFood() { return maxFood; }
    public int getMaxWater() { return maxWater; }
    public int getMaxGold() { return maxGold; }

    
    // For dubugging purposes
    @Override
    public String toString() {
        return String.format("Food: %d/%d, Water: %d/%d, Gold: %d/%d",
            food, maxFood, water, maxWater, gold, maxGold);
    }

}
