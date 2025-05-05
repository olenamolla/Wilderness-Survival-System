package wss.brain;

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
        // === STEP 1: Get all visible squares ===
        var visibleSquares = player.getVision().getVisibleSquares(map, player);

        MapSquare closestTrader = null;
        int minDistance = Integer.MAX_VALUE;

        // === STEP 2: Find the nearest visible trader ===
        for (MapSquare square : visibleSquares) {
            if (square.hasTrader()) {
                int distance = calculateDistance(player, square);

                if (distance < minDistance) {
                    minDistance = distance;
                    closestTrader = square;
                }
            }
        }

        // === STEP 3: If trader found, go toward trader ===
        if (closestTrader != null) {
            System.out.println("[SocialBrain] Moving toward trader at (" +
                closestTrader.getX() + "," + closestTrader.getY() + ")");
            return directionTo(player, closestTrader);
        }

        // === STEP 4: If no trader — fallback to SurvivalBrain behavior ===

        // Step 4.1: Try finding food
        MapSquare foodSquare = player.getVision().closestFood(map, player);
        if (foodSquare != null) {
            System.out.println("[SocialBrain] No trader — moving toward food at (" +
                foodSquare.getX() + "," + foodSquare.getY() + ")");
            return directionTo(player, foodSquare);
        }

        // Step 4.2: Try finding water
        MapSquare waterSquare = player.getVision().closestWater(map, player);
        if (waterSquare != null) {
            System.out.println("[SocialBrain] No trader/food — moving toward water at (" +
                waterSquare.getX() + "," + waterSquare.getY() + ")");
            return directionTo(player, waterSquare);
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

