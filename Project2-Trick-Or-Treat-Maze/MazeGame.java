//Amber Williams and Dom Ketchens
//Project 4: Maze Game

import java.util.ArrayList;

public class MazeGame{
    private static boolean gameOver; // checks if game's over
    private static Music backgroundMusic; //Play background music
    private static Music levelExit; // Play sfx for exiting a level
    private static ArrayList<Ghost> ghosts = new ArrayList<>();

        //current game level
        public static int level;

        //Start game algorithm
        public static void start(){
            gameOver = false;
            level = 0;
            World.start();
            Scene.start(level);
            // starting player positions
            Player.start(1,1);
            //Instantiate and start Ghost objects
            Ghost ghost1 = new Ghost();
            Ghost.startRandom();
            ghosts.add(ghost1); //Add ghost to the list

        //Initialize and play background music
        backgroundMusic = new Music("Assets/background-music.wav");
        backgroundMusic.playLoop();

        //Play sfx with level advancement
        levelExit = new Music("Assets/level-exit-sfx.wav");
        }

        //Update game logic
        public static void update(){
            Player.update(); // update player position
            //Update each ghost's position by looping through the lists of ghosts
            for (Ghost ghost : ghosts)
                {
                    Ghost.update(); // update ghost position from the non-static update method
                }

            Scene.pickupLogic(level); //Pass logic for key and gem
            Scene.foodCounterCheck(); //Pass foodcounter logic

            // check if player reaches exit and has the key
            if (Player.getX() == Exit.getX() && Player.getY() == Exit.getY() && Scene.hasKey() && Scene.hasGem()) {
                System.out.println("Current level: " + level); //Debug message to ensure player is on the right level
                level++;
                System.out.println("Player advanced to level: " + level); //Debug message for level type
                levelExit.play();

                // if player reached the last last, end the game
                if (level == World.getLength()) {
                    gameOver = true;
                }
                else {
                    Scene.start(level); // start new level scene
                    Ghost.startRandom(); //randomize the ghost
                }
            }

            // checks if ghost catches player
            for (Ghost ghost : ghosts){
            if (Player.getX() == Ghost.getX() && Player.getY() == Ghost.getY()) {
                    {
                        gameOver = true; // ends the game if true
                    }
                break; //Stop checking the other ghosts once the game ends
            }
            }

            //Check if food counter is 0
            if (Player.getFoodCounter() <= 0){
                gameOver = true;
            }
        }

        // renders (draw)
        public static void render(){
            Scene.draw(); // draw scene
            Exit.draw(); // draw exit
            Player.draw(); // draw player

            //Draw all ghosts
            for (Ghost ghost : ghosts){
                Ghost.draw(); // draw ghost
                }
            StdDraw.show(100);
        }

        // main game loop
        public static void main(String[] args){
            start(); // starts game
            while (gameOver == false) // loops while game isnt over
                {
                    update(); // updates logic
                    render(); // renders updated game
                }

        //Stop the background music when the game ends
        if (backgroundMusic != null)
            {
                backgroundMusic.stop();
            }

        }

    //Allow for more game over methods to be referenced
    public static void setGameOver(boolean status)
        {
            gameOver = status;
        }
}