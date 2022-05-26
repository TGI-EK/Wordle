package wordlegruppe.wordle.ui.natives;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import javafx.stage.Stage;
import wordlegruppe.wordle.ui.natives.structs.DWMWINDOWATTRIBUTE;
import wordlegruppe.wordle.ui.natives.structs.MARGINS;
import wordlegruppe.wordle.ui.natives.structs.NCCALCSIZE_PARAMS;

import static com.sun.jna.platform.win32.WinDef.*;
import static com.sun.jna.platform.win32.ShellAPI.*;

/**
 * https://github.com/microsoft/terminal/blob/main/src/cascadia/WindowsTerminal/NonClientIslandWindow.cpp
 * https://github.com/JFormDesigner/FlatLaf/blob/main/flatlaf-natives/flatlaf-natives-jna/src/main/java/com/formdev/flatlaf/natives/jna/windows/FlatWindowsNativeWindowBorder.java
 */
public class WndProc implements WinUser.WindowProc {

    private static final int WM_SIZE = 0x0005;
    private static final int WM_ACTIVATE = 0x0006;
    private static final int WM_NCPAINT = 0x0085;
    private static final int WM_NCCALCSIZE = 0x0083;
    private static final int WM_NCHITTEST = 0x0084;
    private static final int WM_NCMOUSEMOVE = 0x00A0;
    private static final int WM_MOUSEMOVE = 0x0200;
    private static final int ABS_AUTOHIDE = 0x0000001;
    private static final int ABM_GETAUTOHIDEBAREX = 0x0000000b;
    private static final int HTCLIENT = 1;
    private static final int HTCAPTION = 2;
    private static final int HTMAXBUTTON = 9;
    private static final int HTCLOSE = 20;
    private static final int HTMINBUTTON = 8;
    private static final int HTSYSMENU = 3;
    private static final int DFC_CAPTION = 1;
    private static final int DFC_BUTTON = 4;
    private static final int DFCS_BUTTON3STATE = 8;
    private static final int DFCS_CAPTIONCLOSE = 0;
    private static final int DCX_WINDOW = 1;
    private static final int DCX_INTERSECTRGN = 128;

    private final Stage stage;
    private final HWND hWnd;
    private BaseTSD.LONG_PTR defWndProc;

    private int wmSizeWparam = 2;

    public WndProc(Stage stage) {
        this.stage = stage;

        //NativeUtilities.addFrame(stage.getScene());

        stage.getScene().rootProperty().addListener((observableValue, oldRoot, newRoot) -> {

            NativeUtilities.addFrame(stage.getScene());
        });

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
            //case WM_NCCALCSIZE -> WmNcCalcSize(hWnd, message, wParam, lParam);
            //case WM_ACTIVATE -> WmActivate(hWnd, message, wParam, lParam);
            //case WM_NCHITTEST -> WmNcHitTest(hWnd, message, wParam, lParam);
            case WM_NCPAINT -> WmNcPaint(hWnd, message, wParam, lParam);
            //case WM_SIZE -> WmSize(hWnd, message, wParam, lParam);
            //case WM_NCMOUSEMOVE -> WmNcMouseMove(hWnd, message, wParam, lParam);
            default -> User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, message, wParam, lParam);
            //default -> User32.INSTANCE.DefWindowProc(hWnd, message, wParam, lParam);
        };
    }

    public LRESULT DefWndProc(HWND hWnd, int message, WPARAM wParam, LPARAM lParam) {
        return User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, message, wParam, lParam);
    }

    private LRESULT WmNcCalcSize(HWND hWnd, int message, WPARAM wParam, LPARAM lParam) {
        // wParam != TRUE
        if(wParam.intValue() != 1) {
            LRESULT lResult = User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, message, wParam, lParam);
            if(lResult.longValue() != 0) return lResult;
        }

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
        LRESULT lResult = User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, message, wParam, lParam);
        //if(lResult.longValue() != HTCLIENT)
            return lResult;

        //return new LRESULT(HTMAXBUTTON);
    }

    private LRESULT WmActivate(HWND hWnd, int message, WPARAM wParam, LPARAM lParam) {
        MARGINS margins = new MARGINS();
        margins.cxLeftWidth = 8;
        margins.cxRightWidth = 8;
        margins.cyBottomHeight = 20;
        margins.cyTopHeight = 37;

        WinNT.HRESULT hr = DwmApi.INSTANCE.DwmExtendFrameIntoClientArea(hWnd, margins);
        // error
        if(hr.longValue() < 0) {
            System.out.println("error on DwmExtendFrameIntoClientArea");
        } else {
            System.out.println("success");
        }
        return User32.INSTANCE.DefWindowProc(hWnd, message, wParam, lParam);
    }

    private LRESULT WmNcPaint(HWND hWnd, int message, WPARAM wParam, LPARAM lParam) {
        LRESULT lResult = User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, message, wParam, lParam);
        HDC hdc = User32Ex.INSTANCE.GetDCEx(hWnd, new HRGN(wParam.toPointer()), DCX_WINDOW | DCX_INTERSECTRGN);
        RECT rc = new RECT();
        User32.INSTANCE.GetClientRect(hWnd, rc);
        //User32Ex.INSTANCE.DrawFrameControl(hdc, rc, DFC_BUTTON, DFCS_BUTTON3STATE);
        User32.INSTANCE.ReleaseDC(hWnd, hdc);
        return lResult;
    }

    private LRESULT WmSize(HWND hWnd, int message, WPARAM wParam, LPARAM lParam) {
        if(wmSizeWparam >= 0)
            wParam = new WPARAM(wmSizeWparam);
        return User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, message, wParam, lParam);
    }

    private LRESULT WmNcMouseMove(HWND hWnd, int message, WPARAM wParam, LPARAM lParam) {
        long wparam = wParam.longValue();
        if(wparam == HTMINBUTTON || wparam == HTMAXBUTTON || wparam == HTCLOSE ||
                wparam == HTCAPTION || wparam == HTSYSMENU )
            sendMessageToClientArea(hWnd, WM_MOUSEMOVE, lParam);
        return DefWndProc(hWnd, message, wParam, lParam);
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

    private void sendMessageToClientArea( HWND hwnd, int uMsg, LPARAM lParam ) {
        // get mouse x/y in window coordinates
        LRESULT xy = screen2windowCoordinates( hwnd, lParam );

        // send message
        User32.INSTANCE.SendMessage( hwnd, uMsg, new WPARAM(), new LPARAM( xy.longValue() ) );
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
