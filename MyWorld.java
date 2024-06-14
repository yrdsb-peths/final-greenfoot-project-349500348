import greenfoot.*;

/**
 * Write a description of class MyWorld here.
 * 
 * @author Ostin H.
 * @version 06/14/2024
 */
public class MyWorld extends World
{
    GreenfootSound gameOverSound = new GreenfootSound("gameover.mp3");
    GreenfootSound levelUpSound = new GreenfootSound("levelup.mp3");
    private int shieldSpawnTimer = 0; // variable to count frames for shield spawning
    private int enemySpawnTimer = 0; // variable to count frames for enemy spawning
    public boolean gameOver = false; // flag to indicate if the game is over
    private boolean isPlaying = false; // flag to indicate if the game is currently playing
    public int score = 0; // current score of the game
    Label scoreLabel = new Label(0, 80); // Label object to display the score
    Label levelLabel = new Label("Level 1", 80); // Label object to display the current level
    private int spawnInterval = 180; // interval for enemy spawning
    int level = 1; // level increases enemy spawn rate and bullet speed
    private int levelUpMessageTimer = 0; // timer for displaying the "Level Up!" message
    private int skipCooldown = 0; // cooldown for the cheat key action
    private int shieldCheatCooldown = 0; // cooldown for spawning shields using cheat key
    private int highscore = 0; // highest score achieved in the game
    Label levelUpLabel = new Label("Level Up!", 100); // Label object to display "Level Up!" message
    Label highscoreLabel = new Label("Highscore: " + highscore, 50); // Label object to display the highscore
    Label playAgainLabel = new Label("Press P to Play Again", 50); // Label object to prompt player to play again
    Label gameOverLabel = new Label("Game Over", 100); // Label object to display "Game Over" message
    private final int shieldSpawnInterval = 900; // interval for shield spawning

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
            shieldSpawnTimer++; // increment the shield spawn timer
            
            // Check if it's time to spawn an enemy
            if (enemySpawnTimer >= spawnInterval)
            {
                spawnEnemy(); // call the spawnEnemy method
                enemySpawnTimer = 0; // reset the timer
            }
            
            // Check if it's time to spawn a shield
            if (shieldSpawnTimer >= shieldSpawnInterval)
            {
                spawnShield(); // call the spawnShield method
                shieldSpawnTimer = 0; // reset the timer
            }
            
            // Check if it's time to remove the "Level Up!" message
            if (levelUpMessageTimer > 0)
            {
                levelUpMessageTimer--; // decrement the timer
                if (levelUpMessageTimer == 0)
                {
                    removeObject(levelUpLabel); // Remove the "Level Up!" label after timer ends
                }
            }
            
            // Check cooldown for cheat key action
            if (skipCooldown > 0) 
            {
                skipCooldown--; // decrement the cooldown
            }
            
            // Check cooldown for shield cheat key action
            if (shieldCheatCooldown > 0) 
            {
                shieldCheatCooldown--; // decrement the cooldown
            }
            
            // Check if cheat key is pressed and cooldown is over
            if (Greenfoot.isKeyDown("l") && skipCooldown <= 0) 
            {
                levelUp(); // Cheat key action: Level up
                skipCooldown = 65; // Reset cooldown
            }
            
