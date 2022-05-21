module wordlegruppe.wordle {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.sun.jna.platform;
    requires com.sun.jna;

    opens wordlegruppe.wordle;
    exports wordlegruppe.wordle;
    opens wordlegruppe.wordle.ui.natives;
    exports wordlegruppe.wordle.ui.natives;
    exports wordlegruppe.wordle.ui.natives.structs;
    opens wordlegruppe.wordle.ui.natives.structs;
    exports wordlegruppe.wordle.ui.controllers;
    opens wordlegruppe.wordle.ui.controllers;
    exports wordlegruppe.wordle.ui.themes;
    opens wordlegruppe.wordle.ui.themes;
}
