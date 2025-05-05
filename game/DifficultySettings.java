package wss.game;

/**
 * Enum representing the difficulty settings in the Wilderness Survival System (WSS).
 * Each level defines how resourceful or harsh the game environment will be.
 *
 * Fields:
 * - plainsRatio: Ratio of map covered with "plains" terrain (easier to cross)
 * - itemSpawnRate: Probability of items appearing on the map
 * - traderFrequency: Probability of traders appearing
 * - initialResources: Starting values for [strength, food, water]
 *
 * This enum supports tuning the entire game experience.
 */

public enum DifficultySettings {
 
    EASY(
        0.6, 0.4, 0.4, new int[]{25, 20, 20},
        0.5, 0.4, 0.3, 0.25
    ),
    MEDIUM(
        0.4, 0.3, 0.3, new int[]{20, 15, 15},
        0.3, 0.2, 0.2, 0.15
    ),
    HARD(
        0.2, 0.2, 0.1, new int[]{15, 10, 10},
        0.15, 0.1, 0.1, 0.05
    );

    // === Difficulty Attributes ===

    private final double plainsRatio;        // % of map that should be plains
    private final double itemSpawnRate;      // % of tiles that should have bonus items
    private final double traderFrequency;    // % of tiles that should have traders
    private final int[] initialResources;    // Format: [strength, food, water]

    private final double foodBonusChance;
    private final double waterBonusChance;
    private final double goldBonusChance;
    private final double traderChance;

    /**
     * Constructor for difficulty level constants.
     *
     * @param plainsRatio      Ratio of terrain that is plains
     * @param itemSpawnRate    Probability of bonus items appearing
     * @param traderFrequency  Probability of traders appearing
     * @param initialResources Initial resources: strength, food, water
     */
    DifficultySettings(double plainsRatio, double itemSpawnRate, double traderFrequency, int[] initialResources, double foodBonusChance, double waterBonusChance, double goldBonusChance, double traderChance) {
        this.plainsRatio = plainsRatio;
        this.itemSpawnRate = itemSpawnRate;
        this.traderFrequency = traderFrequency;
        this.initialResources = initialResources;
        this.foodBonusChance = foodBonusChance;
        this.waterBonusChance = waterBonusChance;
        this.goldBonusChance = goldBonusChance;
        this.traderChance = traderChance;
    }


    // === Accessor Methods ===

    public double getPlainsRatio() {
        return plainsRatio;
    }

    public double getItemSpawnRate() {
        return itemSpawnRate;
    }

    public double getTraderFrequency() {
        return traderFrequency;
    }

    /**
     * @return base initial resources (strength, food, water)
     */
    public int[] getInitialPlayerResources() {
        return initialResources;
    }


    /**
     * Scales the player's initial resources based on map size.
     * Prevents under-provisioning on larger maps.
     *
     * @param width  Width of the map
     * @param height Height of the map
     * @return scaled initial resources: [strength, food, water]
     */
    public int[] getScaledInitialResources(int width, int height) {
        int estSteps = width + (height / 2);     // estimate path length across map
        int buffer = (int)(estSteps * 0.2);       // give 20% more as buffer

        return new int[] {
            Math.max(initialResources[0], estSteps + buffer),
            Math.max(initialResources[1], estSteps + buffer),
            Math.max(initialResources[2], estSteps + buffer)
        };
    }


    public double getFoodBonusChance() {
        return foodBonusChance;
    }
    
    public double getWaterBonusChance() {
        return waterBonusChance;
    }
    
    public double getGoldBonusChance() {
        return goldBonusChance;
    }
    
    public double getTraderChance() {
        return traderChance;
    }
    

    /**
     * Prints the difficulty settings and starting values to the terminal.
     */
    public void printSettings() {
        System.out.println("=== Difficulty: " + this.name() + " ===");
        System.out.printf("Plains Ratio:      %.2f\n", plainsRatio);
        System.out.printf("Item Spawn Rate:   %.2f\n", itemSpawnRate);
        System.out.printf("Trader Frequency:  %.2f\n", traderFrequency);
        System.out.printf("Initial Resources: Strength=%d, Food=%d, Water=%d\n",
                initialResources[0], initialResources[1], initialResources[2]);
        System.out.println();
    }

}
