package wss.vision;

import java.util.ArrayList;
import wss.map.GameMap;
import wss.player.Player;
import wss.map.MapSquare;
import wss.item.Item;

// CautiousVision scans only the adjacent squares (North, East, South). 
// Prioritizes moving to squares that are close and safe. 
// Avoids expensive or risky paths.
public class CautiousVision extends Vision {

    private List<MapSquare> visibleSquares;
    private List<MapSquare> squaresThatHaveGold;
    private List<MapSquare> squaresThatHaveWater;
    private List<MapSquare> squaresThatHaveFood;
    private int[][] mapSquarePositions;
    private int[] currentPosition;

    public CautiousVision() {
        visibleSquares = new ArrayList<>();
        squaresThatHaveFood = new ArrayList<>();
        squaresThatHaveGold = new ArrayList<>();
        squaresThatHaveFood = new ArrayList<>();
        mapSquarePositions = new int[3][2];
        currentPosition = player.getPosition();     // getPosition is not an available function yet
    }

    public boolean hasFood(List<Item> items) {
        for (int index = 0; index  < items.size(); index++) {
            Item item = items.get(index);
            if (item.getName().i)
        }
    }

    public PlayerPath closestFood() {
        for (int index = 0; index < visibleSquares.size(); index++) {
            List<Item> items = visibleSquares.get(index).getItems();

        }
    }

    public List<MapSquare> getVisibleSquares(GameMap map, Player player) {

        // storing north square and its position
        visibleSquares.add(map.getSquare(currentPosition[0], currentPosition[1] + 1));
        mapSquarePositions[0][0] = currentPosition[0];
        mapSquarePositions[0][1] = currentPosition[1] + 1;

        // storing east square and its position
        visibleSquares.add(map.getSquare(currentPosition[0] + 1, currentPosition[1]));
        mapSquarePositions[1][0] = currentPosition[0] + 1;
        mapSquarePositions[1][1] = currentPosition[1];

        // storing south square and its position
        visibleSquares.add(map.getSquare(currentPosition[0], currentPosition[1] - 1));
        mapSquarePositions[2][0] = currentPosition[0];
        mapSquarePositions[2][1] = currentPosition[1] - 1;

        return visibleSquares;
    }
}