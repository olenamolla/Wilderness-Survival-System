package wss.brain;

import wss.util.Path;

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
            Path foodPath = player.getVision().closestFood();
            if (foodPath != null) {
                System.out.println("[SurvivalBrain] Moving toward food (Path Summary): " + foodPath.getSummary());
                return foodPath.getDirections().get(0);
            }
    
            // Step 2: Try to move toward nearest visible water
            Path waterPath = player.getVision().closestWater();
            if (waterPath != null) {
                System.out.println("[SurvivalBrain] Moving toward water (Path Summary): " + waterPath.getSummary());
                return waterPath.getDirections().get(0);
            }
    
            return fallbackDirection(map, player, "SurvivalBrain");
        }
}


