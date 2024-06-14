import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Title Screen with instructions, waits for user input to start game
 * Inherits from World class.
 * @author Ostin H.
 * @version 06/14/24
 */
public class TitleScreen extends World
{
    Label[] labels = {new Label("Space Shooter", 60), new Label("Press Enter to Begin", 30), new Label("\u2191: Move Up\n\u2193: Move Down\nSpace: Shoot", 30)}; //Array for labels 0: title label, 1: start label, 2: control label
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 

        for (int i = 0; i < labels.length; i++) 
        {
            addObject(labels[i], getWidth() / 2, getHeight() / 2 + 50 * i);
        }
        prepare();
    }

    /**
     * Check for user input to start the game.
     */
    public void act()
    {
        if(Greenfoot.isKeyDown("enter"))
        {
            MyWorld gameWorld = new MyWorld();
            Greenfoot.setWorld(gameWorld);
        }
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Hero hero = new Hero();
    }
}
