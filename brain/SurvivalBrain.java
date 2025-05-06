package wss.brain;

import wss.util.Path;

import wss.map.*;
import wss.player.*;
import wss.trader.TradeOffer;
import wss.trader.Trader;

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


        /**
     * Attempts to initiate a trade with the given trader.
     * The SurvivalBrain accepts trades only if they cost very little and provide
     * critical resources (food/water), while ensuring survival priorities are met.
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
            System.out.println("[SurvivalBrain] Trader has no offers left.");
            return;
        }

        // Step 3: Print out the offer details (for logging/debugging)
        offer.printOffer();

        // Step 4: Extract values for offered and requested resources
        int gain = offer.getOfferedValue();   // Value of what would be gained
        int cost = offer.getRequestedValue(); // Value of what would be spent

        // Step 5: Get player's current inventory
        Inventory inv = player.getInventory();
        int currFood = inv.getFood();
        int currWater = inv.getWater();
        int currGold = inv.getGold();

        // Step 6: Evaluate trade safety
        boolean hasEnoughToTrade =
            inv.hasEnoughFood(offer.getFoodRequested()) &&
            inv.hasEnoughWater(offer.getWaterRequested()) &&
            inv.hasEnoughGold(offer.getGoldRequested());

        // Step 7: Avoid trade if player is low on essentials (protect survival)
        boolean costTooHigh =
            offer.getFoodRequested() > currFood / 3 ||      // Don’t spend more than 1/3 of food
            offer.getWaterRequested() > currWater / 3 ||    // Don’t spend more than 1/3 of water
            offer.getGoldRequested() > currGold / 2;        // Don’t spend more than 1/2 of gold

        // Step 8: Decide to trade only if gain is clearly worth it and cost is safe
        if (hasEnoughToTrade && !costTooHigh && gain > cost) {
            System.out.println("[SurvivalBrain] Accepting cautious trade. Gain (" + gain + ") > cost (" + cost + ")");
            // Spend the requested resources
            inv.spendFood(offer.getFoodRequested());
            inv.spendWater(offer.getWaterRequested());
            inv.spendGold(offer.getGoldRequested());

            // Gain the offered resources
            player.increaseFood(offer.getFoodOffered());
            player.increaseWater(offer.getWaterOffered());
            player.increaseGold(offer.getGoldOffered());
        } else {
            System.out.println("[SurvivalBrain] Rejecting trade. Too risky or not worth it.");
        }
    }
}


