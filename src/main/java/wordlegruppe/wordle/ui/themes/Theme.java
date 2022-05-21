package wordlegruppe.wordle.ui.themes;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public enum Theme {
    LIGHT(getStylePath("light.css"), Color.WHITE),
    DARK(getStylePath("dark.css"), Color.web("#3B3B3B")),
    DEFAULT;

    private String globalStylesheet;
    private Color captionColor;

    Theme() {
    }

    Theme(String globalStylesheet, Color captionColor) {
        this.globalStylesheet = globalStylesheet;
        this.captionColor = captionColor;
    }

    public String getGlobalStylesheet() {
        return globalStylesheet;
    }

    public Color getCaptionColor() {
        return captionColor;
    }

    private static Theme currentTheme;
    private static Theme defaultTheme;
    // should be replaced with the update to an ObservableTheme
    private static final List<Consumer<ThemeUpdateEvent>> updateListeners = new ArrayList<>();

    public static void setTheme(Theme theme) {
        Theme prev = currentTheme;
        if(theme == DEFAULT)
            Theme.currentTheme = Theme.defaultTheme;
        else
            Theme.currentTheme = theme;

        updateListeners.forEach(listener -> listener.accept(new ThemeUpdateEvent(prev, theme)));
    }

    public static void init() {
        Theme theme = getDefaultTheme();
        setTheme(theme);
        defaultTheme = theme;
    }

    public static Theme getCurrentTheme() {
        return currentTheme;
    }

    public static void addUpdateListener(Consumer<ThemeUpdateEvent> listener) {
        updateListeners.add(listener);
    }

    public static void removeUpdateListener(Consumer<ThemeUpdateEvent> listener) {
        updateListeners.remove(listener);
    }

    private static Theme getDefaultTheme() {
        return DARK;
    }

    private static String getStylePath(String file) {
        return "file:" + Theme.class.getClassLoader().getResource("wordlegruppe/wordle/ui/css/" + file).getFile();
    }
}
