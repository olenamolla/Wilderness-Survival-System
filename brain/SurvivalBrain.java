package wss.brain;

import wss.map.*;
import wss.player.*;

/**
 * SurvivalBrain always seeks food and water bonuses,
 * regardless of the current resource levels.
 * If none are visible, it attempts to move EAST,
 * and if EAST is not enterable, moves to any valid adjacent square.
 */
public class SurvivalBrain extends Brain {


        @Override
        public MoveDirection makeMove(GameMap map, Player player) {
            // Step 1: Try to move toward nearest visible food
            MapSquare foodSquare = player.getVision().closestFood(map, player);
            if (foodSquare != null) {
                System.out.println("[SurvivalBrain] Moving toward food at (" + foodSquare.getX() + "," + foodSquare.getY() + ")");
                return directionTo(player, foodSquare);
            }
    
            // Step 2: Try to move toward nearest visible water
            MapSquare waterSquare = player.getVision().closestWater(map, player);
            if (waterSquare != null) {
                System.out.println("[SurvivalBrain] Moving toward water at (" + waterSquare.getX() + "," + waterSquare.getY() + ")");
                return directionTo(player, waterSquare);
            }
    
            return fallbackDirection(map, player, "SurvivalBrain");
        }
}


