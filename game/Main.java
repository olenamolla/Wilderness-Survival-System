package wss.game;

public class Main {
    public static void main(String[] args) {
        
        // Create a new game instance and set it up
        // This includes setting up the map, difficulty, players, etc.
        // The Game class handles all
        Game game = new Game();  // Create a new game instance
        
        game.setup(); 
        
        game.run(); 
    
    }
}
