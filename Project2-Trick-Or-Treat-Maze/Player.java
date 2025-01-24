// Amber and Dom Ketchens
// Project 4: Maze Game - Player

import java.util.ArrayList;

public class Player {
    public static final int TILE_SIZE = 32;

    // player starting position and image
    private static int x;
    private static int y;
    private static String image;

    // player's food/health counter
    private static int foodCounter = 300; // Set initial food counter to 100
    private static boolean playerMoved = false; // tracks if player moved

    // players starting position & image
    public static void start(int x, int y) {
        Player.x = x;
        Player.y = y;
        image = "Assets/player-front-idle.png";
    }

    // Draws player's position on screen
    public static void draw() {
        int tileX = x * TILE_SIZE + TILE_SIZE / 2;
        int tileY = y * TILE_SIZE + TILE_SIZE / 2;
        StdDraw.picture(tileX, tileY, image);
    }

    // Updates player's position/image based on input
    public static void update() {
        boolean moved = false; // Checks if player moved

        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();

            // Player movement based on key pressed
            if (key == 'w' && Scene.canMove(x, y - 1)) {
                y--; moved = true; // 'w' Move up
                image = "Assets/player-back-idle.png";
            } else if (key == 's' && Scene.canMove(x, y + 1)) {
                y++; moved = true; // 's' Move down
                image = "Assets/player-front-idle.png";
            } else if (key == 'a' && Scene.canMove(x - 1, y)) {
                x--; moved = true; // 'a' Move left
                image = "Assets/player-left-idle.png";
            } else if (key == 'd' && Scene.canMove(x + 1, y)) {
                x++; moved = true; // 'd' Move right
                image = "Assets/player-right-idle.png";
            }
        }

        if (moved) {
            foodCounter--; // Decrease food counter by 1 for each step
            Scene.foodCounterCheck(); // Check if the food counter needs to be updated
        }

        // Set playerMoved to true if the player has moved
        playerMoved = moved;
    }

    // Get current food counter
    public static int getFoodCounter() {
        return foodCounter;
    }

    // Set food counter
    public static void setFoodCounter(int food) {
        foodCounter = food;
    }

    // Get method returns player's current X coordinate
    public static int getX() {
        return x;
    }

    // Get method returns player's current Y coordinate
    public static int getY() {
        return y;
    }

    // Get method checks if player has moved in the current update
    public static boolean hasMoved() {
        return playerMoved;
    }
}
