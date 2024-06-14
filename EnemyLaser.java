import greenfoot.*;  // import necessary classes from Greenfoot package

/**
 * Write a description of class EnemyLaser here.
 * 
 * @author Ostin H.
 * @version 06/14/2024
 */
public class EnemyLaser extends Actor
{
    private final int speed = 4; // speed at which the enemy laser moves

    public EnemyLaser()
    {
        setRotation(180); // set the rotation of the laser to face downward
        resize(); // resize the enemy laser image
    }
    
    /**
     * Resize the enemy laser image.
     */
    public void resize()
    {
        GreenfootImage img = new GreenfootImage("enemy_laser.png"); // load the enemy laser image
        img.scale(img.getWidth() / 4, img.getHeight() / 4); // scale down the image
        setImage(img); // set the scaled image as the enemy laser's image
    }
    
    /**
     * Act - do whatever the EnemyLaser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        move(speed); // move the enemy laser forward at the set speed
        if (isAtEdge()) // check if the laser is at the edge of the screen
        {
            getWorld().removeObject(this); // remove the laser from the world if it is at the edge
        }
    }
}
