import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Actor
{
    /**
     * Act - do whatever the enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int speed = 2;
    
    public Enemy()
    {
        // rotate image to face hero
        setRotation(90);
        resize();
    }
    
    public void act()
    {
        setLocation(getX() - speed, getY());
        
        // Remove object when off screen to reduce lag
        if (getX() == 0)
        {
            getWorld().removeObject(this);
        }
    }
    
    public void resize()
    {
        GreenfootImage img = new GreenfootImage("enemyspaceship_idle.png");
        img.scale(img.getWidth()/4, img.getHeight()/4);
        setImage(img);
    }
    
}
