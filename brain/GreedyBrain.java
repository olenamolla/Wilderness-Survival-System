package wss.brain;

import java.util.List;
import wss.map.*;
import wss.player.*;

/**
 * GreedyBrain always tries to move toward the closest square that contains gold.
 * It uses the player's vision to scan for visible MapSquares, checks for gold,
 * and returns the direction toward the best gold spot.
 */
public class GreedyBrain extends Brain {

    /**
     * Makes a move decision based on visible gold bonuses.
     *
     * @param map    The full game map.
     * @param player The player using this brain.
     * @return A MoveDirection toward gold, or EAST if none is seen.
     */
    @Override
    public MoveDirection makeMove(GameMap map, Player player) {
        // Ask the player's vision object to return the closest gold-containing square.
        // This method should return null if there’s no visible gold.
        MapSquare goldSquare = player.getVision().closestGold(map, player);

        // If gold was found in visible range, compute the best direction to reach it
        if (goldSquare != null) {
            System.out.println("[GreedyBrain] Moving toward gold at (" +
                goldSquare.getX() + "," + goldSquare.getY() + ")");
            return directionTo(player, goldSquare); // Move toward the gold square.
        }
        // Step 2: No gold found — use shared fallback logic
        return fallbackDirection(map, player, "GreedyBrain");
    }
}
