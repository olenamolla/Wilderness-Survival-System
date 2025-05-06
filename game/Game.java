package wss.game;

import wss.map.GameMap;
import wss.player.Player;
import wss.vision.*;
import wss.brain.*;


import java.util.*;

/**
 * Game class manages the overall flow of the Wilderness Survival System.
 * It handles user input to configure the game, initializes all components,
 * and runs the simulation loop until all players finish or fail.
 */

 public class Game {
    
    // === Fields ===
    private List<Player> players;
    private GameMap gameMap;
    private List<GameReport> reports;
    private DifficultySettings difficulty;
    private Scanner input;

    /**
     * Constructor initializes player list and Scanner.
     */
    public Game() {
        players = new ArrayList<>();
        reports = new ArrayList<>();
        input = new Scanner(System.in);
    }

    /**
     * Setup method prompts user for configuration and creates all game objects.
     */

    public void setup() {
        System.out.println("Welcome to Wilderness Survival System!");

        // === Map Size ===
        System.out.println("Enter map width: ");
        int width = input.nextInt();
        System.out.print("Enter map height: ");
        int height = input.nextInt();

        // === Difficulty ===
        System.out.println("Select difficulty: [1] Easy, [2] Medium, [3] Hard");
        int diffChoice = input.nextInt();

        switch(diffChoice){
            case 1 : 
                difficulty = DifficultySettings.EASY; 
                break;
            case 2 : 
                difficulty = DifficultySettings.MEDIUM; 
                break;
            case 3 :
                difficulty = DifficultySettings.HARD; 
                break;
            default :
                System.out.println("Invalid choice, defaulting to Easy.");
                difficulty = DifficultySettings.EASY;
        }

        // === Map Creation ===
        System.out.println("[DEBUG] Creating new GameMap...");
        gameMap = new GameMap(width, height, difficulty);
        System.out.println("[DEBUG] About to print map from setup()...");
        gameMap.printMap(); // Print the map after creation
        System.out.println("[DEBUG] Map printed from setup()");

        // === Players ===
        System.out.println("How many players? ");
        int numPlayers = input.nextInt();
        input.nextLine(); // Consume newline

        for(int i =0; i < numPlayers; i++){
            System.out.println("Setting up Player #" + (i+1));
            System.out.println("Enter player name: ");
            String name = input.nextLine(); 

            // === Brain Selection ===
            System.out.println("Select Brain: [1] Greedy [2] Social [3] Survival");
            int brainChoice = input.nextInt();

            Brain brain;

            switch(brainChoice) {
                case 1 : 
                    brain = new GreedyBrain();
                    break;
                case 2 : 
                    brain = new SocialBrain();
                    break;
                case 3 : 
                    brain = new SurvivalBrain();
                    break;
                default :
                    System.out.println("Invalid choice, defaulting to Greedy.");
                    brain = new GreedyBrain();
                
            }

            // === Vision Selection ===
            System.out.println("Select Vision: [1] Cautious [2] Greedy [3] Extended");
            int visionChoice = input.nextInt();
            Vision vision;
            switch (visionChoice) {
                case 1 :
                    vision = new CautiousVision();
                    break;
                case 2 :
                    vision = new GreedyVision();
                    break;
                case 3 : 
                    vision = new ExtendedVision();
                    break;
                default:
                    System.out.println("Invalid choice. Defaulting to Cautious.");
                    vision = new CautiousVision();
                    break;
                
            };

            input.nextLine(); // consume newline


            int[] res = difficulty.getInitialPlayerResources();
            int strength = res[0];
            int food = res[1];
            int water = res[2];
            int gold = 100; 


            Player player = new Player(name, vision, brain, gameMap, strength, food, water, gold);
            players.add(player);


        }

    }

    public void printMap() {
        gameMap.printMap(); // Delegates the task to the GameMap
    }
    

    /**
     * Main simulation loop.
     */
    public void run() {
        System.out.println("\nStarting the simulation...");
        printMap(); // Print initial map state

        boolean gameRunning = true;
        while (gameRunning) {
            gameRunning = false;

            for (Player player : players) {
                if (!player.hasFinished()) {
                    player.takeTurn();
                    gameRunning = true; // at least one player still active
                }
            }
        }

        stopSimulation();
    }

    /**
     * Checks if all players are done.
     */
    public boolean isGameOver() {
        for (Player player : players) {
            if (!player.hasFinished()) {
                return false;
            }
        }
        return true;
    }

    /**
    * Returns a list of all players who reached the goal.
    *
    * @return List of winners
    */
    public List<Player> getWinners() {
        List<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (player.reachedGoal()) {
                winners.add(player);
            }
        }
        return winners;
    }   

    /**
     * Finalizes the game and prints all reports.
     */
    public void stopSimulation() {
        System.out.println("\nSimulation complete!\n");
    
        for (Player player : players) {
            GameReport report = player.generateReport();  // player must implement this
            report.printReport();
            reports.add(report);
        }
    
        List<Player> winners = getWinners();
        if (winners.isEmpty()) {
            System.out.println("No player reached the goal.");
        } else {
            System.out.println("Winner(s):");
            for (Player winner : winners) {
                System.out.println(" - " + winner.getName());
            }
        }
    }

 }