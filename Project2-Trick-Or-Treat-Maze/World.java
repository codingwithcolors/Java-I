//Amber Williams and Dom Ketchens
//Project 4: Maze Game - World

import java.util.Scanner;

public class World {

    private static String[][][] levels; // levels array

    public static void start() {
        // Get all the map data and save it for later
        Scanner input = new Scanner(System.in);
        int count = input.nextInt(); // number of levels
        levels = new String[count][][]; // sets up empty array for the levels

        // iterates over each level, reads # of rows and cols
        for (int lvl = 0; lvl < count; lvl++) {
            int rows = input.nextInt();
            int cols = input.nextInt();
            setLevel(lvl, rows, cols, input); // sets up tiles for this level
        }
    }
    // set level metod fills a specific level with tile data. goes through each row
    // and column reading each tile.
    public static void setLevel(int lvl, int rows, int cols, Scanner input) {
        levels[lvl] = new String[rows][cols]; // creates a 2d array
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                String tile = input.next(); // reads each tile
                levels[lvl][y][x] = tile; // assigns tile to correct position
            }
        }
    }
    // get level method retrieves the grid for a specific level
    public static String[][] getLevel(int level) {
        return levels[level];
    }
    // get length method returns the total number of levels in the game
    public static int getLength() {
        return levels.length;
    }
}
