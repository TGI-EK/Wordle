package wordlegruppe.wordle.game;

public class SubmitResult {

    private final LetterResult[] wordRes;
    private final boolean won;

    public SubmitResult(LetterResult[] wordRes, boolean won) {
        this.won = won;
        this.wordRes = wordRes;
    }

    public LetterResult[] getWordRes() {
        return wordRes;
    }

    public boolean isWon() {
        return won;
    }
}
