package wss.player;

/**
 * Enum representing all possible movement directions in the game map.
 * Each direction knows how it affects the player's (x, y) coordinates.
 */

public enum MoveDirection {
    
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0),
    NORTHEAST(1, -1),
    NORTHWEST(-1, -1),
    SOUTHEAST(1, 1),
    SOUTHWEST(-1, 1),
    STAY(0, 0);


    /** Change in x-coordinate when moving in this direction */
    private final int xChange;

    /** Change in y-coordinate when moving in this direction */
    private final int yChange;


    /**
     * Constructs a MoveDirection with specific x and y changes.
     * 
     * @param xChange Change in x-axis (horizontal movement)
     * @param yChange Change in y-axis (vertical movement)
     */
    MoveDirection(int xChange, int yChange) {
        this.xChange = xChange;
        this.yChange = yChange;
    }

    /**
     * Returns how much the x-coordinate should change when moving in this direction.
     * 
     * @return Change in x (can be -1, 0, or +1)
     */
    public int getXChange() {
        return xChange;
    }

    /**
     * Returns how much the y-coordinate should change when moving in this direction.
     * 
     * @return Change in y (can be -1, 0, or +1)
     */
    public int getYChange() {
        return yChange;
    }

    
}
