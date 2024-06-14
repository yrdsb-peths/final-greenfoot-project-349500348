import greenfoot.*;

/**
 * The game world where gameplay takes place.
 * Manages spawning of enemies, powerups, game over scenarios, and game reset functionality.
 * 
 * @author Ostin H.
 * @version 06/14/2024
 */
public class MyWorld extends World
{
    GreenfootSound gameOverSound = new GreenfootSound("gameover.mp3");
    GreenfootSound levelUpSound = new GreenfootSound("levelup.mp3");
    private int[] timers = {0, 0, 0, 0, 0}; // Array of timers 0: shield spawn timer, 1: enemy spawn timer, 2: level up message timer, 3: skip cooldown, 4: shield cheat cooldown
    public boolean gameOver = false; // flag to indicate if the game is over
    private boolean isPlaying = false; // flag to indicate if the game is currently playing
    public int score = 0; // current score of the game
    Label[] labels = {new Label(0, 80), new Label("Level 1", 80), new Label("Level Up!", 100), new Label("Highscore: 0", 50), new Label("Press P to Play Again", 50), new Label("Game Over", 100)}; // Array of labels 0: score label, 1: level label, 2: level up label, 3: highscore label, 4: play again label, 5: game over label
    private int spawnInterval = 180; // interval for enemy spawning
    int level = 1; // level increases enemy spawn rate and bullet speed
    private final int shieldSpawnInterval = 840; // interval for shield spawning
    private int highscore = 0; // highest score achieved in the game session

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
        addObject(labels[0], getWidth()/2, 30);
        addObject(labels[1], 105, 30);
    }
    
    /**
     * Act - do whatever the MyWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (!gameOver) // check if the game is not over
        {
            timers[1]++; // increment the enemy spawn timer
            timers[0]++; // increment the shield spawn timer
            
            // Check if it's time to spawn an enemy
            if (timers[1] >= spawnInterval)
            {
                spawnEnemy(); // call the spawnEnemy method
                timers[1] = 0; // reset the timer
            }
            
            // Check if it's time to spawn a shield
            if (timers[0] >= shieldSpawnInterval)
            {
                spawnShield(); // call the spawnShield method
                timers[0] = 0; // reset the timer
            }
            
            // Check if it's time to remove the "Level Up!" message
            if (timers[2] > 0)
            {
                timers[2]--; // decrement the timer
                if (timers[2] == 0)
                {
                    removeObject(labels[2]); // Remove the "Level Up!" label after timer ends
                }
            }
            
            // Check cooldown for cheat key action
            if (timers[3] > 0) 
            {
                timers[3]--; // decrement the cooldown
            }
            
            // Check cooldown for shield cheat key action
            if (timers[4] > 0) 
            {
                timers[4]--; // decrement the cooldown
            }
            
            // Check if cheat key is pressed and cooldown is over
            if (Greenfoot.isKeyDown("l") && timers[3] <= 0) 
            {
                levelUp(); // Cheat key action: Level up
                timers[3] = 65; // Reset cooldown
            }
            
            // Check if shield cheat key is pressed and cooldown is over
            if (Greenfoot.isKeyDown("s") && timers[4] <= 0)
            {
                spawnShield(); // Cheat key action: Spawn shield
                timers[4] = 30; // Set cooldown for shield cheat
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
        labels[1].setValue("Level " + level); // Update the level label
        updateSpawnInterval(); // Update the spawn interval based on the new level
        showLevelUpMessage(); // Show "Level Up!" message
    }
    
    /**
     * Updates the highscore label if a new highscore is achieved.
     */
    private void updateHighscoreLabel() 
    {
        if (labels[3] != null) 
        {
            labels[3].setValue("Highscore: " + highscore); // Update the highscore label
        }
    }
    
    /**
     * Increases the score by 50 and updates the score label.
     * Checks if the score is a multiple of 500 to level up.
     */
    public void increaseScore()
    {
        score += 50; // Increase score by 50
        labels[0].setValue(score); // Update the score label
        
        // Check if the score is a multiple of 500 to level up
        if (score % 500 == 0)
        {
            level++;
            labels[1].setValue("Level " + level); // Update the level label
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
        removeObjects(getObjects(Shield.class)); // Remove all Shield objects from the world

        gameOver = true; // Set the game over flag to true
        addObject(labels[5], 300, 200); // Add the "Game Over" label to the world
        addObject(labels[3], getWidth() / 2, getHeight() / 2 + 50); // Add the highscore label to the world
        addObject(labels[4], getWidth() / 2, getHeight() / 2 + 100); // Add the "Press P to Play Again" label to the world
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
        addObject(labels[2], getWidth() / 2, getHeight() / 2); // Add the "Level Up!" label to the middle of the screen
        timers[2] = 60; // Displays for 60 frames
    }
    
    /**
     * Updates the score, level, and highscore labels after a game reset.
     */
    private void updateLabels()
    {
        labels[0].setValue(score); // Update score label
        labels[1].setValue("Level " + level); // Update level label
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
        timers[0] = 0;
        timers[1] = 0;
        timers[2] = 0;
        timers[3] = 0;
        gameOver = false;
        isPlaying = true;
        score = 0;
        level = 1;
        spawnInterval = 180;
    
    
        // Add initial objects
        Hero hero = new Hero();
        addObject(hero, 70, 300);
        addObject(labels[0], getWidth()/2, 30);
        addObject(labels[1], 105, 30);
        updateHighscoreLabel(); // Ensure highscore label is added to the world
        
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