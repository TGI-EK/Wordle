package wordlegruppe.wordle.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import wordlegruppe.wordle.App;
import wordlegruppe.wordle.ui.themes.Theme;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import wordlegruppe.wordle.game.Difficulty;

/**
 * @author YetiHafen (Florian Fezer)
 */
public class SettingController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private ChoiceBox<Theme> choiceBox;
    @FXML
    private CheckBox HardMode;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Theme.addStylesheetList(root.getStylesheets());

        choiceBox.getItems().add(Theme.DARK);
        choiceBox.getItems().add(Theme.LIGHT);
        choiceBox.getSelectionModel().select(Theme.getCurrentTheme());

        choiceBox.getSelectionModel().selectedItemProperty().addListener((value, n1, n2) -> Theme.setTheme(value.getValue()));
        
        HardMode.setSelected(Difficulty.INSTANCE.getHardMode());
    }

    @FXML
    private void setHardMode(ActionEvent event) {
        BooleanProperty selected = HardMode.selectedProperty();
        Difficulty.INSTANCE.setHardMode(selected.getValue());
    }
    
    @FXML
    private void buttonBack(ActionEvent event) {
        App.setRoot(TitleController.createLoader());
    }
    
    public static FXMLLoader createLoader() {       
        return new FXMLLoader(App.getUIResource("settings.fxml"));
    } 
}
