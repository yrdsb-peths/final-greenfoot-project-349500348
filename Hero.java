import greenfoot.*;  

/**
 * Write a description of class Hero here.
 * 
 * @author Ostin H
 * @version 06/10/2024
 */ 
public class Hero extends Actor
{
    private int shootTimer; // variable to count frames between shots
    private final int shootSpeed = 20; // number of frames before Hero can shoot again
    private final int speed = 4; // number of pixels the Hero moves per frame while key is pressed
    
    public Hero()
    {
        resize(); // resize the Hero's image 
        shootTimer = 0; // initializes the shoot timer to 0
    }
    
    public void resize()
    {
        GreenfootImage img = new GreenfootImage("spaceship_idle.png"); // load the spaceship image
        img.scale(img.getWidth() / 15, img.getHeight() / 15); // scale down the image
        setImage(img); // set the scaled image as the Hero's image
    }
    
    /**
     * Act - do whatever the Hero wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (shootTimer > 0) // check if the shoot timer is greater than 0
        {
            shootTimer--; // decrement the shoot timer
        }
        if (Greenfoot.isKeyDown("space") && shootTimer == 0) // check if space key is pressed and shoot timer is 0
        {
            shoot(); // call the shoot method
            shootTimer = shootSpeed; // reset the shoot timer
        }
        if (Greenfoot.isKeyDown("up")) // check if the up key is pressed
        {
            setLocation(getX(), getY() - speed); // move the Hero up
        }
        else if (Greenfoot.isKeyDown("down")) // check if the down key is pressed
        {
            setLocation(getX(), getY() + speed); // move the Hero down
        }
        checkCollision();
    }
    
    public void shoot()
    {
        Laser laser = new Laser(); // creates a Laser object
        
        World world = getWorld(); // gets the world the Hero is in
        
        // calculates the location to place the laser at the tip of the ship
        GreenfootImage image = getImage(); // gets the current image of the Hero
        int x = getX() + (image.getWidth() / 15) / 2;
        int y = getY();
        
        world.addObject(laser, x, y); // adds the laser object to the world at the calculated position
    }
    private void checkCollision()
    {
        // Check for collision with Enemy
        if (isTouching(Enemy.class))
        {
            removeTouching(Enemy.class); // remove the enemy that the hero is touching
            MyWorld world = (MyWorld) getWorld(); // get the world the hero is in
            world.gameOver(); // call the game over method
            world.gameOver = true; // set the gameOver flag to true
            getWorld().removeObject(this); // remove the hero from the world
        }
    }
}