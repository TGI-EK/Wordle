package wordlegruppe.wordle.ui.natives;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.win32.W32APIOptions;

public interface User32Ex extends User32 {
    User32Ex INSTANCE = Native.load("user32", User32Ex.class, W32APIOptions.DEFAULT_OPTIONS);

    LONG_PTR SetWindowLongPtr( HWND hWnd, int nIndex, WindowProc wndProc );
    LONG_PTR SetWindowLongPtr( HWND hWnd, int nIndex, LONG_PTR wndProc );
    LONG_PTR SetWindowLong( HWND hWnd, int nIndex, WindowProc wndProc );
    LONG_PTR SetWindowLong( HWND hWnd, int nIndex, LONG_PTR wndProc );
    LRESULT CallWindowProc( LONG_PTR lpPrevWndFunc, HWND hWnd, int uMsg, WPARAM wParam, LPARAM lParam );
    boolean IsZoomed( HWND hWnd );

    int GetDpiForWindow( HWND hwnd );
    int GetSystemMetricsForDpi( int nIndex, int dpi );
}
