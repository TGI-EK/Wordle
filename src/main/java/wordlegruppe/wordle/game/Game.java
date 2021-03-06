package wordlegruppe.wordle.game;


import java.util.HashMap;
import java.util.Map;

/**
 * @author YetiHafen (Florian Fezer)
 */
public class Game {

    private static Game mostRecentGame;

    private final WordList wordList;

    private String wordToGuess;
    private boolean active;
    private boolean won;
    private int tries;

    public Game() {
        Game.mostRecentGame = this;
        this.wordList = new WordList();
    }

    /**
     * start the game
     */
    public void start() {
        // make sure game can only be started once
        if(this.active) throw new IllegalStateException("Game already running");

        this.active = true;
        this.tries = 0;
        this.wordToGuess = wordList.randomWord();
        System.out.println("DEBUG: word to guess:"+wordToGuess);
    }

    /**
     * Used to check a given word
     * @param word the word to check
     * @return an {@link LetterResult[]}
     * with the indices of the specific chars
     * or {@link null} if the given word was not valid
     */
    public SubmitResult submitWord(String word) {
        assert active;
        assert word.length() == 5;

        LetterResult[] results = checkMatches(word);

        this.won = Game.isWon(results);
        if(this.won || this.tries >= 5) {
            this.active = false;
        }
        tries += 1;
        return new SubmitResult(results, this.won, !active);
    }


    private LetterResult[] checkMatches(String word) {

        LetterResult[] results = new LetterResult[5];

        Map<Character, Integer> correctChars = new HashMap<>();

        for(int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);

            int occurrencesInOriginal = countCharsInString(wordToGuess, currentChar);

            if(occurrencesInOriginal == 0) {
                results[i] = LetterResult.WRONG;
                continue;
            }

            if(currentChar == wordToGuess.charAt(i)) {
                results[i] = LetterResult.FULL_RIGHT;
                int correct = correctChars.getOrDefault(currentChar, 0);
                correctChars.put(currentChar, correct + 1);
            }
        }

        Map<Character, Integer> handledMultiOccurrences = new HashMap<>();

        for(int i = 0; i < word.length(); i++) {
            if(results[i] != null) continue;

            char currentChar = word.charAt(i);

            int timesHandled = handledMultiOccurrences.getOrDefault(currentChar, 0);
            handledMultiOccurrences.put(currentChar, timesHandled + 1);

            boolean notOverused = timesHandled < countCharsInString(wordToGuess, currentChar);
            boolean notCorrectGuessed = timesHandled < correctChars.getOrDefault(currentChar, Integer.MAX_VALUE) - 1;

            if(notOverused && notCorrectGuessed)
                results[i] = LetterResult.PART_RIGHT;
            else
                results[i] = LetterResult.WRONG;
        }
        return results;
    }

    /**
     * find the number of times a single
     * {@link char} is contained in a {@link String}
     * @param s the String
     * @param c the char
     * @return the number of times {@code c} is contained in {@code s}
     */
    private int countCharsInString(String s, char c) {
        int count = 0;
        for(char sc : s.toCharArray()) {
            if (sc == c) count++;
        }
        return count;
    }

    /**
     * check if a game with the specified result would be won
     * @param results the {@link LetterResult[]} to check
     * @return if the game would be won
     */
    public static boolean isWon(LetterResult[] results) {
        assert results.length == 5;
        for(LetterResult res : results) {
            if(res != LetterResult.FULL_RIGHT)
                return false;
        }
        return true;
    }

    public static Game getMostRecentGame() {
        return mostRecentGame;
    }

    /**
     * check if the game is won
     * @return if the game is won
     */
    public boolean isWon() {
        return won;
    }
    
    public String getToGuessingWord(){
        return wordToGuess;
    }
    
    public int getTries()
    {
        return tries;
    }
    
    /**
     * Check current game state
     * @return if the game is active
     */
    public boolean isActive() {
        return active;
    }
}
