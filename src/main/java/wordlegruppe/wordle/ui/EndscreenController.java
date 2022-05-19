package wordlegruppe.wordle.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class EndscreenController {

    public static Parent createEndscreen() {
        try {
            URL fxml = EndscreenController.class.getResource("Wordle-Endscreen.fxml");
            assert fxml != null;
            return FXMLLoader.load(fxml);
        } catch (IOException | AssertionError e) {
            e.printStackTrace();
            return null;
        }
    }
}
