package wss.vision;

import wss.map.GameMap;
import wss.player.Player;
import wss.map.MapSquare;

import java.util.ArrayList;
import java.util.List;

import wss.item.Item;
import wss.util.Path;

// CautiousVision scans only the adjacent squares (North, East, South). 
// Prioritizes moving to squares that are close and safe. 
// Avoids expensive or risky paths.
public class CautiousVision extends Vision {

    private List<MapSquare> visibleSquares;
    private List<MapSquare> squaresThatHaveGold;
    private List<MapSquare> squaresThatHaveWater;
    private List<MapSquare> squaresThatHaveFood;
    private int[] currentPosition;

    public CautiousVision() {
        visibleSquares = new ArrayList<>();
        squaresThatHaveFood = new ArrayList<>();
        squaresThatHaveGold = new ArrayList<>();
        squaresThatHaveFood = new ArrayList<>();
        currentPosition = player.getPosition();     // getPosition is not an available function yet
    }

    /**
     * Checks if a map square has food
     * 
     * @param items The list of items in a map square
     */
    private boolean hasFood(List<Item> items) {
        for (int index = 0; index  < items.size(); index++) {
            Item item = items.get(index);
            if (item.getName().equals("Food bonus")) {
                return true;
            }
        }
        return false;
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
        for (int index = 0; index < visibleSquares.size(); index++) {
            MapSquare currentSquare = visibleSquares.get(index);
            List<Item> items = currentSquare.getItems();
            if (hasFood(items, currentSquare)) {
                squaresThatHaveFood.add(currentSquare);
            }
        }

        /* to do: need to create algorithm to determine which square to choose
         * if there are multiple paths with the same distance
        */
        

    }

    /**
     * Retrieves all visible squares
     * 
     * @param map The map of the entire game
     * @param player The player in the game
     * @return List of map squares visible the the player
     */
    public List<MapSquare> getVisibleSquares(GameMap map, Player player) {
        visibleSquares.add(map.getSquare(currentPosition[0], currentPosition[1] + 1));
        visibleSquares.add(map.getSquare(currentPosition[0] + 1, currentPosition[1]));
        visibleSquares.add(map.getSquare(currentPosition[0], currentPosition[1] - 1));

        return visibleSquares;
    }
}