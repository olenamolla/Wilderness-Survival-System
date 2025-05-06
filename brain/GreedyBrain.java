package wss.brain;


import wss.map.*;
import wss.player.*;
import wss.trader.TradeOffer;
import wss.trader.Trader;
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


    @Override
    public void initiateTrade(Player player, Trader trader) {
        // Step 1: Ask the trader for their current trade offer
        TradeOffer offer = trader.offerTrade();

        // Step 2: If the trader has no more offers, exit early
        if (offer == null) {
            System.out.println("[GreedyBrain] Trader has no offers left.");
            return;
        }

        // Step 3: Print out the offer details (for logging/debugging)
        offer.printOffer();

        // Step 4: Greedy logic — accept only if gain is at least double the cost
        int gain = offer.getOfferedValue();     // What the player would get
        int cost = offer.getRequestedValue();   // What the player would give up

        // Step 5: Evaluate whether this is a "greedy-approved" trade
        if (gain >= cost * 2) {
            System.out.println("[GreedyBrain] Accepting trade. Gain (" + gain + ") is >= 2x cost (" + cost + ")");
            // Spend the requested resources
            player.getInventory().spendFood(offer.getFoodRequested());
            player.getInventory().spendWater(offer.getWaterRequested());
            player.getInventory().spendGold(offer.getGoldRequested());

            // Gain the offered resources
            player.increaseFood(offer.getFoodOffered());
            player.increaseWater(offer.getWaterOffered());
            player.increaseGold(offer.getGoldOffered());
        } else {
            System.out.println("[GreedyBrain] Rejecting trade. Not greedy enough.");
        }
    }

}
