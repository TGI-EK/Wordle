package wordlegruppe.wordle.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

    @FXML
    BorderPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Theme.addStylesheetList(root.getStylesheets());
    }


    @FXML
    public void onBtnDarkPress() {
        Theme.setTheme(Theme.DARK);
    }

    @FXML
    public void onBtnLightPress() {
        Theme.setTheme(Theme.LIGHT);
    }


    public static Parent load() throws IOException {
        return FXMLLoader.load(App.getUIResource("theme-demo.fxml"));
    }

}
