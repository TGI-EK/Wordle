package wordlegruppe.wordle.ui.themes;

/**
 * @author YetiHafen (Florian Fezer)
 */
public record ThemeUpdateEvent(
        Theme oldTheme,
        Theme newTheme
    ) {
}
