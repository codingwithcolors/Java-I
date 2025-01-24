//Dom Ketchens
//Project 1: Zombie Apocalypse

import java.util.Scanner;

public class ZombieApocalypse{
	public static void main(String[]args)
	{
		//Setup Game Data
		Scanner input = new Scanner(System.in);
		boolean gameOver = false;
		int colSize = 10;
		int rowSize = 10;
		String floorTile = ". ";
		int playerX = 0;
		int playerY = 0;
		String playerTile = "@ ";
		int exitX = colSize - 1;
		int exitY = rowSize - 1;
		String exitTile = "# "; 
		int [][] zombies = new int[6][2]; //An array for the zombies so that the first integer calls for a specific zombie and the second integer represents the X and Y coordinates
		String zombieTile = "+ ";
		boolean [] zombieStatus = new boolean[6]; //Identifies which specific zombie is being represented and applies logic to all zombies when referenced by itself
		boolean keyPickup = false; //Set key pickup to be false to prepare for later player interaction
		String keyTile = "$ "; //Introduces a key mechanic
		int level = 1; //Sets up the game to start at level 1
		String weaponTile = "^ "; //Introduces a weapon mechanic
		boolean weaponPickup = false; //Set weapon pickup to be false to prepare for later player or zombie interaction
		int keyX = -1;
		int keyY = -1; 
		int weaponX = -1;
		int weaponY = -1;
		int foodX = -1;
		int foodY = -1; //Sets the above integers to start off the grid to be later declared for a random coordinate within the grid
		int foodCounter = 100; //Set the food counter, can be altered for game difficulty
		String foodTile = "& "; //Introduces the food counter mechanic

		//Reset zombies between each level
		for (int i = 0; i < zombies.length; i++)
			{
				zombieStatus[i] = false;
				zombies[i][0] = -1; //Has the same logic as the game setup integers above where zombies are initially set off the grid
				zombies[i][1] = -1; //If this reset mechanic were not present, the zombies will instantly kill the player upon reaching the exit door
			}

		//Setup Level 1 Zombie
		zombieStatus[0] = true; //Evokes the creation of zombie 1
		zombies[0][0] = 5;
		zombies[0][1] = 5; //Zombies have fixed coordinates in order to give predictability for the player

		//Setup Game Data variables to add unpredictability and replayability for the game
		//Store in do-while loops so that the randomized objects do not accidentally spawn over other objects
		for (int i = 0; i < zombies.length; i++)

				{
					do
					{	
						keyX = randomCoordinates(colSize); //randomCoordinates is being referenced in the public static class. This allows for less repetition.
						keyY = randomCoordinates(rowSize);
					}

					while ((keyX == playerX && keyY == playerY) ||
							(keyX == weaponX && keyY == weaponY) ||
							(keyX == foodX && keyY == foodY) ||
							(keyX == exitX && keyY == exitY) ||
							(keyX == zombies[i][0] && keyY == zombies[i][1]));

					do
					{
						weaponX = randomCoordinates(colSize);
						weaponY = randomCoordinates(rowSize);
					}
					while ((weaponX == playerX && weaponY == playerY) ||
							(weaponX == keyX && weaponY == keyY) ||
							(weaponX == foodX && weaponY == foodY) ||
							(weaponX == exitX && weaponY == exitY) ||
							(weaponX == zombies[i][0]) && weaponY == zombies[i][1]);
					do
					{
						foodX = randomCoordinates(colSize);
						foodY = randomCoordinates(rowSize);
					}
					while ((foodX == playerX && foodY == playerY) ||
							(foodX == keyX && foodY == keyY) ||
							(foodX == weaponX && foodY == weaponY) ||
							(foodX == exitX && foodY == exitY) ||
							(foodX == zombies[i][0] && foodY == zombies[i][1])); //These do-while loops prevent the possibility of these random objects occupying the same tile as other instances in the game
				}

 	while (gameOver == false)
		{
		//Draw Game Scene
		for(int y = 0; y < rowSize; y++) //Start outer for-loop (rows)
			{
				for(int x = 0; x < colSize; x++) //Start inner for-loop (columns)
					{
						if (x == playerX && y == playerY) //If grid coord is player position
						{
							System.out.print(playerTile); //Print player tile
						}
						else if (x == exitX && y == exitY) //If grid coord is exit position
							{
								System.out.print(exitTile); //Print exit tile
							}
						else if (zombieLocation(x, y, zombies, zombieStatus)) //If grid coord is zombie position
							{
								System.out.print(zombieTile); //Print zombie tile
							}
						else if (x == keyX && y == keyY) //If grid coord is key position
							{
								System.out.print(keyTile); //Print key tile
							}
						else if (x == weaponX && y == weaponY) //If grid coord is weapon position
							{
								System.out.print(weaponTile); //Print weapon tile
							}
						else if (x == foodX && y == foodY) //If grid coord is food position
							{
								System.out.print(foodTile); //Print food tile
							}
						else						
							{
								System.out.print(floorTile); //Draw floor tile
							} //End inner for-loop (x-axis)
					}
				System.out.print("\n"); //New line for the next row
			} //End outer for-loop (y-axis)

	//Get Player Input
	String choice = input.nextLine();

	//Execute Player Action
	if (choice.equals("w"))
		{
			playerY--;
			System.out.println("Your hunger is now at " + foodCounter + ".");
		}
	else if (choice.equals("s"))
		{
			playerY++;
			System.out.println("Your hunger is now at " + foodCounter + ".");
		}
	else if (choice.equals("d"))
		{
			playerX++;
			System.out.println("Your hunger is now at " + foodCounter + ".");
		}
	else if (choice.equals("a"))
		{
			playerX--;
			System.out.println("Your hunger is now at " + foodCounter + "."); //Have the player move with 'wasd' and display their foodCounter
		}

	//Check Key Pickup
	if (playerX == keyX && playerY == keyY && keyPickup == false) //Checking if keyPickup is set to false and moving the key off the grid completely prevents anymore interactions once the key has been picked up by the player
		{
			keyPickup = true;
			keyX = colSize + 1; //Put the key off the grid
			keyY = rowSize + 1; //Put the key off the grid
			System.out.println("Picked up the key. Hurry to the exit!");
		}

	//Check Weapon Pickup
	if (playerX == weaponX && playerY == weaponY && weaponPickup == false) //Checking if weaponPickup is set to false and moving the weapon off the grid completely prevents anymore interactions once the weapon has been picked up by the player
		{
			weaponPickup = true;
			weaponX = colSize + 1; //Put the weapon off the grid
			weaponY = rowSize + 1; //Put the weapon off the grid
			System.out.println("Picked up the knife.");
		}

	//Check Food Pickup
	if (playerX == foodX && playerY == foodY)
		{
			foodCounter += 5;
			foodX = colSize + 1;
			foodY = rowSize + 1; // Move the food off the grid once the player has eaten it
			System.out.println("Picked up the food on the ground. You feel less hungry.");
		}

	//Check food counter with player movement
	foodCounter--; //Decrease the food counter by 1 for each valid step the player takes
	if (foodCounter <= 0)
		{
			gameOver = true; //Adds another way to lose the game by having foodCounter reach 0
			System.out.println("You starved to death...");
		}

	//Check Win Condition
	//Lock the door if the player doesn't have a key
	if (playerX == exitX && playerY == exitY)
		{
			if (keyPickup == false) //Prevent the player from advancing until keyPickup is true
				{
					System.out.println("The door is locked. Find a key.");
				}
			//Player fully wins the game	
			else if (keyPickup == true && level == 3 && foodCounter > 0) //Added the foodCounter > 0 condition because while testing, there was a possibility of winning and losing the game at the same time.
				{
					gameOver = true;
					System.out.println("You survived and made it to the exit!");
				}
			else //Player moves onto the next level
				{
					level++;
					System.out.println("You made it to the next level. Keep going!");
					if (weaponPickup == true)
						{
							System.out.println("You dropped your knife as you went through the door."); //Introduced a weapon dropping mechanic to make the game more difficult and interesting.
						}

					//Advance to the next level and create more zombies for each level
					//Give the zombies fixed positions so the game is not impossible
					if (level == 2)
						{
							colSize = 12;
							rowSize = 12;
							zombieStatus[1] = true;
							zombieStatus[2] = true;
							zombies[1][0] = 3;
							zombies[1][1] = 10;
							zombies[2][0] = 7;
							zombies[2][1] = 2;
						}
					else if (level == 3)
						{
							colSize = 16;
							rowSize = 16;
							zombieStatus[3] = true;
							zombieStatus[4] = true;
							zombieStatus[5] = true;
							zombies[3][0] = 11;
							zombies[3][1] = 5;
							zombies[4][0] = 9;
							zombies[4][1] = 10;
							zombies[5][0] = 13;
							zombies[5][1] = 12;
						}

					//Reset player, enemies, and objects for each level
					playerX = 0; 
					playerY = 0;
					exitX = colSize - 1;
					exitY = rowSize - 1;
					keyPickup = false;
					weaponPickup = false;
					for (int i = 0; i < zombies.length; i++)

							{
								do
								{	
									keyX = randomCoordinates(colSize); //Only repeated code due to ensuring the reset is properly conveyed at all instances
									keyY = randomCoordinates(rowSize);
								}

								while ((keyX == playerX && keyY == playerY) ||
										(keyX == weaponX && keyY == weaponY) ||
										(keyX == foodX && keyY == foodY) ||
										(keyX == exitX && keyY == exitY) ||
										(keyX == zombies[i][0] && keyY == zombies[i][1]));

								do
								{
									weaponX = randomCoordinates(colSize);
									weaponY = randomCoordinates(rowSize);
								}
								while ((weaponX == playerX && weaponY == playerY) ||
										(weaponX == keyX && weaponY == keyY) ||
										(weaponX == foodX && weaponY == foodY) ||
										(weaponX == exitX && weaponY == exitY) ||
										(weaponX == zombies[i][0]) && weaponY == zombies[i][1]);
								do
								{
									foodX = randomCoordinates(colSize);
									foodY = randomCoordinates(rowSize);
								}
								while ((foodX == playerX && foodY == playerY) ||
										(foodX == keyX && foodY == keyY) ||
										(foodX == weaponX && foodY == weaponY) ||
										(foodX == exitX && foodY == exitY) ||
										(foodX == zombies[i][0] && foodY == zombies[i][1]));
				}
		}
	}

	//Execute Zombie Movement
	for (int i = 0; i < zombies.length; i++)
		{
			if (zombieStatus[i])
				{
					zombieMovement(zombies[i], colSize, rowSize); //Calls upon the class in the main method
				}
		}

	//Check Lose Condition
	//Check Zombie Killing Condition
	//Check Zombie Resource Depletion Condition
	for (int i = 0; i < zombies.length; i++)
		{
			if (zombies[i][0] == playerX && zombies[i][1] == playerY && weaponPickup == false)
			{
				if (Math.random() < 0.5) //Provides a 50% chance of the player instantly dying from the zombie
					{
						gameOver = true;
						System.out.println("Your brains were eaten by the zombie...");
					}
				else
					{
						foodCounter -= 10; //Provides a 50% chance of the player losing 10 points from their food counter
						System.out.println("You were attacked by the zombie! Your food counter is now " + foodCounter);
						if (foodCounter <= 0) //Accounts for possibility of foodCounter going past 0
							{
							gameOver = true;
							System.out.println("You starved to death...");
							}
					}
			} 
		else if (zombies[i][0] == playerX && zombies[i][1] == playerY && weaponPickup == true) //The player has the knife and interacts with the zombie
			{
				zombieStatus[i] = false; //Zombie is dead
				weaponPickup = false; //Weapon breaks after killing a zombie
				zombies[i][0] = colSize + 1;
				zombies[i][1] = rowSize + 1;				
				System.out.println("You killed the zombie.");
				System.out.println("The knife broke.");
			}
		else if (zombies[i][0] == weaponX && zombies[i][1] == weaponY && weaponPickup == false) //The zombie interacts with the weapon on the ground
			{
				weaponX = colSize + 2; //2 instead of 1 so it doesn't infinitely loop when the zombie is also repositioned
				weaponY = rowSize + 2;
				System.out.println("The zombie destroyed the knife!");
			}
		else if (zombies[i][0] == foodX && zombies[i][1] == foodY) //The zombie interacts with the food on the ground
			{
				foodX = colSize + 2; //2 instead of 1 so it doesn't infinitely loop when the zombie is also repositioned
				foodY = rowSize + 2;
				System.out.println("The zombie ate the food!");
			}	
		}

	//Check Player Constraints and ensure player cannot move out of bounds
	playerX = Math.max(0, Math.min(playerX, colSize - 1)); //Provides a range of 0 to 1 less than the grid total for playerX
	playerY = Math.max(0, Math.min(playerY, rowSize - 1)); //Provides a range of 0 to 1 less than the grid total for playerY
		}
	}

