//Amber Williams and Dom Ketchens
//Project 4: Maze Game - Scene

public class Scene {
    private static final int TILE_SIZE = 32;
    private static int rows;
    private static int cols;
    private static boolean[][] walls;
    private static int width;
    private static int height;
    private static String floorImage;
    private static String wallImage;
    //Food
    private static String foodImage;
    private static int foodX;
    private static int foodY; //Setup for multiple food items for each level
    //Keys and Gems
    private static int keyX = -1;
    private static int keyY = -1; //Add key off grid
    private static String keyImage;
    private static boolean keyPickup = false; //Set key pickup to be false to prepare for later player interaction
    private static String gemImage;
    private static int gemX = -1;
    private static int gemY = -1; //Add jack o lantern off grid
    private static boolean gemPickup = false; //Set gem pickup to be false
    //PopUp Messages
    private static double popupTextX;
    private static double popupTextY;
    private static boolean showKeyMessage = false;
    private static boolean showExitMessage = false;
    private static boolean showGemMessage = false;
    private static boolean showLevelMessage = false; //Set pop up messages to be false to prepare for later conditions to be true
    private static long keyMessageStartTime = 0;
    private static long gemMessageStartTime = 0;
    private static long exitMessageStartTime = 0;
    private static long levelMessageStartTime = 0; //Set timer for popup messages to initially be 0
    //Timer
    private static long startTime;
    private static final int TIMER_DURATION = 90; //Can change duration later for game balance
    //Duration for each popup message
    private static final int MESSAGE_DURATION_SECONDS = 2; //Set timer for all messages to be 5 seconds
    //Music & SFX
    private static Music pickupSound; //Play sfx for pickup


    public static void start(int level) {
        floorImage = "Assets/tile-passage.png";
        wallImage = "Assets/tile-brickwall.png";
        keyImage = "Assets/key.png";
        foodImage = "Assets/candy.png";
        gemImage = "Assets/jack-o-lantern.png";
        keyPickup = false; //Reset key pickup for each new level
        gemPickup = false; //Reset gem pickup for each new level

        //SFX
        pickupSound = new Music("Assets/item-pickup-sfx.wav");

        String[][] map = World.getLevel(level);
        rows = map.length;
        cols = map[0].length;

        width = cols * TILE_SIZE;
        height = rows * TILE_SIZE;

        //Popup text that changes for each level
		StdDraw.setPenColor(StdDraw.WHITE);
		//Set popup text dimensions for each level
		if (level == 0)
			{
				popupTextX = width * 0.01;
				popupTextY = height * 0.96;
		} else if (level == 1){
				popupTextX = width * 0.01;
				popupTextY = height * 0.96;
		} else if (level == 2){
				popupTextX = width * 0.01;
				popupTextY = height * 0.98;
		} else if (level == 3){
				popupTextX = width * 0.01;
				popupTextY = height * 0.98;
	}

        walls = new boolean[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                String tile = map[y][x];
                setTile(x, y, tile);
            }
        }

        startTime = System.currentTimeMillis() / 1000; //say no to milliseconds, start timer in seconds

