package wss.player;

import wss.brain.Brain;
import wss.game.GameReport;
import wss.item.Item;
import wss.map.GameMap;
import wss.map.MapSquare;
import wss.map.Terrain;
import wss.trader.Trader;
import wss.vision.Vision;
import wss.player.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents a player in the Wilderness Survival System game.
 * Each player has a position, strength, brain (decision making), vision (map scanning),
 * and an inventory of resources (food, water, gold).
 */

public class Player {
    // === Basic Attributes ===
    private String name;    // Player name
    private int strength;   // Current strength (health)
    private int maxStrength; // Maximum strength
    
    private Vision vision;  // Vision logic to scan the map
    private Brain brain;    // Decision-making logic
    private Inventory inventory;    // Resource tracker

    private GameMap map; // Reference to the game map

    //position on map
    private int x;   // X-coordinate on map
    private int y;   // Y-coordinate on map

    private boolean reachedGoal = false; // Flag to check if the player has reached the goal
    private boolean finished = false; // Flag to check if the player has finished the game

    private int turnsTaken = 0;
    private int tradersMet = 0;
    private List<String> traderTypesEncountered = new ArrayList<>();
    private List<String> terrainsPassed = new ArrayList<>();
    private Map<String, Integer> terrainStrengthLoss = new HashMap<>();

     /**
     * Constructs a player with given starting conditions.
     * 
     * @param name          The player's name
     * @param vision        The player's vision strategy
     * @param brain         The player's AI decision strategy
     * @param map           The game map reference
     * @param startStrength Initial strength value
     * @param startFood     Initial food value
     * @param startWater    Initial water value
     */
    public Player(String name, Vision vision, Brain brain, GameMap map, int startStrength, int maxfood, int maxwater, int maxgold) {
        this.name = name;
        this.vision = vision;
        this.brain = brain;
        this.map = map;
        this.maxStrength = startStrength;
        this.strength = startStrength;
        this.inventory = new Inventory(maxfood, maxwater, maxgold);
        this.x = 0; // Starting position: West edge
        this.y = map.getHeight() / 2; // Center vertically
    }

    /**
     * Executes one simulation turn for the player.
     * If the player is still active, their brain chooses a direction and moves them.
     * Ends the game if strength is depleted or the player reaches the goal.
     */
    public void takeTurn() {
        if (hasFinished()) return;

        if (!hasAnyLegalMove()) {
            finished = true;
            System.out.println("[Player] " + name + " has no legal moves left and perished!");
            return;
        }

        turnsTaken++; // Increment the turn count
        
        MoveDirection direction = brain.makeMove(map, this);
        move(direction);

        if (strength <= 0) {
            finished = true;
            System.out.println("[Player] " + name + " ran out of strength and collapsed!");

        } else if (x == map.getWidth() - 1) {
            finished = true;
            reachedGoal = true;
            System.out.println("[Player] " + name + " reached the easter edge!");
        }
    }

    /**
     * Moves the player in the specified direction and applies terrain/item effects.
     *
     * @param dir The direction to move
     */
    public void move(MoveDirection dir) {
        // Update x and y based on MoveDirection
        int newX = x + dir.getXChange();
        int newY = y + dir.getYChange();

        // Apply terrain costs at the new location
        MapSquare targetSquare = map.getSquare(newX, newY);
        if (targetSquare == null || !targetSquare.isEnterable()) {
            return;
        }

        x = newX;
        y = newY;

        applyTerrainCost(targetSquare);
        handleBonuses(targetSquare);
        handleTrade(targetSquare);
    
    }

    /**
    * Checks if the player can move in any direction, considering terrain and current resources.
    * @return true if there is at least one legal move
    */
    public boolean hasAnyLegalMove() {
        for (MoveDirection dir : MoveDirection.values()) {
            int newX = x + dir.getXChange();
            int newY = y + dir.getYChange();

            MapSquare square = map.getSquare(newX, newY);
            if (square != null && square.isEnterable()) {
                int moveCost = square.getTerrain().getMovementCost();
                int foodCost = square.getTerrain().getFoodCost();
                int waterCost = square.getTerrain().getWaterCost();

                // Check if all costs are affordable
                if (strength >= moveCost &&
                    inventory.getFood() >= foodCost &&
                    inventory.getWater() >= waterCost) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
    * Handles collecting and applying items found in the current square.
    *
    * @param square The current MapSquare the player is on
    */
    private void handleBonuses(MapSquare square) {
        for (Item item : square.getItems()) {
            item.applyTo(this);
        }
        square.getItems().clear();
    }

    /**
    * Handles trade logic if a trader is present in the square.
    * The trade strategy depends on the player's brain or inventory state.
    *
    * @param square The current MapSquare the player is on
    */
    private void handleTrade(MapSquare square) {
        if (square.hasTrader()) {
            Trader trader = square.getTrader();

            tradersMet++;
            traderTypesEncountered.add(trader.getClass().getSimpleName());

            brain.initiateTrade(this, trader);
        }
    }



    /**
     * Applies the terrain penalties at the player's current square,
     * and logs tracking info for terrain report generation.
     * @param square The MapSquare the player just entered
     */
    private void applyTerrainCost(MapSquare square) {

        Terrain terrain = square.getTerrain(); // Get the terrain type of the square

        String terrainName = terrain.getTerrainType().name(); // Get the terrain name

        terrainsPassed.add(terrainName); // Keep record of movement path


        int movementCost = terrain.getMovementCost();

        strength -= movementCost;
        terrainStrengthLoss.put(terrainName,
        terrainStrengthLoss.getOrDefault(terrainName, 0) + movementCost);

        inventory.spendWater(square.getTerrain().getWaterCost());
        inventory.spendFood(square.getTerrain().getFoodCost());
    }



    /**
     * Increases player's food in inventory.
     * @param amount Food to add
     */
    public void increaseFood(int amount) {
        inventory.addFood(amount);
    }

    /**
     * Increases player's water in inventory.
     * @param amount Water to add
     */
    public void increaseWater(int amount) {
        inventory.addWater(amount);
    }

    /**
     * Increases player's gold in inventory.
     * @param amount Gold to add
     */
    public void increaseGold(int amount) {
        inventory.addGold(amount);
    }

    /**
     * Checks whether the player has finished playing.
     * @return True if player is done (either reached goal or died)
     */
    public boolean hasFinished() {
        return finished;
    }

    /**
     * Checks if the player reached the eastern edge of the map.
     * @return True if the player reached the goal
     */
    public boolean reachedGoal() {
        return reachedGoal; 
    }

    // === Getters ===

    public int getX() { return x; }
    public int getY() { return y; }
    public Vision getVision() { return vision; }
    public Inventory getInventory() { return inventory; }
    public String getName() { return name; }
    public int getStrength() { return strength; }




    /**
    * Generates a GameReport containing this player's final game statistics.
    *
    * @return GameReport object summarizing the player's performance.
    */

    public GameReport generateReport() {
        String playerName = this.name; // Get the player's name.
        boolean reachedGoal = this.reachedGoal; // Whether the player reached the goal.

        String brainType = brain.getClass().getSimpleName(); // Get the brain type used
        String visionType = vision.getClass().getSimpleName(); // Get the vision type used



        int finalStrength = this.strength; // Final strength remaining at the end of the game.

        return new GameReport(
            playerName,
            turnsTaken,
            reachedGoal,
            inventory.getFood(),
            inventory.getWater(),
            inventory.getGold(),
            brainType,
            visionType,
            tradersMet,
            traderTypesEncountered,
            terrainsPassed,
            terrainStrengthLoss,
            finalStrength
        );
    }

}
