//Amber and Dom Ketchens
//Project 4: Maze Game - Bullet

public class Bullet{
    private int x;
    private int y;
    private String direction; // wasd keys
    private boolean active;

    public Bullet(int startX, int startY, String direction){
        this.x = startX;
        this.y = startY;
        this.direction = direction;
        this.active = true;
    }

    public void move() {
    	if (!active) return;

    	switch(direction){
            case "up": y--;
            	break;
            case "down": y++;
            	break;
            case "left": x--;
            	break;
            case "right": x++;
            	break;
    	}

    //Check if bullet goes out of boudns of collides with walls
    if (!Scene.canMove(x, y)){
    	active = false; //end the bullet if it hits the wall or goes out of bounds
    	}
    }

       public void draw() {
        if (active) {
            int tileX = x * Player.TILE_SIZE + Player.TILE_SIZE / 2;
            int tileY = y * Player.TILE_SIZE + Player.TILE_SIZE / 2;
            StdDraw.picture(tileX, tileY, "Assets/bullet.png");
        }
    }

    public boolean isActive() {
        return active;
    }

    public boolean collidesWith(Ghost ghost) {
        return active && ghost.getX() == x && ghost.getY() == y;
    }

    public void deactivate() {
        active = false;
    }

}