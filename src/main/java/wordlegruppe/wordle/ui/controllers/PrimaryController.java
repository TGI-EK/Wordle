package wordlegruppe.wordle.ui.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import wordlegruppe.wordle.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot(App.loadFXML("secondary"));
    }
}
