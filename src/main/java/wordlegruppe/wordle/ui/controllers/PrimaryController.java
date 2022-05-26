package wordlegruppe.wordle.ui.controllers;

import javafx.fxml.FXML;
import wordlegruppe.wordle.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() {
        App.setRoot(App.getFXMLLoader("secondary"));
    }
}