	//Random generation coordinates logic for several objects
	public static int randomCoordinates(int size)
		{
			return (int) (Math.random() * (size - 1)); //Move 1 less than the total declared size for colSize and rowSize to avoid the edges, and take a random value
		}

	//Check zombies' coordinates
	public static boolean zombieLocation(int x, int y, int[][] zombies, boolean[] zombieStatus)
		{
			for (int i = 0; i < zombies.length; i++) //Take the array of zombies, which is the individual zombie and the x and y coordinates, i is the index for the array
				{
					if (zombies[i][0] == x && zombies[i][1] == y && zombieStatus[i])
						{
							return true; //Allows true for any added zombie that falls within the above perimeters
						}
				}
			return false;
		}

	//Zombie Movement
	//Moved to a class to make it easier to apply movement logic as more zombies are added
	public static void zombieMovement(int[] zombie, int colSize, int rowSize)
		{
			int movement = (int) (Math.random() * 4);
			switch (movement)
				{
				case 0: //Zombie moves right
					zombie[0] = (zombie[0] + 1) % colSize; //% modulus operator checks if colSize - 1 is true, then it wraps it back to 0
					break;
				case 1: //Zombie moves left
					zombie[0] = --zombie[0] >= 0 ? zombie[0] : (colSize - 1); //ternary operator to check if zombie[0] is < 0, then go to colSize - 1 to wrap around
					break;
				case 2: //Zombie moves up
					zombie[1] = --zombie[1] >= 0 ? zombie[1] : (rowSize - 1); //ternary operator to check if zombie[1] is < (rowSize - 1), then go to 0
					break;
				case 3: //Zombie moves down
					zombie[1] = (zombie[1] + 1) % rowSize; ////% modulus operator checks if rowSize - 1 is true, then it wraps it back to 0
					break; //Adds a bit of difficulty and awareness as the player needs to watch for zombies wrapping around the grid
				}
		}
}

