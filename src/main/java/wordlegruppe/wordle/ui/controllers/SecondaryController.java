package wordlegruppe.wordle.ui.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import wordlegruppe.wordle.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot(App.loadFXML("primary"));
    }
}