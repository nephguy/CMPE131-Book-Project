package cmpe131.cmpebookproject;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

import static android.view.View.FOCUS_DOWN;

/** This is a whole lot of work just to make the virtual keyboard close when you press enter/done **/
public class FocusFixer {

    public static void clearFocus(View v) {
        InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void passFocus(View v) {
        View nextView = v.focusSearch(FOCUS_DOWN);
        if (nextView != null) {
            nextView.requestFocus();
        }
        else
            clearFocus(v);
    }

    public static void setOnKeyListener_clearFocusOnFinish(View v) {
        System.out.println("Applied FIELD_CLEAR_FOCUS_ON_FINISH KeyListener to field " + Util.getIdString(v));
        v.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    clearFocus(v);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public static void setOnKeyListener_passFocusOnFinish(View v) {
        System.out.println("Applied FIELD_PASS_FOCUS_ON_FINISH KeyListener to field " + Util.getIdString(v));
        v.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    passFocus(v);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public static void setAllFieldsClearFocusOnFinish (ViewGroup vg) {
        // could be faster by instead applying KeyListeners while looping to get all fields
        // but this is much more readable and easier to debug
        ArrayList<EditText> allFields = getAllFields(vg, false);
        for (EditText e : allFields)
            setOnKeyListener_clearFocusOnFinish(e);
    }

    public static void setAllFieldsPassFocusOnFinish (ViewGroup vg) {
        // could be faster by instead applying KeyListeners while looping to get all fields
        // but this is much more readable and easier to debug
        ArrayList<EditText> allFields = getAllFields(vg, false);
        for (EditText e : allFields)
            setOnKeyListener_passFocusOnFinish(e);
    }



    // returns a list of all fields within this ViewGroup, INCLUDING those within all sub-ViewGroups
    private static ArrayList<EditText> getAllFields (ViewGroup vg, boolean debug) {
        ArrayList<EditText> fields = new ArrayList<>();
        /*DEBUG*/ if (debug) System.out.println("Finding all fields...");

        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof EditText) {
                /*DEBUG*/ if(debug) System.out.println("Within ViewGroup " + Util.getIdString(vg) + " Found EditText " + Util.getIdString(v));
                fields.add((EditText)v);
            }
            else if (v instanceof ViewGroup) {
                /*DEBUG*/ if(debug) System.out.println("Within ViewGroup " + Util.getIdString(vg) + " Found child ViewGroup " + Util.getIdString(v) + " (child count " + ((ViewGroup) v).getChildCount() + ")");
                if (((ViewGroup) v).getChildCount()!= 0)
                    fields.addAll(getAllFields((ViewGroup)v, debug));
            }
        }

        /*DEBUG*/ if(debug) System.out.println("All fields found.");
        return fields;
    }

}
