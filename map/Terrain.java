package wss.map;

/**
 * Represents a type of terrain on the map.
 * Each terrain has a type (e.g., Plains, Forest, Mountain) and associated movement, water, and food costs.
 */ 

public class Terrain {
    
    /** The type of this terrain, defining its general category */
    private TerrainType terrainType;

    /**
     * Constructs a Terrain with a given type.
     * Costs are determined by the TerrainType.
     *
     * @param terrainType The specific type of terrain.
     */
    public Terrain(TerrainType terrainType) {
        this.terrainType = terrainType;
        System.out.println("[Terrain] Created terrain of type: " + terrainType);
    }

    /**
     * Gets the movement cost associated with this terrain.
     * @return movement cost in units of strength
     */
    public int getMovementCost() {
        return terrainType.getMovementCost();
    }

    /**
     * Gets the water cost associated with this terrain.
     * @return water cost
     */
    public int getWaterCost() {
        return terrainType.getWaterCost();
    }

    /**
     * Gets the food cost associated with this terrain.
     * @return food cost
     */
    public int getFoodCost() {
        return terrainType.getFoodCost();
    }

    /**
     * Gets the type of this terrain.
     * @return terrain type
     */
    public TerrainType getTerrainType() {
        return terrainType;
    }

    /**
     * Checks if this terrain is enterable.
     * For now, all terrains are considered enterable.
     * Additional logic could be added here if needed.
     *
     * @return true (currently always enterable)
     */
    public boolean isEnterable() {
        return true;
    }
    
}
