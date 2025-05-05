package wss.brain;

import wss.util.Path;

import wss.map.*;
import wss.player.*;

/**
 * SocialBrain prioritizes reaching traders first.
 * If no trader is visible, falls back to a survival strategy:
 * looking for food or water, then tries to move EAST or any other valid direction.
 */
public class SocialBrain extends Brain {

    @Override
    public MoveDirection makeMove(GameMap map, Player player) {
        var visibleSquares = player.getVision().getVisibleSquares(map, player);
        MapSquare closestTrader = null;
        int minDistance = Integer.MAX_VALUE;

        for (MapSquare square : visibleSquares) {
            if (square.hasTrader()) {
                int distance = calculateDistance(player, square);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestTrader = square;
                }
            }
        }

        if (closestTrader != null) {
            System.out.println("[SocialBrain] Moving toward trader at (" +
                closestTrader.getX() + "," + closestTrader.getY() + ")");
            return directionTo(player, closestTrader);
        }

        // === STEP 4: Use Path instead of MapSquare ===
        Path foodPath = player.getVision().closestFood();
        if (foodPath != null && !foodPath.getDirections().isEmpty()) {
            System.out.println("[SocialBrain] No trader — moving toward food (Path Summary): " + foodPath.getSummary());
            return foodPath.getDirections().get(0); // First step in path
        }

        Path waterPath = player.getVision().closestWater();
        if (waterPath != null && !waterPath.getDirections().isEmpty()) {
            System.out.println("[SocialBrain] No trader/food — moving toward water (Path Summary): " + waterPath.getSummary());
            return waterPath.getDirections().get(0);
        }

        return fallbackDirection(map, player, "SocialBrain");
    }

    /**
     * Calculates Manhattan distance between the player and a target square.
     * This helps to determine the closest visible square.
     */
    private int calculateDistance(Player player, MapSquare square) {
        return Math.abs(player.getX() - square.getX()) + Math.abs(player.getY() - square.getY());
    }
}

