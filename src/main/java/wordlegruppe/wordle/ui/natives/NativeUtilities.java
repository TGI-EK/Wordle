package wordlegruppe.wordle.ui.natives;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.lang.reflect.Method;

public class NativeUtilities {

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

}
