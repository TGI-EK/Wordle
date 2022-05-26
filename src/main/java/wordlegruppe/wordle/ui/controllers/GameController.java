/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordlegruppe.wordle.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import wordlegruppe.wordle.App;
import wordlegruppe.wordle.ui.themes.Theme;

/**
 * FXML Controller class
 *
 * @author Christos
 */
public class GameController implements Initializable {

    @FXML
    private AnchorPane root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Theme.addStylesheetList(root.getStylesheets());
    }

    public static Parent load() throws IOException {
        return FXMLLoader.load(App.getUIResource("Game.fxml"));
    }
}
