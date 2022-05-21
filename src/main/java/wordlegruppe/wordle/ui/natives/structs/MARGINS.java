package wordlegruppe.wordle.ui.natives.structs;

import com.sun.jna.Structure;

/**
 * @author YetiHafen
 */
@Structure.FieldOrder({"cxLeftWidth", "cxRightWidth", "cyTopHeight", "cyBottomHeight"})
public class MARGINS extends Structure {
    public int cxLeftWidth;
    public int cxRightWidth;
    public int cyTopHeight;
    public int cyBottomHeight;
}
