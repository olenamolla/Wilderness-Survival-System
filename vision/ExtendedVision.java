package wss.vision;

import wss.util.Path;
import java.util.ArrayList;
import java.util.List;

import wss.map.GameMap;
import wss.map.MapSquare;
import wss.player.MoveDirection;
import wss.player.Player;

// ExtendedVision looks ahead farther (up to 2-3 squares). 
// Tries to find longer paths that are overall easier and safer, even if it means taking more steps.

public class ExtendedVision extends Vision {

    // each index of listOfSequencesOfDirections and listOfSequencesOfSquares correspond with each
    // other. Each element of both represents properties of a path. For example, index 0 of 
    // listOfSequencesOfdirections and listOfSequencesOfSquares represent the sequence of 
    // directions and squares for a path
    protected List<List<MoveDirection>> listOfSequencesOfDirections;
    protected List<List<MapSquare>> listOfSequencesOfSquares;

    // current position of the player, only needed for the extended vision
    protected int[] currentPosition;

    public ExtendedVision() {
        super();
        this.listOfSequencesOfDirections = new ArrayList<>();
        this.currentPosition = new int[2];
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

        // the current square of the outer loop iteration
        MapSquare currentSquare;

        // the current minimum distance to food of an iteration
        int currentMinDistance = 3;

        // the current distance of the current path
        int currentDistance;

        // the sequence of directions of the current square
        List<MoveDirection> sequenceOfDirections;

        // the list of squares of the path of the path to the current square
        List<MapSquare> sequenceOfSquares;

        // iterates over each visible square to look for the optimal path
        for (int index = 0; index < visibleSquares.size(); index++) {
            currentSquare = visibleSquares.get(index);
            currentDistance = Math.min(currentPosition[0] - currentSquare.getX(), currentPosition[1] - currentSquare.getY());

            // ensure distance is always positive since this is only supposed to represent how far a square is from a player,
            // not also its direction
            if (currentDistance < 0) {
                currentDistance = currentDistance * -1;
            }

            // if the current square has food and the distance is equal or less than the current minimum distance, evaluate whether it is the optimal path
            if (hasFood(currentSquare) && (currentDistance <= currentMinDistance)) {
                currentPath = new Path();
                sequenceOfDirections = listOfSequencesOfDirections.get(index);
                sequenceOfSquares = listOfSequencesOfSquares.get(index);

                // set sequence of directions and terrains of a path
                for (int sequenceIndex = 0; sequenceIndex < sequenceOfDirections.size(); sequenceIndex++) {
                    currentPath.addStep(
                        sequenceOfDirections.get(sequenceIndex),
                        sequenceOfSquares.get(sequenceIndex).getTerrain(),
                        sequenceOfSquares.get(sequenceIndex)
                    );
                }

                currentTotalCost = currentPath.getMovementCost() + currentPath.getFoodCost() + currentPath.getWaterCost();

                // set the new optimal path if a closer path is found. If a closer path is not found
                // but a path that is of the same distance that has less cost is found, still choose
                // this optimal path. Must construct an if else stament because if a closer path is
                // found, we do not want to override the closer path with a path that isn't as close
                // but has better cost. We prioritize finding the closer square over the better cost
                // since that is the purpose of this function
                if (currentDistance < currentMinDistance) {
                    bestTotalCost = currentTotalCost;
                    optimalPath = currentPath;
                }
                else if (currentTotalCost < bestTotalCost) {
                    bestTotalCost = currentTotalCost;
                    optimalPath = currentPath;
                }
            }
        }

        return optimalPath;
    }

