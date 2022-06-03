package wordlegruppe.wordle.ui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import wordlegruppe.wordle.App;
import wordlegruppe.wordle.ui.themes.Theme;

import java.net.URL;
import java.util.ResourceBundle;

public class TitleController implements Initializable {
    @javafx.fxml.FXML
    private VBox root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Theme.addStylesheetList(root.getStylesheets());
    }

    private static FXMLLoader loader;
    public static FXMLLoader getLoader() {
        if(loader == null)
            loader = new FXMLLoader(App.getUIResource("Titlescreen.fxml"));
        return loader;
    }
}
