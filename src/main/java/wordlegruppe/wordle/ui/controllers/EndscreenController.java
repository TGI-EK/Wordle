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

    /**
     * load the corresponding fxml File
     * @return the Parent object of the FXML structure
     */
    public static Parent createEndscreen() {
        try {
            URL fxml = App.getUIResource("Wordle-Endscreen.fxml");
            assert fxml != null;
            return FXMLLoader.load(fxml);
        } catch (IOException | AssertionError e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Theme.addUpdateListener(this::onThemeUpdate);
    }

    // called when the theme was changed
    public void onThemeUpdate(ThemeUpdateEvent e) {
        // replace the stylesheets on the root component
        root.getStylesheets().remove(e.getOldTheme().getGlobalStylesheet());
        root.getStylesheets().add(e.getNewTheme().getGlobalStylesheet());
    }
}
