import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Hero here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hero extends Actor
{
    private GreenfootImage image;
    
    public Hero()
    {
        image = getImage();
        resize();
    }
    public void resize()
    {
        GreenfootImage img = new GreenfootImage("spaceship_idle.png");
        img.scale(img.getWidth()/12, img.getHeight()/12);
        setImage(img);
    }
    /**
     * Act - do whatever the Hero wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.isKeyDown("space"))
        {
            shoot();
        }
        if(Greenfoot.isKeyDown("up"))
        {
            setLocation(getX(), getY() - 3);
        }
        else if(Greenfoot.isKeyDown("down"))
        {
            setLocation(getX(), getY() + 3);
        }
    }
    
    public void shoot()
    {

        // Creates a Laser object
        Laser laser = new Laser();
        
        // Gets world ship is in
        World world = getWorld();
        
        // Set location of laser to tip of the ship
        int x = getX() + (image.getWidth()/12) / 2;
        int y = getY();
        
        // Add laser object at selected position
        world.addObject(laser, x, y);
    }
}
