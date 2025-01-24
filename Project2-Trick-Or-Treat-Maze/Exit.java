// Amber Williams and Dom Ketchens
// Project 4: Maze Game - Exit

public class Exit {
    public static final int TILE_SIZE = 32;
    private static int x;
    private static int y;
    private static String image;

    // sets initial position, defines where the exit is
    public static void start(int x, int y) {
        Exit.x = x;
        Exit.y = y;
        image = "Assets/tile-exit.png";
    }
    // draws the exit
    public static void draw() {
        int tileX = x * TILE_SIZE + TILE_SIZE / 2;
        int tileY = y * TILE_SIZE + TILE_SIZE / 2;
        StdDraw.picture(tileX, tileY, image);
    }
    
    // getter methods
    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }
}