        // Setup canvas data (tile & scale)
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0.0, width);
        StdDraw.setYscale(height, 0.0);

        placeFood(); //place food in a random position

        //Setup booleans for overlapping logic
        boolean withinBounds;
        boolean overWall;
        boolean overExit;
        boolean overKey;
        boolean overGem;

        //Place jack-o-lanterns in a random location
        do
        	{
        		gemX = (int) (Math.random() * cols);
        		gemY = (int) (Math.random() * rows);
        	}
        while (walls[gemY][gemX] ||
    			(gemX == Exit.getX() && gemY == Exit.getY()) ||
        		(gemX == Player.getX() && gemY == Player.getY()));

        //Place key in a random location
        do
        	{
        		keyX = (int) (Math.random() * cols);
        		keyY = (int) (Math.random() * rows);
 
        	//Check if the key is within bounds of the game popup
        	withinBounds = (keyX >= 0 && keyX < cols) && (keyY >= 0 && keyY < rows);

        	//Check if the key is overlapping a brick wall tile
        	overWall = withinBounds && walls[keyY][keyX];

        	//Check if the key is overlapping the exit tile
        	overExit = withinBounds && (keyX == Exit.getX() && keyY == Exit.getY());

        	//Check if the key is overlapping the jack-o-lantern
        	overGem = withinBounds && (keyX == gemX && keyY == gemY);


        	//Debug Script that shows how the do while loop keeps running until the correct conditions are met
        	if (withinBounds == false)
        		{
        			System.out.println("Key is out of bounds: " + keyX + ", " + keyY);
        		}
        	if (overWall)
        		{
        			System.out.println("Key is on a wall at: " + keyX + ", " + keyY);
        		}
        	if (overExit)
        		{
        			System.out.println("Key is on the exit at: " + keyX + ", " + keyY);
        		}
    }	while (withinBounds == false || overWall || overExit);
	}

	//Check if the player has the key for MazeGame.java to check
	public static boolean hasKey()
		{
			return keyPickup;
		}

	//Check if the player has the gem for MazeGame.java to check
	public static boolean hasGem()
		{
			return gemPickup;
		}


    public static void pickupLogic(int level)
    	{
			//Check Key Pickup
			if (Player.getX() == keyX && Player.getY() == keyY && keyPickup == false) //Checking if keyPickup is set to false and moving the key off the grid completely prevents anymore interactions once the key has been picked up by the player
			{
				keyPickup = true;
				keyX = cols + 1; //Put the key off the grid
				keyY = rows + 1; //Put the key off the grid
				showKeyMessage = true;
				keyMessageStartTime = System.currentTimeMillis() / 1000; //Set start time for the key pop up message
				pickupSound.play();
			}

			//Check gem pickup
			if (Player.getX() == gemX && Player.getY() == gemY && gemPickup == false)
					{
						gemPickup = true;
						gemX = cols + 1;
						gemY = cols + 1; //Put the jack o lantern off the grid
						showGemMessage = true;
						gemMessageStartTime = System.currentTimeMillis() / 1000;
						pickupSound.play();
					}

		//Lock the door if the player doesn't have both the key and the gem
		if (Player.getX() == Exit.getX() && Player.getY() == Exit.getY()) //note exit naming may not be accurate due to having to change for the file
			{
				if (keyPickup == false || gemPickup == false) //Prevent the player from advancing until keyPickup is true
					{
						showExitMessage = true;
						exitMessageStartTime = System.currentTimeMillis() / 1000; //Set start time for the exit pop up message
					}
				else
					{
						showLevelMessage = true;
						levelMessageStartTime = System.currentTimeMillis() / 1000; //Set start time for the level progression pop up message
					}
	  		}
	  	}

	  	//Check and update food counter
	  	public static void foodCounterCheck() {
	  		//if player moves, decrease food counter by 1
	  		if (Player.getFoodCounter() <= 0)
	  			{
                	return;
	  			}

	  	//Check if player has landed on a food item
	  	if (Player.getX() == foodX && Player.getY() == foodY){
	  		Player.setFoodCounter(Player.getFoodCounter() + 10); //adds 2 to the food counter when food's collecting
              placeFood(); //Place new food at different location after collected  		
	  		  pickupSound.play();// play the pickup sfx
	  	}
	 	}

	  	//Randomly places food at a new location (avoiding the walls)
	  	public static void placeFood()
	  		{
	  			do {
	  				foodX = randomCoordinates(cols);
	  				foodY = randomCoordinates(rows);
	  			} while ((walls[foodY][foodX] || (foodX == Exit.getX() && foodY == Exit.getY())) || (foodX == gemX && foodY == gemY) || (foodX == keyX && foodY == keyY)); //Ensures food isn't places on a wall or exit
	  		}
	  	//Random coordinate generator for food
	  	public static int randomCoordinates(int size)
	  		{
	  			return (int) (Math.random() * size);
	  		}

	//Set tiles for walls, player spawn and exit	
    public static void setTile(int x, int y, String tile) {
        if (tile.equals("#")) {
            walls[y][x] = true; //walls
        } else if (tile.equals("@")) {
            Player.start(x, y); // player spawn
        } else if (tile.equals("!")) {
            Exit.start(x,y); // exit tile
        }
    }

    //Draw the level on the canvas
    public static void draw() {
    	//Draw all tiles for the walls and floors
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int tileX = x * TILE_SIZE + TILE_SIZE / 2;
                int tileY = y * TILE_SIZE + TILE_SIZE / 2;

                if (walls[y][x] == true) {
                    StdDraw.picture(tileX, tileY, wallImage);
            	} else {
                    StdDraw.picture(tileX, tileY, floorImage);
                }
            }
        }

    //Draw the key in the level
    //Calculates the center position
    if (keyPickup == false)
    	{
    		int keyTileX = keyX * TILE_SIZE + TILE_SIZE/2;
    		int keyTileY = keyY * TILE_SIZE + TILE_SIZE/2;
    		StdDraw.picture(keyTileX, keyTileY, keyImage);
   		}

    //Draw the jack-o-lantern in the level
    if (gemPickup == false)
    	{
    		int gemTileX = gemX * TILE_SIZE + TILE_SIZE/2;
    		int gemTileY = gemY * TILE_SIZE + TILE_SIZE/2;
    		StdDraw.picture(gemTileX, gemTileY, gemImage);
   		}

   	//Checks if foodX and foodY have been assigned valid coordinates
   		if (foodX != -1 && foodY != -1){
   			int foodTileX = foodX * TILE_SIZE + TILE_SIZE / 2;
   			int foodTileY = foodY * TILE_SIZE + TILE_SIZE / 2;
   			StdDraw.picture(foodTileX, foodTileY, foodImage);
   		}

   	//Setup text color for all key and gem messages
	StdDraw.setPenColor(StdDraw.WHITE);

	//Check current time for message duration (in milliseconds because seconds isn't built into Java because Java hates me)
	long currentTime = System.currentTimeMillis() / 1000;

	//Popup message if player picks up the key but not the gem
	//Added if else for all scenarios to be logically sound for the player
	if (showKeyMessage && gemPickup == false && (currentTime - keyMessageStartTime < MESSAGE_DURATION_SECONDS))
		{
			StdDraw.textLeft(popupTextX, popupTextY, "Picked up the key. Find the jack-o-lantern!");
		}
	else if (showKeyMessage && (currentTime - keyMessageStartTime >= MESSAGE_DURATION_SECONDS))
		{
			showKeyMessage = false; //Reset key popup message once the duration is over
		}

	//Popup message if player picks up the gem but not the key
	if (showGemMessage && keyPickup == false && (currentTime - gemMessageStartTime < MESSAGE_DURATION_SECONDS))
		{
			StdDraw.textLeft(popupTextX, popupTextY, "Picked up the jack-o-lantern. Find the key!");
		}
	else if (showGemMessage && (currentTime - gemMessageStartTime >= MESSAGE_DURATION_SECONDS))
		{
			showGemMessage = false; //Reset gem popup message once the duration is over

		}

	//Popup message if player picks up the key but not the gem
	if (showKeyMessage && gemPickup == true && (currentTime - keyMessageStartTime < MESSAGE_DURATION_SECONDS))
		{
			StdDraw.textLeft(popupTextX, popupTextY, "Picked up the key. Hurry to the exit!");
		}
	else if (showKeyMessage && (currentTime - keyMessageStartTime >= MESSAGE_DURATION_SECONDS))
		{
			showKeyMessage = false; //Reset key popup message once the duration is over
		}

	//Popup message if player picks up the gem but not the key
	if (showGemMessage && keyPickup == true && (currentTime - gemMessageStartTime < MESSAGE_DURATION_SECONDS))
		{
			StdDraw.textLeft(popupTextX, popupTextY, "Picked up the jack-o-lantern. Hurry to the exit!");
		}
	else if (showGemMessage && (currentTime - gemMessageStartTime >= MESSAGE_DURATION_SECONDS))
		{
			showGemMessage = false; //Reset gem popup message once the duration is over

		}

	//Popup message if the player tries to leave without the key or the gem
	if (showExitMessage && (keyPickup == false || gemPickup == false) && (currentTime - exitMessageStartTime < MESSAGE_DURATION_SECONDS))
		{
			StdDraw.textLeft(popupTextX, popupTextY, "The door is locked. Find the key and the gem.");
		}
	else if (showExitMessage && (currentTime - exitMessageStartTime >= MESSAGE_DURATION_SECONDS))
		{
			showExitMessage = false; //Reset exit popup message once the duration is over
		}

	//Popup message if the player has the key and the gem and reaches the exit
	if (showLevelMessage && (keyPickup == true && gemPickup == true) && (currentTime - levelMessageStartTime < MESSAGE_DURATION_SECONDS))
		{
			StdDraw.textLeft(popupTextX, popupTextY, "You made it to the next level. Keep going!");
		}
	else if (showLevelMessage && (currentTime - levelMessageStartTime >= MESSAGE_DURATION_SECONDS))
		{
			showLevelMessage = false; //Reset level popup message once the duration is over
		}
	//Message for the food counter
		StdDraw.textLeft(180.0, 20.0, "Food: " + Player.getFoodCounter());

	//Convert the current time to seconds
	currentTime = System.currentTimeMillis() / 1000; //because no one likes milliseconds

	//Convert the remaining time in seconds
	long elapsedTime = currentTime - startTime;
	long remainingTime = TIMER_DURATION - elapsedTime;

	//Check if the timer is finished
	if (remainingTime <= 0)
		{
			MazeGame.setGameOver(true);
		}
	else
		{
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.textLeft(10.0, 20.0, "Time: " + remainingTime + " seconds.");
		}

	}

public static boolean canMove(int x, int y) {
    // Ensure x and y are within the bounds of the walls array
    if (x < 0 || x >= cols || y < 0 || y >= rows) {
        return false; // Out of bounds, can't move
    }
    return !walls[y][x]; // Check if the tile is not a wall
}

//To use for the ghostie
public static int getCols() {
    return cols;
}

public static int getRows() {
    return rows;
}


}