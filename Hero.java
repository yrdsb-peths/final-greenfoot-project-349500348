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
    private int shootTimer;
    private final int shootSpeed = 20;
    private final int speed = 4;
    
    public Hero()
    {
        image = getImage();
        resize();
        shootTimer = 0; 
    }
    public void resize()
    {
        GreenfootImage img = new GreenfootImage("spaceship_idle.png");
        img.scale(img.getWidth()/15, img.getHeight()/15);
        setImage(img);
    }
    /**
     * Act - do whatever the Hero wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (shootTimer > 0)
        {
            shootTimer--;
        }
        if (Greenfoot.isKeyDown("space") && shootTimer == 0)
        {
            shoot();
            shootTimer = shootSpeed;
        }
        if(Greenfoot.isKeyDown("up"))
        {
            setLocation(getX(), getY() - speed);
        }
        else if(Greenfoot.isKeyDown("down"))
        {
            setLocation(getX(), getY() + speed);
        }
    }
    
    public void shoot()
    {

        // Creates a Laser object
        Laser laser = new Laser();
        
        // Gets world ship is in
        World world = getWorld();
        
        // Set location of laser to tip of the ship
        int x = getX() + (image.getWidth()/15) / 2;
        int y = getY();
        
        // Add laser object at selected position
        world.addObject(laser, x, y);
    }
}
