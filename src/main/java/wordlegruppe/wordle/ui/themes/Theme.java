package wordlegruppe.wordle.ui.themes;

import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public enum Theme {
    LIGHT(getStylePath("light.css"), Color.web("#F2F2F2")),
    DARK(getStylePath("dark.css"), Color.web("#3C3F41")),
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
    private static final List<List<String>> usedStylesheets = new ArrayList<>();

    public static void setTheme(Theme theme) {
        Theme prev = currentTheme;
        if(theme == DEFAULT)
            Theme.currentTheme = Theme.defaultTheme;
        else
            Theme.currentTheme = theme;

        usedStylesheets.forEach(list -> {
            list.remove(prev.globalStylesheet);
            list.add(currentTheme.globalStylesheet);
        });
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

    public static void addStylesheetList(List<String> stylesheets) {
        usedStylesheets.add(stylesheets);
        stylesheets.add(currentTheme.globalStylesheet);
    }

    public static void removeUpdateListener(Consumer<ThemeUpdateEvent> listener) {
        updateListeners.remove(listener);
    }

    public static void removeStylesheetList(List<String> stylesheets) {
        usedStylesheets.remove(stylesheets);
    }

    private static Theme getDefaultTheme() {
        return LIGHT;
    }

    private static String getStylePath(String file) {
        URL url = Theme.class.getClassLoader().getResource("wordlegruppe/wordle/ui/css/" + file);
        if (url == null) {
            return null;
        } else {
            return url.toExternalForm();
        }
    }
}