            // Check if shield cheat key is pressed and cooldown is over
            if (Greenfoot.isKeyDown("s") && shieldCheatCooldown <= 0)
            {
                spawnShield(); // Cheat key action: Spawn shield
                shieldCheatCooldown = 30; // Set cooldown for shield cheat
            }
        }
        else // if the game is over
        {
            if (Greenfoot.isKeyDown("p"))
            {
                resetGame(); // Reset the game if p pressed
            }
        }
    }
    
    /**
     * Increases the level of the game.
     */
    public void levelUp() 
    {
        level++; // Increment the level
        levelLabel.setValue("Level " + level); // Update the level label
        updateSpawnInterval(); // Update the spawn interval based on the new level
        showLevelUpMessage(); // Show "Level Up!" message
    }
    
    /**
     * Updates the highscore label if a new highscore is achieved.
     */
    private void updateHighscoreLabel() 
    {
        if (highscoreLabel != null) 
        {
            highscoreLabel.setValue("Highscore: " + highscore); // Update the highscore label
        }
    }
    
    /**
     * Increases the score by 50 and updates the score label.
     * Checks if the score is a multiple of 500 to level up.
     */
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
        if (score > highscore) 
        {
            highscore = score; // Update highscore if new highscore is achieved
            updateHighscoreLabel();
        }
    }
    
    /**
     * Updates the spawn interval based on the current level.
     * Decreases the spawn interval as the level increases, with a minimum interval of 30.
     */
    private void updateSpawnInterval()
    {
        // Decrease the spawn interval as the level increases, minimum of 30
        spawnInterval = Math.max(180 - (level - 1) * 10, 30);
    }
    
    /**
     * Handles game over scenario by removing all enemy and enemy laser objects,
     * displaying the "Game Over" message, highscore label, and prompt to play again.
     */
    public void gameOver()
    {
        gameOverSound.play();
        removeObjects(getObjects(Enemy.class)); // Remove all Enemy objects from the world
        removeObjects(getObjects(EnemyLaser.class)); // Remove all EnemyLaser objects from the world
        
        gameOver = true; // Set the game over flag to true
        addObject(gameOverLabel, 300, 200); // Add the "Game Over" label to the world
        addObject(highscoreLabel, getWidth() / 2, getHeight() / 2 + 50); // Add the highscore label to the world
        addObject(playAgainLabel, getWidth() / 2, getHeight() / 2 + 100); // Add the "Press P to Play Again" label to the world
    }
    
    /**
     * Spawns a new enemy object at the rightmost position of the screen with a random y position.
     */
    public void spawnEnemy()
    {
        Enemy enemy = new Enemy(); // Create a new Enemy object
        
        // Add the enemy to the right side of the screen at a random y position
        int x = getWidth() - 1; // Rightmost position
        int y = Greenfoot.getRandomNumber(getHeight()); // Random y position
        
        addObject(enemy, x, y); // Add the enemy to the world at the calculated position
    }
    
    /**
     * Displays the "Level Up!" message in the middle of the screen for a certain duration.
     */
    private void showLevelUpMessage()
    {  
        levelUpSound.play();
        addObject(levelUpLabel, getWidth() / 2, getHeight() / 2); // Add the "Level Up!" label to the middle of the screen
        levelUpMessageTimer = 60; // Displays for 60 frames
    }
    
    /**
     * Updates the score, level, and highscore labels after a game reset.
     */
    private void updateLabels()
    {
        scoreLabel.setValue(score); // Update score label
        levelLabel.setValue("Level " + level); // Update level label
        updateHighscoreLabel(); // Update highscore label
    }
    
    /**
     * Resets the game by removing all objects from the world and resetting game variables.
     * Adds initial objects such as the hero, score label, and level label.
     */
    public void resetGame()
    {
        // Remove all objects from the world
        removeObjects(getObjects(Actor.class));
        
        // Reset game variables
        enemySpawnTimer = 0;
        gameOver = false;
        isPlaying = true;
        score = 0;
        level = 1;
        spawnInterval = 180;
        levelUpMessageTimer = 0;
        skipCooldown = 0;
    
        // Add initial objects
        Hero hero = new Hero();
        addObject(hero, 70, 300);
        addObject(scoreLabel, getWidth()/2, 30);
        addObject(levelLabel, 105, 30);
        updateHighscoreLabel(); // Ensure highscoreLabel is added to the world
        
        updateLabels(); // Update score, level, and highscore labels
    }
    
    /**
     * Spawns a new shield object at the rightmost position of the screen with a random y position.
     */
    public void spawnShield()
    {
        Shield shield = new Shield(); // Create a new Shield object
        int x = getWidth() - 1; // Rightmost position
        int y = Greenfoot.getRandomNumber(getHeight()); // Random y position
        addObject(shield, x, y); // Add the shield to the world at the calculated position
    }
}
