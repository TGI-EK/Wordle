package wordlegruppe.wordle.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import wordlegruppe.wordle.App;
import wordlegruppe.wordle.ui.themes.Theme;
import wordlegruppe.wordle.ui.themes.ThemeUpdateEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndscreenController implements Initializable {

    @FXML
    public VBox root;
    @FXML
    public Button btn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Theme.addStylesheetList(root.getStylesheets());
    }


    private static FXMLLoader loader;
    public static FXMLLoader getLoader() {
        if(loader == null)
            loader = new FXMLLoader(App.getUIResource("Wordle-endscreen.fxml"));
        return loader;
    }
}
