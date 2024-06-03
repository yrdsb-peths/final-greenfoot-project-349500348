import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    private int enemySpawnTimer = 0; 

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        Hero hero = new Hero();
        addObject(hero, 70, 300);
    }
    
    public void act()
    {
        // Increment the spawn timer
        enemySpawnTimer++;
        
        // Check if it's time to spawn an enemy (every 120 frames, assuming 60 fps = 2 seconds)
        if (enemySpawnTimer >= 120)
        {
            spawnEnemy();
            enemySpawnTimer = 0; // Reset the timer
        }
    }
    
    public void spawnEnemy()
    {
        Enemy enemy = new Enemy();
        
        // Add the enemy to the right side of the screen at a random y position
        int x = getWidth() - 1; // Rightmost position
        int y = Greenfoot.getRandomNumber(getHeight());
        
        addObject(enemy, x, y);
    }
}
