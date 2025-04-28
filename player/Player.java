public class Player {
    // Attributes
    private String name;
    private int currentStrength;
    private int currentFood;
    private int currentWater;
    private int maxStrength;
    private int maxFood;
    private int maxWater;
    private int gold;
    
    private Vision vision;
    private Brain brain;
    private Inventory inventory;

    //position on map
    private int x; 
    private int y;

    // Constructor
    public Player(String name, Vision vision, Brain brain, int startStrength, int startFood, int startWater) {
        this.name = name;
        this.vision = vision;
        this.brain = brain;
        this.maxStrength = startStrength;
        this.maxFood = startFood;
        this.maxWater = startWater;
        this.currentStrength = startStrength;
        this.currentFood = startFood;
        this.currentWater = startWater;
        this.gold = 0;
        this.inventory = new Inventory();
        this.x = 0; // Starting position: West edge
        this.y = 0; // Starting vertical position
    }

    // Movement method
    public void move(Map map, MoveDirection direction) {
        // Update x and y based on MoveDirection
        x += direction.getXChange();
        y += direction.getYChange();

        // Apply terrain costs at the new location
        MapSquare square = map.getSquare(x, y);
        square.applyTerrainCost(this);

        // Handle items
        collectItems(square);

        // Handle trader if present
        if (square.hasTrader()) {
            tradeWithTrader(square.getTrader());
        }
    }

    // Collect items from current square
    private void collectItems(MapSquare square) {
        for (Item item : square.getItems()) {
            item.apply(this);
        }
        square.clearItems(); // Assuming one-time bonuses are removed
    }

    // Trade interaction
    private void tradeWithTrader(Trader trader) {
        brain.initiateTrade(this, trader);
    }

    // Stat manipulation methods
    public void reduceStrength(int amount) {
        currentStrength = Math.max(0, currentStrength - amount);
    }

    public void reduceFood(int amount) {
        currentFood = Math.max(0, currentFood - amount);
    }

    public void reduceWater(int amount) {
        currentWater = Math.max(0, currentWater - amount);
    }

    public void addFood(int amount) {
        currentFood = Math.min(maxFood, currentFood + amount);
    }

    public void addWater(int amount) {
        currentWater = Math.min(maxWater, currentWater + amount);
    }

    public void addGold(int amount) {
        gold += amount;
    }

    // Getters and etc...
}
