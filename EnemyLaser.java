import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy_Laser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyLaser extends Actor
{
    /**
     * Act - do whatever the Enemy_Laser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage image;

    /**
     * Act - do whatever the laser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public EnemyLaser()
    {
        setRotation(180);
        image = getImage();
        resize();
    }
    public void resize()
    {
        GreenfootImage img = new GreenfootImage("enemy_laser.png");
        img.scale(img.getWidth()/4, img.getHeight()/4);
        setImage(img);
    }
    
    private int speed = 4;
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
        if(isTouching(Hero.class))
            {
                removeTouching(Hero.class);
                getWorld().removeObject(this);
            }
    }
}
