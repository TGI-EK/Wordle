package wordlegruppe.wordle.ui;

import java.io.IOException;
import javafx.fxml.FXML;
import wordlegruppe.wordle.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}