    /**
     * Returns the path to the closest food. If there are more than one
     * path that is the same distance, this method chooses the path that
     * leads to more food. If there are multiple paths that are the same
     * distance and have the same amount of food, choose path randomly.
     * 
     * @return Path object containing the best path to food
     */
    public Path closestWater() {
        // contains the square that will take the least accumulated cost (movement + gold + water) to get to
        int bestTotalCost = Integer.MAX_VALUE;

        // contains the current total cost of the iteration
        int currentTotalCost = Integer.MAX_VALUE;

        // the current path of the iteration
        Path currentPath = null;

        // the closest and best path to the square with food
        Path optimalPath = null;

        // the current square of the outer loop iteration
        MapSquare currentSquare;

        // the current minimum distance to food of an iteration
        int currentMinDistance = 3;

        // the current distance of the current path
        int currentDistance;

        // the sequence of directions of the current square
        List<MoveDirection> sequenceOfDirections;

        // the list of squares of the path of the path to the current square
        List<MapSquare> sequenceOfSquares;

        // iterates over each visible square to look for the optimal path
        for (int index = 0; index < visibleSquares.size(); index++) {
            currentSquare = visibleSquares.get(index);
            currentDistance = Math.min(currentPosition[0] - currentSquare.getX(), currentPosition[1] - currentSquare.getY());

            // ensure distance is always positive since this is only supposed to represent how far a square is from a player,
            // not also its direction
            if (currentDistance < 0) {
                currentDistance = currentDistance * -1;
            }

            // if the current square has food and the distance is equal or less than the current minimum distance, evaluate whether it is the optimal path
            if (hasWater(currentSquare) && (currentDistance <= currentMinDistance)) {
                currentPath = new Path();
                sequenceOfDirections = listOfSequencesOfDirections.get(index);
                sequenceOfSquares = listOfSequencesOfSquares.get(index);

                // set sequence of directions and total cost of a path since extended vision has paths that can be longer than one square
                for (int sequenceIndex = 0; sequenceIndex < sequenceOfDirections.size(); sequenceIndex++) {
                    currentPath.addStep(
                        sequenceOfDirections.get(sequenceIndex),
                        sequenceOfSquares.get(sequenceIndex).getTerrain(),
                        sequenceOfSquares.get(sequenceIndex)
                    );
                }

                currentTotalCost = currentPath.getMovementCost() + currentPath.getFoodCost() + currentPath.getWaterCost();

                // set the new optimal path if a closer path is found. If a closer path is not found
                // but a path that is of the same distance that has less cost is found, still choose
                // this optimal path. Must construct an if else stament because if a closer path is
                // found, we do not want to override the closer path with a path that isn't as close
                // but has better cost. We prioritize finding the closer square over the better cost
                // since that is the purpose of this function
                if (currentDistance < currentMinDistance) {
                    bestTotalCost = currentTotalCost;
                    optimalPath = currentPath;
                }
                else if (currentTotalCost < bestTotalCost) {
                    bestTotalCost = currentTotalCost;
                    optimalPath = currentPath;
                }
            }
        }

        return optimalPath;
    }
    
