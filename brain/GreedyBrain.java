package wss.brain;

import wss.map.*;
import wss.player.*;
import wss.trader.TradeOffer;
import wss.trader.Trader;
import wss.util.Path;

/**
 * GreedyBrain prioritizes gold collection but is more flexible with trades.
 * It will accept trades that are profitable, with special consideration for gold trades.
 */
public class GreedyBrain extends Brain {
    private static final double MIN_PROFIT_MARGIN = 1.2;  // 20% profit minimum
    private static final double GOLD_PROFIT_MARGIN = 1.5; // 50% profit for gold trades

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
        // This method should return null if there's no visible gold.
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

        // Calculate base values
        int gain = offer.getOfferedValue();
        int cost = offer.getRequestedValue();

        // Calculate gold-specific values
        int goldGain = offer.getGoldOffered();
        int goldCost = offer.getGoldRequested();
        boolean isGoldTrade = goldGain > 0 || goldCost > 0;

        // Check if we have enough resources
        Inventory inv = player.getInventory();
        if (!inv.hasEnoughFood(offer.getFoodRequested()) ||
            !inv.hasEnoughWater(offer.getWaterRequested()) ||
            !inv.hasEnoughGold(offer.getGoldRequested())) {
            System.out.println("[GreedyBrain] Rejecting trade. Not enough resources.");
            return;
        }

        // Accept trade if:
        // 1. It's a gold trade and gold profit margin is met
        // 2. It's a regular trade and minimum profit margin is met
        // 3. The trade is at least slightly profitable (gain > cost)
        boolean acceptTrade = false;
        String reason = "";

        if (isGoldTrade) {
            if (goldGain >= goldCost * GOLD_PROFIT_MARGIN) {
                acceptTrade = true;
                reason = "Good gold profit margin";
            } else if (gain > cost) {
                acceptTrade = true;
                reason = "Profitable overall trade";
            }
        } else {
            if (gain >= cost * MIN_PROFIT_MARGIN) {
                acceptTrade = true;
                reason = "Meets minimum profit margin";
            } else if (gain > cost) {
                acceptTrade = true;
                reason = "Slightly profitable trade";
            }
        }

        if (acceptTrade) {
            System.out.println("[GreedyBrain] Accepting trade. " + reason);
            // Execute the trade
            player.getInventory().spendFood(offer.getFoodRequested());
            player.getInventory().spendWater(offer.getWaterRequested());
            player.getInventory().spendGold(offer.getGoldRequested());

            player.increaseFood(offer.getFoodOffered());
            player.increaseWater(offer.getWaterOffered());
            player.increaseGold(offer.getGoldOffered());
        } else {
            System.out.println("[GreedyBrain] Rejecting trade. Not profitable enough.");
        }
    }
}
