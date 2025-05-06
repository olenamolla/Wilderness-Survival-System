package wss.brain;

import wss.util.Path;

import wss.map.*;
import wss.player.*;
import wss.trader.TradeOffer;
import wss.trader.Trader;

/**
 * SocialBrain prioritizes reaching traders first.
 * If no trader is visible, falls back to a survival strategy:
 * looking for food or water, then tries to move EAST or any other valid direction.
 */
public class SocialBrain extends Brain {

    @Override
    public MoveDirection makeMove(GameMap map, Player player) {
        
      try {  
        var visibleSquares = player.getVision().getVisibleSquares(map, player);
        MapSquare closestTrader = null;
        int minDistance = Integer.MAX_VALUE;

        for (MapSquare square : visibleSquares) {
            if (square != null && square.hasTrader()) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return MoveDirection.EAST; // fail-safe
        }
    }

    /**
     * Calculates Manhattan distance between the player and a target square.
     * This helps to determine the closest visible square.
     */
    private int calculateDistance(Player player, MapSquare square) {
        return Math.abs(player.getX() - square.getX()) + Math.abs(player.getY() - square.getY());
    }

    /**
     * Attempts to initiate a trade with the given trader.
     * The SocialBrain is not purely greedy — it is open to fair trades,
     * especially if the gain is at least equal to the cost.
     *
     * @param player The player using this brain.
     * @param trader The trader the player is attempting to trade with.
     */
    @Override
    public void initiateTrade(Player player, Trader trader) {
        // Step 1: Ask the trader for their current trade offer
        TradeOffer offer = trader.offerTrade();

        // Step 2: If the trader has no more offers, exit early
        if (offer == null) {
            System.out.println("[SocialBrain] Trader has no offers left.");
            return;
        }

        // Step 3: Print out the offer details (for logging/debugging)
        offer.printOffer();

        // Step 4: Get gain and cost values from the offer
        int gain = offer.getOfferedValue();     // What the player would receive
        int cost = offer.getRequestedValue();   // What the player would give

        // Step 5: Evaluate if the offer is "socially acceptable"
        // Accept trade if gain is at least equal to cost, or up to 1.5x the cost
        if (gain >= cost && gain <= cost * 1.5) {
            System.out.println("[SocialBrain] Accepting trade. Gain (" + gain + ") is fair or slightly better than cost (" + cost + ")");
            // Spend the requested resources
            player.getInventory().spendFood(offer.getFoodRequested());
            player.getInventory().spendWater(offer.getWaterRequested());
            player.getInventory().spendGold(offer.getGoldRequested());

            // Gain the offered resources
            player.increaseFood(offer.getFoodOffered());
            player.increaseWater(offer.getWaterOffered());
            player.increaseGold(offer.getGoldOffered());
        } else {
            System.out.println("[SocialBrain] Rejecting trade. Deal seems unfair.");
        }
    }
}

