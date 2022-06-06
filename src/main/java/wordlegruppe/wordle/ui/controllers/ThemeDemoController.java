package wordlegruppe.wordle.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import wordlegruppe.wordle.App;
import wordlegruppe.wordle.ui.themes.Theme;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Demo on how to use themes and handle theme updates
 * @author YetiHafen
 */
public class ThemeDemoController implements Initializable {


    public ChoiceBox<String> choiceBox;
    @FXML
    BorderPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Theme.addStylesheetList(root.getStylesheets());
        choiceBox.getItems().add("Option 1");
        choiceBox.getItems().add("Option 2");
        choiceBox.getItems().add("Option 3");
    }


    @FXML
    public void onBtnDarkPress() {
        Theme.setTheme(Theme.DARK);
    }

    @FXML
    public void onBtnLightPress() {
        Theme.setTheme(Theme.LIGHT);
    }


    private static FXMLLoader loader;
    public static FXMLLoader getLoader() {
        if(loader == null)
            loader = new FXMLLoader(App.getUIResource("theme-demo.fxml"));
        return loader;
    }

}
