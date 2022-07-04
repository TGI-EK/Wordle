package wordlegruppe.wordle.game;

import javafx.scene.paint.Color;

/**
 * @author YetiHafen (Florian Fezer)
 */
public enum LetterResult {

    WRONG(Color.web("#D91818")),
    PART_RIGHT(Color.web("#FFF200")),
    FULL_RIGHT(Color.web("#5FFF3E"));

    private final Color color;

    LetterResult(Color color) {
        this.color = color;
    }

    public Color getCorrespondingColor() {
        return color;
    }
}
