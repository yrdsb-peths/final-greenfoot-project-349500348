import greenfoot.*;  // import necessary classes from Greenfoot package

/**
 * a shield power-up that moves left towards the hero, protects hero from one hit
 * Inherits from the Actor class.
 * 
 * @author Ostin H.
 * @version 06/14/2024
 */
public class Shield extends Actor
{
    /**
     * Act - do whatever the Shield wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Shield()
    {
        resize(); // resize the shield
    }
    
    public void act()
    {
        setLocation(getX() - 2, getY()); // move the shield to the left
        if (isAtEdge()) // check if the object is off the left edge of the screen
        {
            getWorld().removeObject(this); // remove the object from the world
        }
    }
    
    /**
     * Resize the shield image.
     */
    public void resize()
    {
        GreenfootImage img = new GreenfootImage("shield.png"); // load the image
        img.scale(img.getWidth() / 35, img.getHeight() / 35); // scale down the image
        setImage(img); // set the scaled image as the image
    }
}
