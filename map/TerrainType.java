package wss.map;

import wss.game.DifficultySettings;
import java.util.Random;

/**
 * Enum representing the possible types of terrain and their associated movement, water, and food costs.
 */

public enum TerrainType {
   
    // === Enum Constants with Costs ===

    PLAINS(1, 1, 1),
    FOREST(2, 2, 2),
    MOUNTAIN(3, 3, 3),
    DESERT(2, 4, 2),
    SWAMP(4, 3, 3);

    private final int movementCost;
    private final int waterCost;
    private final int foodCost;

    /**
     * Constructs a TerrainType with specified costs.
     *
     * @param movementCost Movement cost (strength units)
     * @param waterCost Water cost
     * @param foodCost Food cost
     */
    TerrainType(int movementCost, int waterCost, int foodCost) {
        this.movementCost = movementCost;
        this.waterCost = waterCost;
        this.foodCost = foodCost;
    }

    /**
     * Returns the movement cost of this terrain type.
     * @return movement cost
     */
    public int getMovementCost() {
        return movementCost;
    }

    /**
     * Returns the water cost of this terrain type.
     * @return water cost
     */
    public int getWaterCost() {
        return waterCost;
    }

    /**
     * Returns the food cost of this terrain type.
     * @return food cost
     */
    public int getFoodCost() {
        return foodCost;
    }

    /**
     * Randomly selects a TerrainType from the available types.
     * Currently, all terrains have equal chance.
     * Later, this method can be modified to adjust probabilities based on difficulty.
     *
     * @param difficulty Difficulty setting (currently unused)
     * @param random Random object to control randomness
     * @return A randomly selected TerrainType
     */
    public static TerrainType chooseRandomTerrain(DifficultySettings difficulty, Random random) {
        TerrainType[] values = TerrainType.values();
        return values[random.nextInt(values.length)];
    }

}
