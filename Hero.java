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
        img.scale(img.getWidth()/10, img.getHeight()/10);
        setImage(img);
    }
    /**
     * Act - do whatever the Hero wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(Greenfoot.isKeyDown("up"))
        {
            setLocation(getX(), getY() + 1);
        }
        else if(Greenfoot.isKeyDown("down"))
        {
            setLocation(getX(), getY() - 1);
        }
    }
}
