package wss.game;


import java.util.List;
import wss.map.GameMap;
import java.util.Map;

/**
 * Represents a summary report of a player's gameplay session.
 * Tracks movement, resource usage, strategy, and trader encounters.
 */

 public class GameReport {

    // === Core Game Data ===
    private String playerName;
    private int turnsTaken;
    private boolean reachedGoal;
    private int foodRemaining;
    private int waterRemaining;
    private int goldCollected;
    private int finalStrength;

    // === Strategy Info ===
    private String brainType;
    private String visionType;

    // === Trader Interactions ===
    private int tradersMet;
    private List<String> traderTypesEncountered;

    // === Movement Tracking ===
    private List<String> terrainsPassed;
    private Map<String, Integer> terrainStrengthLoss;

    /**
     * Constructor for the complete GameReport.
     *
     * @param playerName              Player's name
     * @param turnsTaken              Total number of turns taken
     * @param reachedGoal             Whether player reached the goal
     * @param foodRemaining           Food remaining at the end
     * @param waterRemaining          Water remaining at the end
     * @param goldCollected           Gold collected
     * @param brainType               Name of brain strategy used
     * @param visionType              Vision type used
     * @param tradersMet              Number of traders met
     * @param traderTypesEncountered  List of trader types encountered
     * @param terrainsPassed          List of terrain types player moved through
     * @param terrainStrengthLoss     Strength lost per terrain type
     * @param finalStrength           Strength remaining at the end
     */
    public GameReport(String playerName, int turnsTaken, boolean reachedGoal,
                      int foodRemaining, int waterRemaining, int goldCollected,
                      String brainType, String visionType,
                      int tradersMet, List<String> traderTypesEncountered,
                      List<String> terrainsPassed, List<Integer> strengthLossPerStep,
                      int finalStrength) {

        this.playerName = playerName;
        this.turnsTaken = turnsTaken;
        this.reachedGoal = reachedGoal;
        this.foodRemaining = foodRemaining;
        this.waterRemaining = waterRemaining;
        this.goldCollected = goldCollected;
        this.brainType = brainType;
        this.visionType = visionType;
        this.tradersMet = tradersMet;
        this.traderTypesEncountered = traderTypesEncountered;
        this.terrainsPassed = terrainsPassed;
        this.terrainStrengthLoss = terrainStrengthLoss;
        this.finalStrength = finalStrength;
    }

    /**
     * Prints a detailed summary of the game report.
     */
    public void printReport() {
        System.out.println("===== Game Report =====");
        System.out.println("Player:          " + playerName);
        System.out.println("Turns Taken:     " + turnsTaken);
        System.out.println("Reached Goal:    " + (reachedGoal ? "Yes" : "No"));
        System.out.println("Brain Strategy:  " + brainType);
        System.out.println("Vision Type:     " + visionType);
        System.out.println("Food Left:       " + foodRemaining);
        System.out.println("Water Left:      " + waterRemaining);
        System.out.println("Gold Collected:  " + goldCollected);
        System.out.println("Final Strength:  " + finalStrength);
        System.out.println("Traders Met:     " + tradersMet);
        System.out.println("Trader Types:    " + String.join(", ", traderTypesEncountered));
        System.out.println("\nTerrains Passed: " + String.join(" → ", terrainsPassed));
        System.out.println("Strength Lost by Terrain:");
        for (String terrain : terrainStrengthLoss.keySet()) {
            System.out.printf("  %-10s: -%d strength\n", terrain, terrainStrengthLoss.get(terrain));
        }

        System.out.println("========================\n");
    }

}
