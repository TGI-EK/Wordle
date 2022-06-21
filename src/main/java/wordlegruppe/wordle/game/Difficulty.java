package wordlegruppe.wordle.game;

/**
 * @author Oliver Schneider
 */
public class Difficulty {
    
    public static final Difficulty INSTANCE = new Difficulty();
    
    private boolean hardMode;
    
    public void setHardMode(boolean mode)
    {
        this.hardMode = mode;
    }
    
    public boolean getHardMode()
    {
        return hardMode;     
    }
}
