// import the Greenfoot package

import greenfoot.*;

/**
 * Player-controlled character
 * Hero can move up and down, shoot lasers, and collect shields
 * Inherits from Actor class
 * 
 * @author Ostin H.
 * @version 06/14/2024
 */ 
public class Hero extends Actor
{
    private int shootTimer; // variable to count frames between shots
    private final int shootSpeed = 40; // number of frames before Hero can shoot again
    private final int speed = 4; // number of pixels the Hero moves per frame while key is pressed
    private boolean hasShield = false; // indicates if the Hero has a shield
    private boolean removedFromWorld = false; // indicates if the Hero has been removed from the world
    GreenfootSound destroySound = new GreenfootSound("explosion.mp3");
    GreenfootSound shootSound = new GreenfootSound("laserzap.mp3");

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
        MyWorld world = (MyWorld) getWorld(); // get the world the Hero is in
        if(!world.gameOver) // check if the game is not over
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
            checkCollision(); // check for collisions
        }
    }
    
    public void shoot()
    {
        shootSound.play();

        Laser laser = new Laser(); // creates a Laser object
        
        MyWorld world = (MyWorld) getWorld(); // gets the world the Hero is in
        
        // calculates the location to place the laser at the tip of the ship
        GreenfootImage image = getImage(); // gets the current image of the Hero
        int x = getX() + (image.getWidth() / 15) / 2;
        int y = getY();
        
        world.addObject(laser, x, y); // adds the laser object to the world at the calculated position
    }
    
    private void checkCollision()
    {
        //check for collision with shield
        if (isTouching(Shield.class)) 
        {
            collectShield(); // collect the shield
        }
        // Check for collision with Enemy Laser
        if(isTouching(EnemyLaser.class))
        {
            if (hasShield) 
            {
                destroySound.play();
                removeTouching(EnemyLaser.class); // remove enemy laser
                removeShield(); // remove shield
            }
            else
            {
                destroySound.play();
                removeTouching(EnemyLaser.class); // remove enemy laser
                MyWorld world = (MyWorld) getWorld(); // get the world the Hero is in
                world.gameOver = true; // set the gameOver flag to true
                world.gameOver(); // call the game over method
                getWorld().removeObject(this); // remove the Hero from the world
            }
        }
        if(getWorld() != null)
        {
            // Check for collision with Enemy
            if (isTouching(Enemy.class))
            {
                if (hasShield) //check for shield
                {
                    destroySound.play();
                    removeTouching(Enemy.class); // remove the enemy
                    removeShield(); // remove shield
                }
                else
                {
                    destroySound.play();
                    removeTouching(Enemy.class); // remove the enemy
                    MyWorld world = (MyWorld) getWorld(); // get the world the Hero is in
                    world.gameOver = true; // set the gameOver flag to true
                    world.gameOver(); // call the game over method
                    getWorld().removeObject(this); // remove the Hero from the world
                }
                
            }
        }
    }
    
    private void collectShield() 
    {
        hasShield = true; // set the shield flag to true
        setImage("spaceship_shield.png"); // change the Hero's image to show the shield
        removeTouching(Shield.class); // Remove the shield power-up
    }

    private void removeShield() 
    {
        hasShield = false; // set the shield flag to false
        resize(); // resize the Hero's image to remove the shield
    }
}
