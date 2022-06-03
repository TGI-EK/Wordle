package wordlegruppe.wordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import wordlegruppe.wordle.ui.controllers.GameController;
import wordlegruppe.wordle.ui.controllers.TitleController;
import wordlegruppe.wordle.ui.natives.NativeUtilities;
import wordlegruppe.wordle.ui.themes.Theme;
import wordlegruppe.wordle.ui.themes.ThemeUpdateEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {

    private static App instance;
    private static Scene scene;
    private static Stage mainStage;

    private Object controller;

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        Theme.init();

        scene = new Scene(TitleController.getLoader().load(), 600, 600);
        controller = TitleController.getLoader().getController();
        mainStage = stage;

        stage.setScene(scene);
        stage.setTitle("Wordle");

        InputStream icon = getClass().getResourceAsStream("icon-p2.png");
        assert icon != null;
        stage.getIcons().add(new Image(icon));
        stage.setResizable(false);
        stage.show();

        Theme.addUpdateListener(this::onThemeChanged);
        scene.addEventHandler(KeyEvent.KEY_TYPED, this::onKeyTyped);
        NativeUtilities.customizeCation(stage, Theme.getCurrentTheme().getCaptionColor());

        if(isWin10())
            NativeUtilities.reopenWindow(mainStage);

        System.out.println(System.getProperties());
    }

    private void onThemeChanged(ThemeUpdateEvent e) {
        NativeUtilities.customizeCation(mainStage, e.getNewTheme().getCaptionColor());
        if(isWin10())
            NativeUtilities.reopenWindow(mainStage);
    }

    private void onKeyTyped(KeyEvent e) {
        // only trigger if GameController ist used
        if(!(controller instanceof GameController c)) return;
        c.onKeyTyped(e);
    }

    public static void setRoot(FXMLLoader loader) {
        try {
            scene.setRoot(loader.load());
            instance.controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isWin10() {
        return isWin() && System.getProperty("os.version").equals("10.0");
    }

    public static boolean isWin() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    public static FXMLLoader getFXMLLoader(String fxml) {
        return new FXMLLoader(getUIResource(fxml + ".fxml"));
    }

    public static Scene getScene() {
        return scene;
    }

    public static URL getUIResource(String name) {
        return App.class.getResource("/wordlegruppe/wordle/ui/" + name);
    }

    public static void main(String[] args) {
        launch();
    }

}