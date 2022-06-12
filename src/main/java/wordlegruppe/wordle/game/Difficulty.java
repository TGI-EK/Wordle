package wordlegruppe.wordle.game;

/**
 *
 * @author Oliver Schneider
 */
public class Difficulty {
    
    private static final Difficulty recentDifficulty = new Difficulty();
    
    private boolean hardMode;
    
    public void setHardMode(boolean mode)
    {
        this.hardMode = mode;
    }
    
    public boolean getHardMode()
    {
        return hardMode;     
    }
    
    public static Difficulty getRecentDifficulty()
    {
        return recentDifficulty;
    }
}
