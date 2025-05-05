package wss.vision;

// GreedyVision scans all surrounding squares. 
// Looks for the richest bonuses, even if they are farther away or expensive to reach.

public class GreedyVision {

    protected List<MoveDirection> directions;

    public GreedyVision() {
        super();
        this.directions = new ArrayList<>();
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

        MapSquare currentSquare;

        // iterates over each visible square to look for the optimal path
        for (int index = 0; index < visibleSquares.size(); index++) {
            currentSquare = visibleSquares.get(index);
            
            // if the current square has food, then evaluate whether it is the optimal path
            if (hasFood(currentSquare)) {    // replace hasFood() with MapSquare function once it exists
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
     * Returns the path to the closest water. If there are more than one
     * path that is the same distance, this method chooses the path that
     * leads to more water. If there are multiple paths that are the same
     * distance and have the same amount of water, choose path randomly.
     * 
     * @return Path object containing the best path to water
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

        MapSquare currentSquare;

        // iterates over each visible square to look for the optimal path
        for (int index = 0; index < visibleSquares.size(); index++) {
            currentSquare = visibleSquares.get(index);
            
            // if the current square has food, then evaluate whether it is the optimal path
            if (hasWater(currentSquare)) {    // replace hasWater() with MapSquare function once it exists
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
     * Returns the path to the closest gold. If there are more than one
     * path that is the same distance, this method chooses the path that
     * leads to more gold. If there are multiple paths that are the same
     * distance and have the same amount of gold, choose path randomly.
     * 
     * @return Path object containing the best path to gold
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

        MapSquare currentSquare;

        // iterates over each visible square to look for the optimal path
        for (int index = 0; index < visibleSquares.size(); index++) {
            currentSquare = visibleSquares.get(index);
            
            // if the current square has food, then evaluate whether it is the optimal path
            if (hasGold(currentSquare)) {    // replace hasGold() with MapSquare function once it exists
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
     * Stores the squares that are only one square away from the player
     * for later evaluation to determine which square has the least cost
     */
    protected void setEasiestSquares() {
        easiestSquares.add(visibleSquares.get(1));
        easiestSquaresDirections.add(MoveDirection.NORTHEAST);

        easiestSquares.add(visibleSquares.get(2));
        easiestSquaresDirections.add(MoveDirection.EAST);

        easiestSquares.add(visibleSquares.get(3));
        easiestSquaresDirections.add(MoveDirection.SOUTHEAST);
    }

    /**
     * Retrieves all visible squares
     * 
     * @param map The map of the entire game
     * @param player The player in the game
     * @return List of map squares visible the the player
     */
    public List<MapSquare> getVisibleSquares(GameMap map, Player player) {

        // stores square and direction for north square
        visibleSquares.add(map.getSquare(player.getX(), player.getY() - 1));
        directions.add(MoveDirection.NORTH);

        // stores square and direction for northeast square
        visibleSquares.add(map.getSquare(player.getX() + 1, player.getY() - 1));
        directions.add(MoveDirection.NORTHEAST);

        // stores square and direction for east square
        visibleSquares.add(map.getSquare(player.getX() + 1, player.getY()));
        directions.add(MoveDirection.EAST);

        // stores square and direction for east square
        visibleSquares.add(map.getSquare(player.getX() + 1, player.getY() + 1));
        directions.add(MoveDirection.SOUTHEAST);

        // stores square and direction for south square
        visibleSquares.add(map.getSquare(player.getX(), player.getY() + 1));
        directions.add(MoveDirection.SOUTH);

        // stores square and direction for south west square
        visibleSquares.add(map.getSquare(player.getX() - 1, player.getY() + 1));
        directions.add(MoveDirection.SOUTHWEST);

        // stores square and direction for west square
        visibleSquares.add(map.getSquare(player.getX() - 1, player.getY()));
        directions.add(MoveDirection.WEST);

        // stores square and direction for north west square
        visibleSquares.add(map.getSquare(player.getX() - 1, player.getY() - 1));
        directions.add(MoveDirection.NORTHWEST);

        return visibleSquares;
    }
}
