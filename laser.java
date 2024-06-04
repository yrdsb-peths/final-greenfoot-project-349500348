import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class laser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Laser extends Actor
{
    private GreenfootImage image;

    /**
     * Act - do whatever the laser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Laser()
    {
        image = getImage();
        resize();
    }
    public void resize()
    {
        GreenfootImage img = new GreenfootImage("laser.png");
        img.scale(img.getWidth()/4, img.getHeight()/4);
        setImage(img);
    }
    
    private int speed = 5;
    public void act()
    {
        // Move object at selected speed
        move(speed);
        // Remove object when off screen
        if (isAtEdge())
        {
            getWorld().removeObject(this);
        }
        else 
        {
            kill();
        }
    }
    public void kill()
    {
        if(isTouching(Enemy.class))
            {
                removeTouching(Enemy.class);
                getWorld().removeObject(this);
            }
    }
}