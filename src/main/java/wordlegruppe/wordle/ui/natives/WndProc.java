package wordlegruppe.wordle.ui.natives;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import javafx.stage.Stage;

import static com.sun.jna.platform.win32.WinDef.*;
import static com.sun.jna.platform.win32.ShellAPI.*;

/**
 * https://github.com/microsoft/terminal/blob/main/src/cascadia/WindowsTerminal/NonClientIslandWindow.cpp
 * https://github.com/JFormDesigner/FlatLaf/blob/main/flatlaf-natives/flatlaf-natives-jna/src/main/java/com/formdev/flatlaf/natives/jna/windows/FlatWindowsNativeWindowBorder.java
 */
public class WndProc implements WinUser.WindowProc {

    public static final int WM_NCCALCSIZE = 0x0083;
    public static final int WM_NCHITTEST = 0x0084;
    private static final int ABS_AUTOHIDE = 0x0000001;
    private static final int ABM_GETAUTOHIDEBAREX = 0x0000000b;
    private static final int HTCLIENT = 1;

    private final Stage stage;
    private final HWND hWnd;
    private final BaseTSD.LONG_PTR defWndProc;

    public WndProc(Stage stage) {
        this.stage = stage;
        this.hWnd = NativeUtilities.getHwnd(stage);
        this.defWndProc = User32Ex.INSTANCE.SetWindowLongPtr(hWnd, WinUser.GWL_WNDPROC, this);

        // force window redraw
        RECT rect = new RECT();
        User32.INSTANCE.GetWindowRect(hWnd, rect);
        User32.INSTANCE.SetWindowPos(hWnd, null, rect.left, rect.top, RECTWIDTH(rect), RECTHEIGHT(rect), WinUser.SWP_FRAMECHANGED);
    }

    public void uninstall() {
        User32Ex.INSTANCE.SetWindowLongPtr(hWnd, WinUser.GWL_WNDPROC, defWndProc);
    }

    @Override
    public LRESULT callback(HWND hWnd, int message, WPARAM wParam, LPARAM lParam) {
        return switch (message) {
            case WM_NCCALCSIZE -> WmNcCalcSize(hWnd, message, wParam, lParam);
            case WM_NCHITTEST -> WmNcHitTest(hWnd, message, wParam, lParam);
            default -> User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, message, wParam, lParam);
        };
    }

    private LRESULT WmNcCalcSize(HWND hWnd, int message, WPARAM wParam, LPARAM lParam) {
        // wParam != TRUE
        if(wParam.intValue() != 1)
            return User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, message, wParam, lParam);

        NCCALCSIZE_PARAMS params = new NCCALCSIZE_PARAMS(new Pointer(lParam.longValue()));

        int originalTop = params.rgrc[0].top;

        WinDef.LRESULT lResult = User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, message, wParam, lParam);
        if(lResult.longValue() != 0)
            return lResult;

        params.read();

        params.rgrc[0].top = originalTop;
        params.write();

        boolean isMaximized = User32Ex.INSTANCE.IsZoomed(hWnd);
        boolean isFullscreen = stage.isFullScreen();

        if(isMaximized && !isFullscreen) {
            params.rgrc[0].top += getResizeHandleHeight();

            ShellAPI.APPBARDATA autohide = new ShellAPI.APPBARDATA();
            autohide.cbSize = new WinDef.DWORD(autohide.size());
            int state = Shell32.INSTANCE.SHAppBarMessage(new WinDef.DWORD(ABM_GETSTATE), autohide).intValue();
            if((state & ABS_AUTOHIDE) != 0) {
                WinUser.HMONITOR hMonitor = User32.INSTANCE.MonitorFromWindow(hWnd, WinUser.MONITOR_DEFAULTTONEAREST);
                WinUser.MONITORINFO monitorInfo = new WinUser.MONITORINFO();
                User32Ex.INSTANCE.GetMonitorInfo(hMonitor, monitorInfo);

                if(hasAutohideTaskbar(ABE_TOP, monitorInfo.rcMonitor))
                    params.rgrc[0].top++;
                if(hasAutohideTaskbar(ABE_BOTTOM, monitorInfo.rcMonitor))
                    params.rgrc[0].bottom--;
                if(hasAutohideTaskbar(ABE_LEFT, monitorInfo.rcMonitor))
                    params.rgrc[0].left++;
                if(hasAutohideTaskbar(ABE_RIGHT, monitorInfo.rcMonitor))
                    params.rgrc[0].right--;

            }
        }
        params.write();
        return lResult;
    }

    private LRESULT WmNcHitTest(HWND hWnd, int message, WPARAM wParam, LPARAM lParam) {
        // TODO
        return User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, message, wParam, lParam);
    }

    private int getResizeHandleHeight() {
        int dpi = User32Ex.INSTANCE.GetDpiForWindow(hWnd);
        return User32Ex.INSTANCE.GetSystemMetricsForDpi(WinUser.SM_CXPADDEDBORDER, dpi) +
                User32Ex.INSTANCE.GetSystemMetricsForDpi(WinUser.SM_CYSIZEFRAME, dpi);
    }

    private boolean hasAutohideTaskbar( int edge, RECT rcMonitor ) {
        ShellAPI.APPBARDATA data = new ShellAPI.APPBARDATA();
        data.cbSize = new DWORD( data.size() );
        data.uEdge = new UINT( edge );
        data.rc = rcMonitor;
        UINT_PTR hTaskbar = Shell32.INSTANCE.SHAppBarMessage( new DWORD( ABM_GETAUTOHIDEBAREX ), data );
        return hTaskbar.longValue() != 0;
    }

    private LRESULT screen2windowCoordinates(HWND hwnd, LPARAM lParam ) {
        // get window rectangle needed to convert mouse x/y from screen to window coordinates
        RECT rcWindow = new RECT();
        User32.INSTANCE.GetWindowRect( hwnd, rcWindow );

        // get mouse x/y in window coordinates
        int x = GET_X_LPARAM( lParam ) - rcWindow.left;
        int y = GET_Y_LPARAM( lParam ) - rcWindow.top;

        return new LRESULT( MAKELONG( x, y ) );
    }

    private int RECTWIDTH(RECT rc) {
        return rc.right - rc.left;
    }

    private int RECTHEIGHT(RECT rc) {
        return rc.bottom - rc.top;
    }

    private int GET_Y_LPARAM(BaseTSD.LONG_PTR lParam) {
        return (short) ((lParam.longValue() >> 16) & 0xffff);
    }
    private int GET_X_LPARAM(BaseTSD.LONG_PTR lParam) {
        return (short) (lParam.longValue() & 0xffff);
    }
    private long MAKELONG( int low, int high ) {
        return (low & 0xffff) | ((long) (high & 0xffff) << 16);
    }
}
