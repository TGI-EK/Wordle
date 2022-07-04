package wordlegruppe.wordle.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import wordlegruppe.wordle.App;
import wordlegruppe.wordle.ui.themes.Theme;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author YetiHafen (Florian Fezer)
 * @author Ichmagmathe (Oliver Schneider)
 */
public class TitleController implements Initializable {
    @FXML
    private VBox root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Theme.addStylesheetList(root.getStylesheets());
    }


    public void onBtnStartAction(ActionEvent e) {
        App.setRoot(GameController.getLoader());
    }

    public void onBtnSettingsAction(ActionEvent e) {
        App.setRoot(SettingController.createLoader());
    }

    public void onBtnQuitAction(ActionEvent e) {
        System.exit(0);
    }

    public static FXMLLoader createLoader() {
        return new FXMLLoader(App.getUIResource("Titlescreen.fxml"));
    }

}
