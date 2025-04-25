package wss.game;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {
    DifficultySettings diff = DifficultySettings.HARD;
    int[] scaled = diff.getScaledInitialResources(20, 20);
    System.out.println("Scaled Resources for 20x20 map: ");
    System.out.println("Strength: " + scaled[0]);
    System.out.println("Food: " + scaled[1]);
    System.out.println("Water: " + scaled[2]);
    }
}
