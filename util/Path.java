package wss.util;

import java.util.ArrayList;
import java.util.List;

import wss.map.Terrain;
import wss.player.MoveDirection;

/**
 * Represents a sequence of movement directions through the map.
 * Tracks the accumulated movement, food, and water costs for following this path.
 */

public class Path {
    
    /** The list of movement directions in this path */
    private List<MoveDirection> directions;

    /** Total movement (strength) cost for following the path */
    private int movementCost;

    /** Total food cost for following the path */
    private int foodCost;

    /** Total water cost for following the path */
    private int waterCost;


    /**
     * Constructs an empty Path.
     * Costs start at zero and no steps are added initially.
     */
    public Path() {
        this.directions = new ArrayList<>();
        this.movementCost = 0;
        this.foodCost = 0;
        this.waterCost = 0;
        System.out.println("[Path] New empty path created.");
    }


    /**
     * Adds a new step to the path.
     * Also updates the total movement, food, and water costs based on the terrain of the new step.
     *
     * @param direction The direction to add (NORTH, EAST, etc.)
     * @param terrain The terrain being entered after making this move
     */
    public void addStep(MoveDirection direction, Terrain terrain) {
        directions.add(direction);

        // Add terrain costs
        movementCost += terrain.getMovementCost();
        foodCost += terrain.getFoodCost();
        waterCost += terrain.getWaterCost();

        System.out.println("[Path] Step added: " + direction +
            " (Movement Cost: +" + terrain.getMovementCost() +
            ", Food Cost: +" + terrain.getFoodCost() +
            ", Water Cost: +" + terrain.getWaterCost() + ")");
    }

    /**
     * Returns the list of movement directions in this path.
     * 
     * @return List of MoveDirection objects
     */
    public List<MoveDirection> getDirections() {
        return directions;
    }

    /**
     * Returns the total movement (strength) cost accumulated along this path.
     * 
     * @return Total movement cost
     */
    public int getMovementCost() {
        return movementCost;
    }

    /**
     * Returns the total food cost accumulated along this path.
     * 
     * @return Total food cost
     */
    public int getFoodCost() {
        return foodCost;
    }

    /**
     * Returns the total water cost accumulated along this path.
     * 
     * @return Total water cost
     */
    public int getWaterCost() {
        return waterCost;
    }


    /**
     * Returns a summary of the path for display or debugging.
     * Shows total cost and the sequence of directions.
     * 
     * @return String summary of the path
     */
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Path] Summary: ");
        for (MoveDirection dir : directions) {
            sb.append(dir.name()).append(" -> ");
        }
        sb.append("END | Total Movement: ").append(movementCost)
          .append(", Food: ").append(foodCost)
          .append(", Water: ").append(waterCost);
        return sb.toString();
    }
}
