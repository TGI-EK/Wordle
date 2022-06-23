/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordlegruppe.wordle.ui.controllers;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.*;
import wordlegruppe.wordle.game.Game;
import wordlegruppe.wordle.game.LetterResult;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import wordlegruppe.wordle.App;
import wordlegruppe.wordle.game.Difficulty;
import wordlegruppe.wordle.game.SubmitResult;
import wordlegruppe.wordle.game.WordList;
import wordlegruppe.wordle.ui.themes.Theme;

/**
 * FXML Controller class
 *
 * @author Christos
 * @author YetiHafen
 * @author Ichmagmathe
 */
public class GameController implements Initializable {

    private String currentWord = "";
    private String lastWord;
    private LetterResult[] lastResult;
    private int rowIndex = 0;
    private boolean hardMode = false;
    private final Game game = new Game();
    private final WordList wordList = new WordList();

    @FXML
    private Pane root;

    @FXML
    private GridPane grid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Theme.addStylesheetList(root.getStylesheets());    
        // Game starting
        game.start();
        this.hardMode = Difficulty.INSTANCE.getHardMode();
    }

    private void updateDisplayedWord() {
        for(int i = 0; i < 5; i++) {
            // multiply columns with rowIndex and add columnIndex to get
            // absolute index in a list that looks like this
            //  [0][1][2][3][4]
            //  [5][6][7][8][9]
            //  [.][.]..
            Node node = grid.getChildren().get(5*rowIndex + i);
            if(node instanceof Label label) {
                label.setText("" + (currentWord.length() > i ? currentWord.charAt(i) : ""));
            }
        }
    }

    public void onKeyTyped(KeyEvent e) {
        String in = e.getCharacter();

        byte[] data = in.getBytes(StandardCharsets.US_ASCII);

        int charCode = data[0];

        // check for uppercase/lowercase by ASCII value
        boolean isUppercase = charCode >= 65 && charCode <= 90;
        boolean isLowercase = charCode >= 97 && charCode <= 122;

        boolean isLetter = isLowercase ||isUppercase;

        if(isLowercase) {
            // ascii jump - convert to uppercase
            charCode -= 32;
        }

        // convert modified input to char
        char upper = (char) charCode;

        // detect special use cases
        boolean isBackspace = charCode == 8;
        boolean isEnter = charCode == 13;

        int wordLength = currentWord.length();

        if(isLetter && wordLength < 5)
            // append letter to word
            currentWord += upper;
        else if(isBackspace && wordLength > 0)
            // remove one letter from word
            currentWord = currentWord.substring(0, wordLength - 1);
        
        //Go to next Row
        if(isEnter && rowIndex < 6 && wordLength == 5)
        {
            if(wordList.contains(currentWord))
            {
                if(hardMode && checkForChar()){
                    SubmitResult submitResult = game.submitWord(currentWord.toLowerCase());

                    if(submitResult.isGameOver()) {
                        App.setRoot(EndscreenController.createLoader());
                        return;
                    }

                    LetterResult[] result = submitResult.getWordRes();

                    changeColor(result);

                    lastWord = currentWord;
                    lastResult = result;
                    currentWord = "";
                    rowIndex += 1;
                }
                else if(!hardMode)
                {
                    SubmitResult submitResult = game.submitWord(currentWord.toLowerCase());

                    if(submitResult.isGameOver()) {
                        App.setRoot(EndscreenController.createLoader());
                        return;
                    }

                    LetterResult[] result = submitResult.getWordRes();

                    changeColor(result);

                    currentWord = "";
                    rowIndex += 1;
                }
            } else {
                new WiggleTimer().start();
            }
        } else if(isEnter) new WiggleTimer().start();

        // update UI
        if(game.isActive()) updateDisplayedWord();
    }

    //Etwas mehr Uebersichtlichkeit
    private void changeColor(LetterResult[] result) {
        //Change color for specific case; don't forget lowerCase :C

        for(int i = 0; i < 5; i++) {
            Node node = grid.getChildren().get(5*rowIndex + i);
            if(node instanceof Label label)
            {
                Color color = result[i].getCorrespondingColor();
                Insets insets = label.getInsets();
                label.setBackground(new Background(new BackgroundFill(color, null, insets)));
            }
        }
    }

    private boolean checkForChar() {
        if(lastWord == null && lastResult == null) return true;
        assert lastWord != null;
        char[] chars = lastWord.toCharArray();
        char[] currentWordChars = currentWord.toCharArray();
        int charsRight = 0;
        Boolean[] isValid = new Boolean[5];

        for(int i = 0; i < lastResult.length; i++){
            if(lastResult[i].toString().equals("PART_RIGHT") || lastResult[i].toString().equals("FULL_RIGHT"))
            {
                for(int j = 0; j < currentWordChars.length; j++){
                    if(currentWordChars[j] == chars[i]){
                        isValid[i] = true;
                        currentWordChars[j] = ' ';
                        break;
                    }
                    else isValid[i] = false;
                }
                charsRight++;
            }
        }
        //Check ob genau so viele "true" im Array sind wie richtige buchstaben
        return (int)Arrays.stream(isValid).filter(c -> c != null && c).count() == charsRight;
    }

    private class WiggleTimer extends AnimationTimer {
        private int a = 360;
        @Override
        public void handle(long now) {
            if((a -= 10) <= 0) {
                stop();
                applyInRow(rowIndex, label -> label.setTranslateX(0));
                return;
            }

            applyInRow(rowIndex, label -> label.setTranslateX(Math.sin(a)*10));
        }

        private void applyInRow(int row, Consumer<Label> action) {
            for(int i = 0; i < 5; i++) {
                Node node = grid.getChildren().get(5*row+i);
                if(node instanceof Label label) action.accept(label);
            }
        }
    }

    public static FXMLLoader getLoader() {
        return new FXMLLoader(App.getUIResource("Game.fxml"));
    }
}
