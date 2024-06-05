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
    private GreenfootImage image;
    private int speed = 2;
    private int shootTimer;
    private final int shootSpeed = 150;
    
    public Enemy()
    {
        // rotate image to face hero
        image = getImage();
        setRotation(90);
        resize();
        shootTimer = 0;
    }
    
    public void act()
    {
        setLocation(getX() - speed, getY());
        if (shootTimer > 0)
        {
            shootTimer--;
        }
        if (shootTimer == 0)
        {
            shoot();
            shootTimer = shootSpeed;
        }
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
    
    public void shoot()
    {

        // Creates a Laser object
        EnemyLaser enemyLaser = new EnemyLaser();
        
        // Gets world ship is in
        World world = getWorld();
        
        // Set location of laser to tip of the ship
        int x = getX() + (image.getWidth()/4) / 2;
        int y = getY();
        
        // Add laser object at selected position
        world.addObject(enemyLaser, x, y);
    }
}
