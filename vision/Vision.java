package wss.vision;

import java.util.ArrayList;
import java.util.List;

import wss.map.GameMap;
import wss.player.MoveDirection;
import wss.player.Player;
import wss.map.MapSquare;

import wss.util.Path;


public abstract class Vision {

    protected List<MapSquare> visibleSquares;
    protected List<MapSquare> easiestSquares;
    protected List<MoveDirection> easiestSquaresDirections;

    public Vision() {
        this.visibleSquares = new ArrayList<>();
        this.easiestSquares = new ArrayList<>();
    }

    /**
     * Returns the path to the closest food. If there are more than one
     * path that is the same distance, this method chooses the path that
     * leads to more food. If there are multiple paths that are the same
     * distance and have the same amount of food, choose path randomly.
     * 
     * @return Path object containing the best path to food
     */
    public abstract Path closestFood();

    /**
     * Returns the path to the closest water. If there are more than one
     * path that is the same distance, this method chooses the path that
     * leads to more water. If there are multiple paths that are the same
     * distance and have the same amount of water, choose path randomly.
     * 
     * @return Path object containing the best path to water
     */
    public abstract Path closestWater();

    /**
     * Returns the path to the closest gold. If there are more than one
     * path that is the same distance, this method chooses the path that
     * leads to more gold. If there are multiple paths that are the same
     * distance and have the same amount of gold, choose path randomly.
     * 
     * @return Path object containing the best path to gold
     */
    public abstract Path closestGold();


    /**
     * Returns a path in the direction of east that costs the 
     * least overall. The path will only be one square long
     * 
     * @return Path object containing the easiest path
     */
    public Path easiestPath() {
        int bestTotalCost = Integer.MAX_VALUE;
    
    
        Path optimalPath = null;

        setEasiestSquares(); // sets easiestSquares and easiestSquaresDirections

        for (int index = 0; index < easiestSquares.size(); index++) {
            // each entry is a single MapSquare and MoveDirection
            MapSquare square = easiestSquares.get(index);  // Single square
            MoveDirection direction = easiestSquaresDirections.get(index);  // Single direction
    
            Path currentPath = new Path();
    
            // all three: direction, terrain, and square
            currentPath.addStep(direction, square.getTerrain(), square);
    
            int currentTotalCost = currentPath.getMovementCost()
                              + currentPath.getFoodCost()
                              + currentPath.getWaterCost();
    
            if (currentTotalCost < bestTotalCost) {
            bestTotalCost = currentTotalCost;
            optimalPath = currentPath;
            }
        }
  

        return optimalPath;
    }

    /**
     * Stores the squares that are only one square away from the player
     * for later evaluation to determine which square has the least cost
     */
    protected abstract void setEasiestSquares();

    
    // === Utility methods to check for items on a square ===
    protected boolean hasFood(MapSquare square) {
        return square.hasItemOfType("Food Bonus");
    }

    protected boolean hasWater(MapSquare square) {
        return square.hasItemOfType("Water Bonus");
    }

    protected boolean hasGold(MapSquare square) {
        return square.hasItemOfType("Gold Bonus");
    }

    
    /**
     * Retrieves all visible squares
     * 
     * @param map The map of the entire game
     * @param player The player in the game
     * @return List of map squares visible the the player
     */
    public abstract List<MapSquare> getVisibleSquares(GameMap map, Player player);
}
