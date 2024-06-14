import greenfoot.*; 

/**
 * a laser shot from the Hero spaceship and destroys enemy objects on contact.
 * Inherits from the Actor class.
 * 
 * @author Ostin H.
 * @version 06/14/2024
 */
public class Laser extends Actor
{
    private final int speed = 5; // speed at which the laser moves
    GreenfootSound destroySound = new GreenfootSound("explosion.mp3");
    public Laser()
    {
        resize(); // resize the laser image
    }
    
    /**
     * Resize the laser image.
     */
    public void resize()
    {
        GreenfootImage img = new GreenfootImage("laser.png"); // load the laser image
        img.scale(img.getWidth() / 4, img.getHeight() / 4); // scale down the image
        setImage(img); // set the scaled image as the laser's image
    }
    
    /**
     * Act - do whatever the Laser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        move(speed); // move the laser forward at the set speed
        if (isAtEdge()) // check if the laser is at the edge of the screen
        {
            getWorld().removeObject(this); // remove the laser from the world if it is at the edge
        }
        else 
        {
            kill(); // check for collisions with enemies
        }
    }
    
    /**
     * Check for collisions with enemies and remove them.
     */
    public void kill()
    {
        if (isTouching(Enemy.class)) // check if the laser is touching an enemy
        {
            destroySound.play();
            MyWorld world = (MyWorld) getWorld();
            world.increaseScore();
            removeTouching(Enemy.class); // remove the enemy that the laser is touching
            getWorld().removeObject(this); // remove the laser from the world
        }
    }
}
