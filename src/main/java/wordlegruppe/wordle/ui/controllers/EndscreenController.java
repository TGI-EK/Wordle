package wordlegruppe.wordle.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import wordlegruppe.wordle.App;
import wordlegruppe.wordle.ui.themes.Theme;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import wordlegruppe.wordle.game.Game;

public class EndscreenController implements Initializable {

    @FXML
    public VBox root;
    public Button btn;
    @FXML
    private Label LabelWon;
    @FXML
    private Label LabelWonText;
    @FXML
    private Label LabelTries;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Theme.addStylesheetList(root.getStylesheets());
        setEndGameText(Game.getMostRecentGame().isWon(), Game.getMostRecentGame().getTries());
    }
    
    public void setEndGameText(boolean won, int tries)
    {
        if(won)
        {
            LabelWon.setText("You won!");
            LabelWonText.setText("Congrats! You guessed the word");
            LabelTries.setText("Number of attempts: "+tries);
        }
        else
        {
            LabelWon.setText("You lost!");
            LabelWonText.setText("Too bad! You have not guessed the word");
            LabelTries.setText("");
        }
    }
    
    @FXML
    private void buttonRestart(ActionEvent event) 
    {
        App.setRoot(GameController.getLoader());
    }
    
    @FXML
    private void buttonBack(ActionEvent event) {
        App.setRoot(TitleController.createLoader());
    }
         
    public static FXMLLoader createLoader() {
        return new FXMLLoader(App.getUIResource("Wordle-endscreen.fxml"));
    }
}    
