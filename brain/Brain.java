package wss.brain;

import java.util.List;
import wss.map.*;
import wss.player.*;
import wss.vision.*;
import wss.enums.*;

//all brain types, method
public abstract class Brain {
    public abstract MoveDirection makemove(Map map, Player player);
}

//greedybrain - priotizes routes toward gold.
public class GreedyBrain extends Brain {
    @Override
    
    public MoveDirection makeMove(Map map, Player player) {
        //all squares player can see
        List<MapSquare> visibleSquares = player.getVision().getVisibleSquares(map, player);

        //keep track of best gold
        MapSquare bestGoldSquare = null;
        int maxGoldFound = 0;

        //loop through all squares visible
        for (MapSquare square : visibleSquares) {
            if (square.getItem() instanceof GoldBonus) {
                if (gold.getAmount() > maxGoldFound) {
                    maxGoldFound = gold.getAmount();
                    bestGoldSquare = square;
                }
            }
        }

        //if gold square found, move toward
        if (bestGoldSquare != null) {
            return directionTo(player, bestGoldSquare);
        }

        //else stay in place
        return MoveDirection.STAY;
    }
}

//survivalbrain - priotizes food/water if low
public class SurvivalBrain extends Brain {
    public static final int LOW_THRESHOLD = 30;

    @Override
    public MoveDirection makeMove(Map map, Player player) {
        List<MapSquare> visibleSquares = player.getVision().getVisibleSquares();

        //check players food/water level
        boolean lowFood = player.getInventory().getFood() < LOW_THRESHOLD;
        boolean lowWater = player.getInventory().getWater() < LOW_THRESHOLD;

        MapSquare bestResourceSquare = null;
        int bestBonus = 0;

        //loop through visiblesquares
        for (MapSquare square : visibleSquares) {
            int bonus = 0;
            
            if (square.getItem() instanceof FoodBonus && lowFood) {
                bonus += ((FoodBonus) square.getItem()).getAmount();
            }

            if (square.getItem() instanceof WaterBonus && lowWater) {
                bonus += ((WaterBonus) square.getItem()).getAmount();
            }

            if (bonus > bestBonus) {
                bestBonus = bonus;
                bestResourceSquare = square;
            }

            if (bestResourceSquare != null) {
                return directionTo(player, bestResourceSquare);
            }

            return MoveDirection.STAY;
    }
}

//socialbrain - priotizes routes to traders
public class SocialBrain extends Brain {
    private static final int HELP_RADIUS = 5;

    @Override
    public MoveDirection makeMove(Map map, Player player) {
        List<MapSquare> visibleSquares = player.getVision().getVisibleSquares(map, player);

        MapSquare nearestPlayerSquare = null;
        int minDistance = Integer.MAX_VALUE;

        //loop through visible squares
        for (MapSquare square : visibleSquares) {
            if (square.getTrader() != null) {
                int distance = calculateDistance(player, square);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestTraderSquare = square;
                }
            }
        }

        if (nearestTraderSquare != null && minDistance <= HELP_RADIUS) {
            return directionTo(player, nearestTraderSquare);
        }

        //fallback strategy
        Brain fallback = new SurvivialBrain();
        return fallback.makeMove(map, player);
    }
}

//calculates direction player should move too
private static MoveDirection directionTo(Player player, MapSquare target) {
    int dx = target.getX() - player.getX();
    int dy = target.getY() - player.getY();

    //calculation of x and y coordinates
    if (dx == 0 && dy == 0) return MoveDirection.STAY;
    if (dy < 0) return MoveDirection.NORTH;
    if (dy > 0) return MoveDirection.SOUTH;
    if (dX < 0) return MoveDirection.EAST;
    if (dX > 0) return MoveDirection.WEST;

    return MoveDirection.STAY; 
}

//04-27 ?? reach()
private static int calculateDistance(Player player, MapSquare square) {
    return Math.abs(player.getX() - square.getX()) + Math.abs(player.getY() - square.getY());
}
