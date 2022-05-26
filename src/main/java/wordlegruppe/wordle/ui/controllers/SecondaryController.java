package wordlegruppe.wordle.ui.controllers;

import javafx.fxml.FXML;
import wordlegruppe.wordle.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() {
        App.setRoot(App.getFXMLLoader("primary"));
    }
}