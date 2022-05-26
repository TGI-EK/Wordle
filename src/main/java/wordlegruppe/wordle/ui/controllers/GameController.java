/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordlegruppe.wordle.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import wordlegruppe.wordle.App;
import wordlegruppe.wordle.ui.themes.Theme;

/**
 * FXML Controller class
 *
 * @author Christos
 */
public class GameController implements Initializable {

    private String currentWord = "";
    private int rowIndex = 0;

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane grid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Theme.addStylesheetList(root.getStylesheets());
    }

    private void updateDisplayedWord() {
        for(int i = 0; i < 5; i++) {
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

        boolean isUppercase = charCode >= 65 && charCode <= 90;
        boolean isLowercase = charCode >= 97 && charCode <= 122;
        boolean isLetter = isLowercase ||isUppercase;

        if(isLowercase) {
            charCode -= 32;
        }

        char upper = (char) charCode;

        boolean isBackspace = charCode == 8;
        boolean isEnter = charCode == 13;

        int wordLength = currentWord.length();

        if(isLetter && wordLength < 5)
            currentWord += upper;
        else if(isBackspace && wordLength > 0)
            currentWord = currentWord.substring(0, wordLength - 1);

        updateDisplayedWord();
    }

    private static FXMLLoader loader;
    public static FXMLLoader getLoader() {
        if(loader == null)
            loader = new FXMLLoader(App.getUIResource("Game.fxml"));
        return loader;
    }
}
