package wordlegruppe.wordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import wordlegruppe.wordle.ui.controllers.EndscreenController;
import wordlegruppe.wordle.ui.controllers.GameController;
import wordlegruppe.wordle.ui.controllers.ThemeDemoController;
import wordlegruppe.wordle.ui.natives.NativeUtilities;
import wordlegruppe.wordle.ui.natives.WndProc;
import wordlegruppe.wordle.ui.themes.Theme;
import wordlegruppe.wordle.ui.themes.ThemeUpdateEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        Theme.init();

        scene = new Scene(GameController.load()/*loadFXML("primary")*/, 600, 600);
        mainStage = stage;

        stage.setScene(scene);
        stage.setTitle("Wordle");

        InputStream icon = getClass().getResourceAsStream("icon-p2.png");
        assert icon != null;
        stage.getIcons().add(new Image(icon));
        stage.setResizable(false);
        stage.show();

        Theme.addUpdateListener(this::onThemeChanged);
        NativeUtilities.customizeCation(stage, Theme.getCurrentTheme().getCaptionColor());
    }

    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }

    private void onThemeChanged(ThemeUpdateEvent e) {
        NativeUtilities.setCaptionColor(mainStage, e.getNewTheme().getCaptionColor());
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getUIResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static URL getUIResource(String name) {
        return App.class.getResource("/wordlegruppe/wordle/ui/" + name);
    }

    public static void main(String[] args) {
        launch();
    }

}