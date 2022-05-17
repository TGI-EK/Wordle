module wordlegruppe.wordle {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.sun.jna.platform;
    requires com.sun.jna;

    opens wordlegruppe.wordle;
    exports wordlegruppe.wordle;
    opens wordlegruppe.wordle.ui;
    exports wordlegruppe.wordle.ui;
    opens wordlegruppe.wordle.ui.natives;
    exports wordlegruppe.wordle.ui.natives;
}
