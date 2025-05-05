package wss.brain;

import java.util.List;
import wss.map.*;
import wss.player.*;
import wss.util.Path;

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
        Path goldPath = player.getVision().closestGold();

        // If gold was found in visible range, compute the best direction to reach it
        if (goldPath != null) {
            System.out.println("[GreedyBrain] Moving toward gold (Path Summary): " + goldPath.getSummary());
        return goldPath.getDirections().get(0); // First step in path
        }
        // Step 2: No gold found — use shared fallback logic
        return fallbackDirection(map, player, "GreedyBrain");
    }
}