        /**
     * Returns the path to the closest food. If there are more than one
     * path that is the same distance, this method chooses the path that
     * leads to more food. If there are multiple paths that are the same
     * distance and have the same amount of food, choose path randomly.
     * 
     * @return Path object containing the best path to food
     */
    public Path closestGold() {
        // contains the square that will take the least accumulated cost (movement + gold + water) to get to
        int bestTotalCost = Integer.MAX_VALUE;

        // contains the current total cost of the iteration
        int currentTotalCost = Integer.MAX_VALUE;

        // the current path of the iteration
        Path currentPath = null;

        // the closest and best path to the square with food
        Path optimalPath = null;

        // the current square of the outer loop iteration
        MapSquare currentSquare;

        // the current minimum distance to food of an iteration
        int currentMinDistance = 3;

        // the current distance of the current path
        int currentDistance;

        // the sequence of directions of the current square
        List<MoveDirection> sequenceOfDirections;

        // the list of squares of the path of the path to the current square
        List<MapSquare> sequenceOfSquares;

        // iterates over each visible square to look for the optimal path
        for (int index = 0; index < visibleSquares.size(); index++) {
            currentSquare = visibleSquares.get(index);
            currentDistance = Math.min(currentPosition[0] - currentSquare.getX(), currentPosition[1] - currentSquare.getY());

            // ensure distance is always positive since this is only supposed to represent how far a square is from a player,
            // not also its direction
            if (currentDistance < 0) {
                currentDistance = currentDistance * -1;
            }

            // if the current square has food and the distance is equal or less than the current minimum distance, evaluate whether it is the optimal path
            if (hasGold(currentSquare) && (currentDistance <= currentMinDistance)) {
                currentPath = new Path();
                sequenceOfDirections = listOfSequencesOfDirections.get(index);
                sequenceOfSquares = listOfSequencesOfSquares.get(index);

                // set sequence of directions and total cost of a path since extended vision has paths that can be longer than one square
                for (int sequenceIndex = 0; sequenceIndex < sequenceOfDirections.size(); sequenceIndex++) {
                    currentPath.addStep(
                        sequenceOfDirections.get(sequenceIndex),
                        sequenceOfSquares.get(sequenceIndex).getTerrain(),
                        sequenceOfSquares.get(sequenceIndex)
                        );
                }

                currentTotalCost = currentPath.getMovementCost() + currentPath.getFoodCost() + currentPath.getWaterCost();

                // set the new optimal path if a closer path is found. If a closer path is not found
                // but a path that is of the same distance that has less cost is found, still choose
                // this optimal path. Must construct an if else stament because if a closer path is
                // found, we do not want to override the closer path with a path that isn't as close
                // but has better cost. We prioritize finding the closer square over the better cost
                // since that is the purpose of this function
                if (currentDistance < currentMinDistance) {
                    bestTotalCost = currentTotalCost;
                    optimalPath = currentPath;
                }
                else if (currentTotalCost < bestTotalCost) {
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
        List<MoveDirection> sequenceOfDirections;
        List<MapSquare> sequenceOfSquares;
        currentPosition[0] = player.getX();
        currentPosition[1] = player.getY();

        // stores square and direction for north east square
        sequenceOfDirections = new ArrayList<>();
        sequenceOfSquares = new ArrayList<>();
        visibleSquares.add(map.getSquare(player.getX() + 1, player.getY() - 1));
        sequenceOfDirections.add(MoveDirection.NORTHEAST);
        sequenceOfSquares.add(map.getSquare(player.getX() + 1, player.getY() - 1));
        listOfSequencesOfDirections.add(sequenceOfDirections);
        listOfSequencesOfSquares.add(sequenceOfSquares);

        // stores square and direction for east square
        sequenceOfDirections = new ArrayList<>();
        sequenceOfSquares = new ArrayList<>();
        visibleSquares.add(map.getSquare(player.getX() + 1, player.getY()));
        sequenceOfDirections.add(MoveDirection.EAST);
        sequenceOfSquares.add(map.getSquare(player.getX() + 1, player.getY()));
        listOfSequencesOfDirections.add(sequenceOfDirections);
        listOfSequencesOfSquares.add(sequenceOfSquares);

        // stores square and direction for south east square
        sequenceOfDirections = new ArrayList<>();
        sequenceOfSquares = new ArrayList<>();
        visibleSquares.add(map.getSquare(player.getX() + 1, player.getY() + 1));
        sequenceOfDirections.add(MoveDirection.SOUTHEAST);
        sequenceOfSquares.add(map.getSquare(player.getX() + 1, player.getY() + 1));
        listOfSequencesOfDirections.add(sequenceOfDirections);
        listOfSequencesOfSquares.add(sequenceOfSquares);

        // stores square and directions for east -> north east square
        sequenceOfDirections = new ArrayList<>();
        sequenceOfSquares = new ArrayList<>();
        visibleSquares.add(map.getSquare(player.getX() + 2, player.getY() - 1));
        sequenceOfDirections.add(MoveDirection.EAST);
        sequenceOfDirections.add(MoveDirection.NORTHEAST);
        sequenceOfSquares.add(map.getSquare(player.getX() + 1, player.getY()));
        sequenceOfSquares.add(map.getSquare(player.getX() + 2, player.getY() - 1));
        listOfSequencesOfDirections.add(sequenceOfDirections);
        listOfSequencesOfSquares.add(sequenceOfSquares);

        // stores square and direction for east -> east square
        sequenceOfDirections = new ArrayList<>();
        sequenceOfSquares = new ArrayList<>();
        visibleSquares.add(map.getSquare(player.getX() + 2, player.getY()));
        sequenceOfDirections.add(MoveDirection.EAST);
        sequenceOfDirections.add(MoveDirection.EAST);
        sequenceOfSquares.add(map.getSquare(player.getX() + 1, player.getY()));
        sequenceOfSquares.add(map.getSquare(player.getX() + 2, player.getY()));
        listOfSequencesOfDirections.add(sequenceOfDirections);
        listOfSequencesOfSquares.add(sequenceOfSquares);

        // stores square and direction for east -> south east square
        sequenceOfDirections = new ArrayList<>();
        sequenceOfSquares = new ArrayList<>();
        visibleSquares.add(map.getSquare(player.getX() + 2, player.getY() + 1));
        sequenceOfDirections.add(MoveDirection.EAST);
        sequenceOfDirections.add(MoveDirection.SOUTHEAST);
        sequenceOfSquares.add(map.getSquare(player.getX() + 1, player.getY()));
        sequenceOfSquares.add(map.getSquare(player.getX() + 2, player.getY() + 1));
        listOfSequencesOfDirections.add(sequenceOfDirections);
        listOfSequencesOfSquares.add(sequenceOfSquares);

        // stores square and direction for east -> east -> east square
        sequenceOfDirections = new ArrayList<>();
        sequenceOfSquares = new ArrayList<>();
        visibleSquares.add(map.getSquare(player.getX() + 3, player.getY()));
        sequenceOfDirections.add(MoveDirection.EAST);
        sequenceOfDirections.add(MoveDirection.EAST);
        sequenceOfDirections.add(MoveDirection.EAST);
        sequenceOfSquares.add(map.getSquare(player.getX() + 1, player.getY()));
        sequenceOfSquares.add(map.getSquare(player.getX() + 2, player.getY()));
        sequenceOfSquares.add(map.getSquare(player.getX() + 3, player.getY()));
        listOfSequencesOfDirections.add(sequenceOfDirections);
        listOfSequencesOfSquares.add(sequenceOfSquares);

        return visibleSquares;
    }

    @Override
    protected void setEasiestSquares() {
    // Option 1: Use the simplest square in visibleSquares (e.g. cheapest path)
    // Option 2: Leave empty if unused or logic is not relevant in ExtendedVision
        if (!visibleSquares.isEmpty()) {
            easiestSquares.add(visibleSquares.get(0));
            easiestSquaresDirections.add(MoveDirection.EAST); // Replace with logic if needed
        }
    }
}
