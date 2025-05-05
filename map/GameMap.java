package wss.map;

/**
 * Represents the entire game map, consisting of MapSquares arranged in a 2D grid.
 * Handles generating terrain, bonuses, and traders based on difficulty settings.
 */


import java.util.Random;

import wss.game.DifficultySettings;
import wss.item.FoodBonus;
import wss.item.GoldBonus;
import wss.item.WaterBonus;
import wss.trader.Trader;

public class GameMap {

    /** Width of the map (number of squares horizontally) */
    private int width;

    /** Height of the map (number of squares vertically) */
    private int height;

    /** 2D array of MapSquares forming the map grid */
    private MapSquare[][] grid;

    /** Difficulty setting that influences map generation */
    private DifficultySettings difficultySetting;

    /** Random generator for randomized placement */
    private Random random;

    /**
     * Constructs a GameMap with the given dimensions and difficulty.
     * @param width Width of the map
     * @param height Height of the map
     * @param difficultySetting Difficulty setting for terrain and item generation
     */
    public GameMap(int width, int height, DifficultySettings difficultySetting) {
        this.width = width;
        this.height = height;
        this.difficultySetting = difficultySetting;
        this.grid = new MapSquare[width][height];
        this.random = new Random();

        generateMap();
    }

    /**
     * Generates the map with terrain, bonuses, and traders based on difficulty.
     */
    private void generateMap() {
        System.out.println("[GameMap] Starting map generation with difficulty: " + difficultySetting);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                // Select terrain based on difficulty
                Terrain terrain = new Terrain(TerrainType.chooseRandomTerrain(difficultySetting, random));
                MapSquare square = new MapSquare(terrain, x, y);
                // Randomly populate square with items and trader
                if (random.nextDouble() < difficultySetting.getFoodBonusChance()) {
                    square.addItem(new FoodBonus());
                }
                if (random.nextDouble() < difficultySetting.getWaterBonusChance()) {
                    square.addItem(new WaterBonus());
                }
                if (random.nextDouble() < difficultySetting.getGoldBonusChance()) {
                    square.addItem(new GoldBonus());
                }
                if (random.nextDouble() < difficultySetting.getTraderChance()) {
                    square.setTrader(new Trader());
                }

                grid[x][y] = square;
                System.out.println("Square (" + x + "," + y + ") has terrain: " + terrain.getTerrainType());

            }
        }

        System.out.println("[GameMap] Map generation complete.");
    }

     /**
     * Retrieves the MapSquare at a specific coordinate.
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return MapSquare at (x,y) or null if out of bounds
     */
    public MapSquare getSquare(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[x][y];
        } else {
            System.out.println("[GameMap] Attempted to access invalid coordinates: (" + x + ", " + y + ")");
            return null;
        }
    }

    /**
     * Returns the width of the map.
     * @return Width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the map.
     * @return Height of the map
     */
    public int getHeight() {
        return height;
    }


}
