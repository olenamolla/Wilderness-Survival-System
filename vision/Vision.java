package wss.vision;

import java.util.ArrayList;
import java.util.List;

import wss.map.GameMap;
import wss.player.Player;
import wss.map.MapSquare;
import wss.item.Item;
import wss.util.Path;


public abstract class Vision {
    private List<MapSquare> visibleSquares;
    private List<MapSquare> squaresThatHaveGold;
    private List<MapSquare> squaresThatHaveWater;
    private List<MapSquare> squaresThatHaveFood;
    private int[] currentPosition;

    /**
     * Checks if a map square has food
     * 
     * @param items The list of items in a map square
     */
    protected abstract boolean hasFood(List<Item> items);

    /**
     * Checks if a map square has water
     * 
     * @param items The list of items in a map square
     */
    protected abstract boolean hasWater(List<Item> items);

    /**
     * Checks if a map square has gold
     * 
     * @param items The list of items in a map square
     * @return Does the square contain gold?
     */
    protected abstract boolean hasGold(List<Item> items);

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
     * Returns the path that has the least cost overall. Determines least
     * cost by summing all costs together and choosing the sum that is
     * the least.
     * 
     * @return Path object containing the easiest path
     */
    public abstract Path easiestPath();

    /**
     * Retrieves all visible squares
     * 
     * @param map The map of the entire game
     * @param player The player in the game
     * @return List of map squares visible the the player
     */
    public abstract List<MapSquare> getVisibleSquares(GameMap map, Player player);
}
