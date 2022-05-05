module wordlegruppe.wordle {
    requires javafx.controls;
    requires javafx.fxml;

    opens wordlegruppe.wordle to javafx.fxml;
    exports wordlegruppe.wordle.ui;
    opens wordlegruppe.wordle.ui to javafx.fxml;
}
