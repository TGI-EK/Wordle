package wordlegruppe.wordle.game;

/**
 * @author YetiHafen (Florian Fezer)
 */
public class SubmitResult {

    private final LetterResult[] wordRes;
    private final boolean won;
    private final boolean gameOver;

    public SubmitResult(LetterResult[] wordRes, boolean won, boolean isOver) {
        this.won = won;
        this.wordRes = wordRes;
        this.gameOver = isOver;
    }

    public LetterResult[] getWordRes() {
        return wordRes;
    }

    public boolean isWon() {
        return won;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
