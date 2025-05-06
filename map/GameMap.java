package wss.map;

/**
 * Represents the entire game map, consisting of MapSquares arranged in a 2D grid.
 * Handles generating terrain, bonuses, and traders based on difficulty settings.
 */


import java.util.Random;

import wss.trader.Range;

import wss.game.DifficultySettings;
import wss.item.FoodBonus;
import wss.item.GoldBonus;
import wss.item.Item;
import wss.item.WaterBonus;
import wss.trader.GreedyTrader;
import wss.trader.ImpatientTrader;
import wss.trader.RegularTrader;
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
                    int type = random.nextInt(3);
                    Range food = difficultySetting.getFoodTradeRange();
                    Range water = difficultySetting.getWaterTradeRange();
                    Range gold = difficultySetting.getGoldTradeRange();
                    int trades = 3;

                    Trader trader = switch (type) {
                        case 0 -> new RegularTrader(trades, food, water, gold);
                        case 1 -> new ImpatientTrader(trades, food, water, gold);
                        case 2 -> new GreedyTrader(trades, food, water, gold);
                        default -> new RegularTrader(trades, food, water, gold);
                    };

                    square.setTrader(trader);
                }

                grid[x][y] = square;
            }
        }

        System.out.println("[GameMap] Map generation complete.");
    }

    public void printMap() {
        System.out.println("[DEBUG] Entering GameMap.printMap()");
        System.out.flush();
        System.out.println("\n\n");
        System.out.flush();
        System.out.println("==========================================");
        System.out.flush();
        System.out.println("                MAP VIEW                  ");
        System.out.flush();
        System.out.println("==========================================");
        System.out.flush();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                MapSquare square = grid[x][y];

                // Terrain code (2-letter abbreviation)
                String terrainCode = switch (square.getTerrain().getTerrainType()) {
                    case PLAINS -> "PL";
                    case MOUNTAIN -> "MO";
                    case FOREST -> "FO";
                    case DESERT -> "DE";
                    case SWAMP -> "SW";
                };

                // Bonus codes (max 1 shown, or more if you want)
                String bonusCode = "-";
                for (Item item : square.getItems()) {
                    if (item.getName().contains("Food")) { bonusCode = "F"; break; }
                    if (item.getName().contains("Water")) { bonusCode = "W"; break; }
                    if (item.getName().contains("Gold")) { bonusCode = "G"; break; }
                }

                // Trader code (2-letter)
                String traderCode = "--";
                if (square.hasTrader()) {
                    String name = square.getTrader().getClass().getSimpleName();
                    if (name.contains("Greedy")) traderCode = "TG";
                    else if (name.contains("Impatient")) traderCode = "TI";
                    else if (name.contains("Regular")) traderCode = "TR";
                }

                // Print formatted square
                System.out.print(String.format("%s-%s-%s | ", terrainCode, bonusCode, traderCode));
                System.out.flush();
            }
            System.out.println(); // Newline per row
            System.out.flush();
        }
        System.out.println("==========================================");
        System.out.flush();
        System.out.println("Legend:");
        System.out.flush();
        System.out.println("Terrain: PL=Plains, MO=Mountain, FO=Forest, DE=Desert, SW=Swamp");
        System.out.flush();
        System.out.println("Bonus: F=Food, W=Water, G=Gold");
        System.out.flush();
        System.out.println("Trader: TG=Greedy, TI=Impatient, TR=Regular");
        System.out.flush();
        System.out.println("==========================================\n\n");
        System.out.flush();
        System.out.println("[DEBUG] Exiting GameMap.printMap()");
        System.out.flush();
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
