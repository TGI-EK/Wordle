package wordlegruppe.wordle.ui.natives;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import wordlegruppe.wordle.ui.controllers.SecondaryController;
import wordlegruppe.wordle.ui.natives.structs.DWMWINDOWATTRIBUTE;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * utilities for interactions with the Win32Api
 * @author YetiHafen
 */
public class NativeUtilities {

    /**
     * *should* return the HWND for the Specified Stage
     * might not, because JavaFX ist stupid and has no way
     * to do this
     * @param stage the Stage
     * @return hopefully the HWND for the correct stage
     */
    public static WinDef.HWND getHwnd(Stage stage) {
        try {
            return getNativeHandleForStage(stage);
        } catch(Exception ignored) {
            // bad hack to use when jar is not shaded and can't access internal functions
            String title = stage.getTitle();
            return User32.INSTANCE.FindWindow(null, title);
        }
    }

    // the giant waste of time
    private static WinDef.HWND getNativeHandleForStage(Stage stage) throws Exception {
        final Method getPeer = Window.class.getDeclaredMethod("getPeer",null);
        getPeer.setAccessible(true);
        final Object tkStage = getPeer.invoke(stage);

        final Method getRawHandle = tkStage.getClass().getMethod("getRawHandle");
        getRawHandle.setAccessible(true);
        final Pointer pointer = new Pointer((Long) getRawHandle.invoke(tkStage));
        return new WinDef.HWND(pointer);
    }

    public static void addFrame(Scene scene) {
        Parent appRoot = scene.getRoot();
        FXMLLoader loader = new FXMLLoader(SecondaryController.class.getResource("frame.fxml"));

        try {
            BorderPane borderPane = loader.load();
            borderPane.setCenter(appRoot);
            scene.setRoot(borderPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enables the Immersive Dark Mode for a specified stage
     * officially only supported (documented) since Win 11 Build 22000
     * @param stage the stage to enable the Dark mode for
     * @return if Immersive Dark Mode could be enabled successfully
     */
    public static boolean enableImmersiveDarkMode(Stage stage) {
        WinDef.HWND hWnd = getHwnd(stage);
        WinNT.HRESULT res = DwmApi.INSTANCE.DwmSetWindowAttribute(hWnd, DWMWINDOWATTRIBUTE.DWMWA_USE_IMMERSIVE_DARK_MODE, new IntByReference(1), 4);
        return res.longValue() >= 0;
    }

    /**
     * Sets the Caption Color of the specified Stage to the specified Color
     * this is only officially supported/documented since Win 11 Build 22000
     * @param stage the Stage to change the Caption Color
     * @param color the Color to use
     * @return if the change was successful
     */
    public static boolean setCaptionColor(Stage stage, Color color) {
        WinDef.HWND hWnd = getHwnd(stage);
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);
        // win api accepts the colors in reverse order
        int rgb = red + green * 16 * 16 + blue * 16 * 16 * 16 * 16;
        WinNT.HRESULT res = DwmApi.INSTANCE.DwmSetWindowAttribute(hWnd, DWMWINDOWATTRIBUTE.DWMWA_CAPTION_COLOR, new IntByReference(rgb), 4);
        return res.longValue() >= 0;
    }

}
