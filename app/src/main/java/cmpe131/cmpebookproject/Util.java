package cmpe131.cmpebookproject;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Util {

    public static <T extends Enum> void setSpinnerSelection (Spinner spinner, Enum o) {
        int itemPos = ((ArrayAdapter<T>)spinner.getAdapter()).getPosition((T)o);
        //System.out.println("INDEX OF " + o + " IN SPINNER: " + itemPos);
        spinner.setSelection(itemPos);
    }

    public static void setOnKeyListener_fieldPressButtonOnFinish(final View v, final Button b) {
        System.out.println("Applied FIELD_PRESS_BUTTON_ON_FINISH KeyListener to field " + getIdString(v));
        v.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (b == null) {
                    System.out.println("ERROR: BUTTON WAS NOT INSTANTIATED BEFORE PASSING INTO FUNCTION");
                    return false;
                }
                else if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    FocusFixer.clearFocus(v);
                    b.callOnClick();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public static String getIdString(View v) {
        return v.getContext().getResources().getResourceEntryName(v.getId());
    }

}