/* Pseudocode
Setup Game Data
Main Game Loop
1. Draw Game Scene
	for the number of rows
		for the number of columns
			if this grid coordinate is player's position
				print player tile
			else if thus grid coordinate is exit's position
				print exit tile
			else
				print floor tile
		terminate row
2. Get Player Input
3. Execute Player Action
4. Check Win Condition
	if player coordinates and exit coordinate are the same
		set game over to true
		print victory message to the player
5. Execute Monster Action
6. Check Lost Condition

Add Addtional Features
1. Constrain player to the level
	Check player X is within 0 and the max number for columns
	Check player Y is within 0 and the max number rows
2. Randomly placed key to unlock the level exit
	keyX, keyY
	do
		Convert key X to a random integer within the range of columns
		Convert key Y to a random integer within the range of rows
	while
		key X and y does not overlap any other object
	Let player pick up key
		Check player X is equal to key X and player Y is equal to key Y
			Player picked up the key
			Key is moved off of the grid
	Lock exit door unless the player has the key
		if player picks up the key
			print victory message to the player
		else
			print locked door message to the player
3. Multiple levels
	Declare level as 1 and advance to 2 more levels to win.
		level++ to advance to the next level

4. Weapon
	Add a knife feature in that allows the player to fight back
	weaponPickup = true or false to check if the player has the knife
	weaponPickup == true && playerXY == zombie[i][0][1], if all three variables aligned then the player can kill the zombie
5. More Zombies
	Add 2 more zombies in level 2 and 3 more zombies in level 3
	Store zombie logic in arrays and public classes and methods so that adjustments for game balance are easily achievable

6. Smarter Zombies
	Track the player? (unbalanced in future levels)
	Special attack moves that the zombie can do (feels a bit unbalanced since the player only has a knife)
	Introduce a counter where the zombie can destroy resources over time?
	(introduces complex logic for possible edge case scenarios and the counter clutters other counters in the game)
	
	Zombie instantly destroys food and weapons when contact is made
		This provides motivation for the player to kill the zombie if given the change or run away quickly	

7. Food Counter
	Add food on the ground that the player can pick up for += 10;
	foodCounter easily adjustable for game balance
	Based on rubric, allow a 50/50 chance for zombie player interaction for the player to lose -= 10 from foodCoutner or die instantly
	Add another method to lose the game if foodcounter <= 0

*/