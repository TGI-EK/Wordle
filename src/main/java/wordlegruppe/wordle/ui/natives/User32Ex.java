package wordlegruppe.wordle.ui.natives;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.win32.W32APIOptions;

/**
 * Define Win32Api functions to use
 * @author YetiHafen
 */
public interface User32Ex extends User32 {
    User32Ex INSTANCE = Native.load("user32", User32Ex.class, W32APIOptions.DEFAULT_OPTIONS);

    // https://docs.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-setwindowlongptra
    LONG_PTR SetWindowLongPtr( HWND hWnd, int nIndex, WindowProc wndProc );
    LONG_PTR SetWindowLongPtr( HWND hWnd, int nIndex, LONG_PTR wndProc );

    // https://docs.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-callwindowproca
    LRESULT CallWindowProc( LONG_PTR lpPrevWndFunc, HWND hWnd, int uMsg, WPARAM wParam, LPARAM lParam );

    // https://docs.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-iszoomed
    boolean IsZoomed( HWND hWnd );

    // https://docs.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-drawframecontrol
    boolean DrawFrameControl(HDC hdc, RECT rect, int uType, int uState);

    // https://docs.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-getdpiforwindow
    int GetDpiForWindow( HWND hwnd );

    // https://docs.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-getsystemmetricsfordpi
    int GetSystemMetricsForDpi( int nIndex, int dpi );
}
