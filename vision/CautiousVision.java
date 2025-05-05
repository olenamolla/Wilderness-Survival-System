package wss.vision;

import wss.map.GameMap;
import wss.player.Player;
import wss.map.MapSquare;

import java.util.ArrayList;
import java.util.List;

import wss.item.Item;
import wss.util.Path;
import wss.player.MoveDirection;

// CautiousVision scans only the adjacent squares (North, East, South). 
// Prioritizes moving to squares that are close and safe. 
// Avoids expensive or risky paths.
public class CautiousVision extends Vision {

    private List<MapSquare> visibleSquares;
    private List<MoveDirection> directions;
    private List<MapSquare> squaresThatHaveGold;
    private List<MapSquare> squaresThatHaveWater;
    private List<MapSquare> squaresThatHaveFood;

    public CautiousVision() {
        visibleSquares = new ArrayList<>();
        directions = new ArrayList<>();
        squaresThatHaveFood = new ArrayList<>();
        squaresThatHaveGold = new ArrayList<>();
        squaresThatHaveWater = new ArrayList<>();
    }

    private boolean hasItem(MapSquare currentSquare) {
        return true;
    }

    /**
     * Returns the path to the closest food. If there are more than one
     * path that is the same distance, this method chooses the path that
     * leads to more food. If there are multiple paths that are the same
     * distance and have the same amount of food, choose path randomly.
     * 
     * @return Path object containing the best path to food
     */
    public Path closestFood() {
        // contains the square that will take the least accumulated cost (movement + gold + water) to get to
        int bestTotalCost = Integer.MAX_VALUE;

        // contains the current total cost of the iteration
        int currentTotalCost = Integer.MAX_VALUE;

        // the current path of the iteration
        Path currentPath = null;

        // the closest and best path to the square with food
        Path optimalPath = null;

        // iterates over each visible square to look for the optimal path
        for (int index = 0; index < visibleSquares.size(); index++) {
            MapSquare currentSquare = visibleSquares.get(index);
            
            // if the current square has food, then evaluate whether it is the optimal path
            if (hasItem(currentSquare)) {    // replace hasItem() with MapSquare function once it exists
                currentPath = new Path();
                currentPath.addStep(directions.get(index), currentSquare.getTerrain());
                currentTotalCost = currentPath.getMovementCost() + currentPath.getFoodCost() + currentPath.getWaterCost();
                
                // set the new optimal path if a better path is found
                if (currentTotalCost < bestTotalCost) {
                    bestTotalCost = currentTotalCost;
                    optimalPath = currentPath;
                }
            }
        }

        return optimalPath;
    }

    /**
     * Retrieves all visible squares
     * 
     * @param map The map of the entire game
     * @param player The player in the game
     * @return List of map squares visible the the player
     */
    public List<MapSquare> getVisibleSquares(GameMap map, Player player) {
        visibleSquares.clear();
        directions.clear();
        
        int[] currentPosition = player.getPosition();     // getPosition is not an available function yet

        // stores square and direction for north square
        visibleSquares.add(map.getSquare(currentPosition[0], currentPosition[1] + 1));
        directions.add(MoveDirection.NORTH);

        // stores square and direction for east square
        visibleSquares.add(map.getSquare(currentPosition[0] + 1, currentPosition[1]));
        directions.add(MoveDirection.EAST);

        // stores square and direction for south square
        visibleSquares.add(map.getSquare(currentPosition[0], currentPosition[1] - 1));
        directions.add(MoveDirection.SOUTH);

        return visibleSquares;
    }
}
