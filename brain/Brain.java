package wss.brain;



import wss.map.*;
import wss.player.*;

/**
 * Abstract Brain class defines the decision-making behavior for a player.
 * All Brain types must implement a strategy for making a move.
 */
public abstract class Brain {

    /**
     * Determines the next move direction based on the current map and player status.
     *
     * @param map The entire game map.
     * @param player The player making the move.
     * @return The direction the player should move (or STAY).
     */
    public abstract MoveDirection makeMove(GameMap map, Player player);

    /**
     * Calculates the direction from the player to the target square.
     *
     * @param player The player making the move.
     * @param target The destination square.
     * @return The appropriate MoveDirection.
     */
    protected MoveDirection directionTo(Player player, MapSquare target) {
        int dx = target.getX() - player.getX();
        int dy = target.getY() - player.getY();

        if (dx == 0 && dy == 0) return MoveDirection.STAY;
        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? MoveDirection.EAST : MoveDirection.WEST;
        } else {
            return dy > 0 ? MoveDirection.SOUTH : MoveDirection.NORTH;
        }
    }

    /**
     * Shared fallback logic when no preferred move (like food, trader, etc.) is found.
     * Attempts EAST first, then any legal adjacent square. Always returns a direction.
     *
     * @param map    The game map
     * @param player The player making the move
     * @param tag    A string tag for printing debug info (e.g., "SocialBrain")
     * @return A MoveDirection that is passable, never STAY
     */
    protected MoveDirection fallbackDirection(GameMap map, Player player, String tag) {
        // Step 1: Try moving EAST
        int eastX = player.getX() + MoveDirection.EAST.getXChange();
        int eastY = player.getY() + MoveDirection.EAST.getYChange();
        if (map.getSquare(eastX, eastY) != null && map.getSquare(eastX, eastY).isEnterable()) {
            System.out.println("[" + tag + "] No preferred targets — moving EAST");
            return MoveDirection.EAST;
        }

        // Step 2: Try any other valid direction
        for (MoveDirection dir : MoveDirection.values()) {
            int tryX = player.getX() + dir.getXChange();
            int tryY = player.getY() + dir.getYChange();
            if (map.getSquare(tryX, tryY) != null && map.getSquare(tryX, tryY).isEnterable()) {
                System.out.println("[" + tag + "] No EAST — moving " + dir);
                return dir;
            }
        }

        // Step 3: Emergency fallback to EAST even if blocked (will fail in Player.move())
        System.out.println("[" + tag + "] Trapped — defaulting to EAST (even if blocked)");
        return MoveDirection.EAST;
    }
}
