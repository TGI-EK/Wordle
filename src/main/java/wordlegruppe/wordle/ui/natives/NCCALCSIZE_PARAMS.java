package wordlegruppe.wordle.ui.natives;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

@Structure.FieldOrder("rgrc")
public class NCCALCSIZE_PARAMS extends Structure {

    public WinDef.RECT[] rgrc = new WinDef.RECT[1];

    public NCCALCSIZE_PARAMS(Pointer p) {
        super(p);
        read();
    }
}
