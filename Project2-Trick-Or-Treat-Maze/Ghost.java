import java.util.Random; // import random class

public class Ghost {
    public static final int TILE_SIZE = 32;

    //ghost position and image
    private static int x;
    private static int y;
    private static String image;

    // speed of the ghost (how many tiles it moves per update)
    private static final int SPEED = 1;
    private static Random rand = new Random(); // creates a random number generator

    // start the ghost at a specific position
    public static void startRandom() {
        image = "Assets/Ghost.png"; // Set the image of the ghost

        //Get the maze dimensions for each level and pick a random coordinate
        int cols = Scene.getCols();
        int rows = Scene.getRows();

        //Find a random position that doesn't overlap the walls
        do {
            x = rand.nextInt(cols); // Random x position
            y = rand.nextInt(rows); // Random y position
       } while (x == Player.getX() && y == Player.getY() || !Scene.canMove(x, y));
    }

    // draw the ghost at its current position
    public static void draw() {
        int tileX = x * TILE_SIZE + TILE_SIZE / 2;
        int tileY = y * TILE_SIZE + TILE_SIZE / 2;
        StdDraw.picture(tileX, tileY, image); // draw the ghost at its position
    }

    // update the ghost's position to chase the player
    public static void update() {
        if (!Player.hasMoved()) {
            return; // return if the player hasn't moved
        }

        // get the player's current position
        int playerX = Player.getX();
        int playerY = Player.getY();

        // try to move towards the player, but randomize if stuck
        boolean moved = false;

        // try moving towards the player horizontally (left-right)
        if (x < playerX && Scene.canMove(x + SPEED, y)) {
            x++; // move right towards the player
            moved = true;
        } else if (x > playerX && Scene.canMove(x - SPEED, y)) {
            x--; // move left towards the player
            moved = true;
        }

        // try moving towards the player vertically (up-down)
        if (!moved) {
            if (y < playerY && Scene.canMove(x, y + SPEED)) {
                y++; // move down towards the player
                moved = true;
            } else if (y > playerY && Scene.canMove(x, y - SPEED)) {
                y--; // move up towards the player
                moved = true;
            }
        }

        // if the ghost couldn't move towards the player, move randomly
        if (!moved) {
            randomDirection();
        }
    }

    // randomly move the ghost if it is stuck. Scene.canMove checks whether
    // it can move to a new tile.
    private static void randomDirection() {
        // try a random direction (up, down, left, right)
        int direction = rand.nextInt(4); // 0 = left, 1 = right, 2 = up, 3 = down

        switch (direction) {
            case 0: // try moving left
                if (Scene.canMove(x - SPEED, y)) {
                    x--;
                }
                break;
            case 1: // try moving right
                if (Scene.canMove(x + SPEED, y)) {
                    x++;
                }
                break;
            case 2: // try moving up
                if (Scene.canMove(x, y - SPEED)) {
                    y--;
                }
                break;
            case 3: // try moving down
                if (Scene.canMove(x, y + SPEED)) {
                    y++;
                }
                break;
        }
    }

    public static int getX()
        {
            return x;
        }
    public static int getY()
        {
            return y;
        }
}
