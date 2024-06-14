import greenfoot.*;  // import necessary classes from Greenfoot package

/**
 * Write a description of class Enemy here.
 * 
 * @author Ostin H.
 * @version 06/14/2024
 */
public class Enemy extends Actor
{
    private int speed = 2; // speed at which the enemy moves
    private int shootTimer; // variable to count frames between shots
    private final int shootSpeed = 150; // number of frames before enemy can shoot again

    public Enemy()
    {
        setRotation(90); // rotate image to face hero
        resize(); // resize the enemy image
        shootTimer = 0; // initializes the shoot timer to 0
    }
    
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setLocation(getX() - speed, getY()); // move the enemy leftward
        if (shootTimer > 0) // check if the shoot timer is greater than 0
        {
            shootTimer--; // decrement the shoot timer
        }
        if (shootTimer == 0) // check if the shoot timer is 0
        {
            shoot(); // call the shoot method
            shootTimer = shootSpeed; // reset the shoot timer
        }
        // Remove object when off screen to reduce lag
        if (getX() == 0) // check if the enemy is off the left edge of the screen
        {
            getWorld().removeObject(this); // remove the enemy from the world
        }
    }
    
    /**
     * Resize the enemy image.
     */
    public void resize()
    {
        GreenfootImage img = new GreenfootImage("enemyspaceship_idle.png"); // load the enemy spaceship image
        img.scale(img.getWidth() / 4, img.getHeight() / 4); // scale down the image
        setImage(img); // set the scaled image as the enemy's image
    }
    
    /**
     * Shoot a laser from the enemy.
     */
    public void shoot()
    {
        // Creates an EnemyLaser object
        EnemyLaser enemyLaser = new EnemyLaser();
        
        // Gets the world the enemy is in
        World world = getWorld();
        
        // Set location of laser to tip of the ship
        int x = getX() + (getImage().getWidth() / 4) / 2;
        int y = getY();
        
        // Add laser object at selected position
        world.addObject(enemyLaser, x, y);
    }
}
