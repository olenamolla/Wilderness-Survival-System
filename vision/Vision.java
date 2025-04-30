package wss.vision;

import wss.map.GameMap;
import wss.player.Player;
import wss.map.MapSquare;
import wss.item.Item;


public abstract class Vision {
    public abstract PlayerPath closestFood();
    public abstract PlayerPath closestWater();
    public abstract PlayerPath easiestPath();
    public abstract List<MapSquare> getVisibleSquares(GameMap map, Player player);
}
