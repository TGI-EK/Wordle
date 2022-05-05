package wordlegruppe.wordle.game;

import javafx.application.Platform;

import java.util.function.Consumer;

public class WordGenerator {

    public void fetchCurrentWord(Consumer<String> callback) {
        // reine demo (hier soll später komplexer code stehen
        new Thread(() -> Platform.runLater(() -> callback.accept("TESTE"))).start();
    }
}
