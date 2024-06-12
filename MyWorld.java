import greenfoot.*;  

/**
 * Write a description of class MyWorld here.
 * 
 * @author Ostin H
 * @version 06/12/2024
 */
public class MyWorld extends World
{
    private int enemySpawnTimer = 0; // variable to count frames for enemy spawning
    public boolean gameOver = false; // flag to indicate if the game is over
    public int score = 0;
    Label scoreLabel = new Label(0, 80);
    Label levelLabel = new Label("Level 1", 80);
    public int spawnInterval = 180;
    int level = 1; // level increases enemy spawn rate and bullet speed
    private int levelUpMessageTimer = 0; 
    private Label levelUpLabel;
    private int skipCooldown = 0;
    /**
     * Constructor for objects of class MyWorld.
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        
        // Create a new Hero object and add it to the world
        Hero hero = new Hero();
        addObject(hero, 70, 300);
        addObject(scoreLabel, getWidth()/2, 30);
        addObject(levelLabel, 105, 30);
    }
    
    /**
     * Act - do whatever the MyWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (!gameOver) // check if the game is not over
        {
            enemySpawnTimer++; // increment the enemy spawn timer
            
            // Check if it's time to spawn an enemy (every 120 frames, assuming 60 fps = 2 seconds)
            if (enemySpawnTimer >= spawnInterval)
            {
                spawnEnemy(); // call the spawnEnemy method
                enemySpawnTimer = 0; // reset the timer
            }
            if (levelUpMessageTimer > 0)
            {
                levelUpMessageTimer--;
                if (levelUpMessageTimer == 0)
                {
                    removeObject(levelUpLabel); // Remove the "Level Up!" label after timer ends
                }
            }
            if (skipCooldown > 0) 
            {
                skipCooldown--;
            }

            if (Greenfoot.isKeyDown("p") && skipCooldown == 60) 
            {
                levelUp(); // Cheat key action: Level up
                skipCooldown = 65; // Reset cooldown
            }
        }
    }
    public void levelUp() 
    {
        level++;
        levelLabel.setValue("Level " + level); // Update the level label
        updateSpawnInterval(); // Update the spawn interval based on the new level
        showLevelUpMessage(); // Show "Level Up!" message
    }
    public void increaseScore()
    {
        score += 50; // Increase score by 50
        scoreLabel.setValue(score); // Update the score label
        
        // Check if the score is a multiple of 500 to level up
        if (score % 500 == 0)
        {
            level++;
            levelLabel.setValue("Level " + level); // Update the level label
            updateSpawnInterval(); // Update the spawn interval based on the new level
            showLevelUpMessage();
        }
    }
    private void updateSpawnInterval()
    {
        // Decrease the spawn interval as the level increases, minimum of 30
        spawnInterval = Math.max(180 - (level - 1) * 10, 30);
    }
    public void gameOver()
    {
        Label gameOverLabel = new Label("Game Over", 100); // create a Label object with "Game Over" text
        addObject(gameOverLabel, 300, 200); // add the Label object to the world at position (300, 200)
    }
    
    public void spawnEnemy()
    {
        Enemy enemy = new Enemy(); // create a new Enemy object
        
        // Add the enemy to the right side of the screen at a random y position
        int x = getWidth() - 1; // Rightmost position
        int y = Greenfoot.getRandomNumber(getHeight()); // Random y position
        
        addObject(enemy, x, y); // add the enemy to the world at the calculated position
    }
    private void showLevelUpMessage()
    {
        if (levelUpLabel != null)
        {
            removeObject(levelUpLabel); // remove previous label (fail safe)
        }
        levelUpLabel = new Label("Level Up!", 100); // create level up label
        addObject(levelUpLabel, getWidth() / 2, getHeight() / 2); // add label to middle
        levelUpMessageTimer = 60; // displays for 60 frames
    }
}
