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

public class SettingController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private ChoiceBox<Theme> choiceBox;

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

        choiceBox.getSelectionModel().selectedItemProperty().addListener((value, n1, n2) -> Theme.setTheme(value.getValue()));
    }

    private static FXMLLoader loader;
    public static FXMLLoader getLoader() {
        if(loader == null)
            loader = new FXMLLoader(App.getUIResource("settings.fxml"));
        return loader;
    }
}
