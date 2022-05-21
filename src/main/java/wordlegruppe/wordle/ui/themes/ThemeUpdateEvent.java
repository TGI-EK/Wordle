package wordlegruppe.wordle.ui.themes;

public class ThemeUpdateEvent {

    private final Theme oldTheme;
    private final Theme newTheme;

    public ThemeUpdateEvent(Theme oldTheme, Theme newTheme) {
        this.oldTheme = oldTheme;
        this.newTheme = newTheme;
    }

    public Theme getOldTheme() {
        return oldTheme;
    }

    public Theme getNewTheme() {
        return newTheme;
    }
}
