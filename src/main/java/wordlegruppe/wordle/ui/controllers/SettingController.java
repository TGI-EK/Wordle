package wordlegruppe.wordle.ui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import wordlegruppe.wordle.App;

public class SettingController{
    private static FXMLLoader loader;
    public static FXMLLoader getLoader() {
        if(loader == null)
            loader = new FXMLLoader(App.getUIResource("settings.fxml"));
        return loader;
    }
}
