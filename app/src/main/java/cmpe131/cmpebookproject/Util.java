package cmpe131.cmpebookproject;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Util {

    /** Intent Flags and Keys **/

    public static final int REQUEST_CREATE_ACCOUNT = 17371;
    public static final String INTENT_DATA_NEWUSER_USERNAME = "INTENT_DATA_NEWUSER_USERNAME";
    public static final String INTENT_DATA_BOOK = "INTENT_DATA_BOOK";
    public static final String INTENT_DATA_LIST_LISTTITLE = "INTENT_DATA_LIST_LISTTITLE";
    public static final String INTENT_DATA_LIST_DATASET = "INTENT_DATA_LIST_DATASET";
    public static final String INTENT_DATA_LIST_LAYOUTRES = "INTENT_DATA_LIST_LAYOUTRES";
    public static final String INTENT_DATA_LIST_LABELIFEMPTY = "INTENT_DATA_LIST_LABELIFEMPTY";
    public static final String INTENT_DATA_SEARCHCRITERIA = "INTENT_DATA_SEARCHCRITERIA";


    /** Utility Methods **/

    public static <T extends Enum> void setSpinnerSelection (Spinner spinner, Enum o) {
        int itemPos = ((ArrayAdapter<T>)spinner.getAdapter()).getPosition((T)o);
        //System.out.println("INDEX OF " + o + " IN SPINNER: " + itemPos);
        spinner.setSelection(itemPos);
    }

    public static void setOnKeyListener_fieldPressButtonOnFinish(final EditText field, final View button) {
        System.out.println("Applied FIELD_PRESS_BUTTON_ON_FINISH KeyListener to field " + getIdString(field));
        field.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (button == null) {
                    throw new IllegalArgumentException("Button was not instantiated before passing into this function");
                    //System.out.println("ERROR: BUTTON WAS NOT INSTANTIATED BEFORE PASSING INTO FUNCTION");
                    //return false;
                }
                else if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    FieldFocusTools.clearFocus(field);
                    button.callOnClick();
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

    public static long generateSerialUID (String s) {
        String serialUID = "";
        StringBuilder sb = new StringBuilder(serialUID);
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if (Character.isAlphabetic(c))
                sb.append(Character.getNumericValue(c));
            if (Character.isDigit(c))
                sb.append(c);
        }
        return Long.parseLong(sb.toString());
    }

    public static void shortToast (Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void shortToast(String text) {
        shortToast(ApplicationManager.getContext(), text);
    }

    public static void longToast (Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void longToast(String text) {
        longToast(ApplicationManager.getContext(), text);
    }

    public AlertDialog.Builder styleFixedAlertDialogBuilder(Context context) {
        return new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.DialogTheme));
    }

    /** only used because of a weird bug with checkboxes made from xml layout files.
     *
     *  See {@link cmpe131.cmpebookproject.R.layout#view_checkbox XML CheckBox}
     * **/
    public static CheckBox makeCheckBoxWithMargin(Context context) {
        CheckBox checkBox = new CheckBox(context);

        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 32, 16);
        checkBox.setLayoutParams(params);

        return checkBox;
    }

    public static void debugListAllChildViews(View view) {
        if (!(view instanceof ViewGroup)) {
            System.out.println(getIdString(view) + " is not a ViewGroup, and hence has no child views");
            return;
        }
        ViewGroup vg = (ViewGroup)view;

        System.out.println("Finding all views within " + getIdString(vg));

        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof ViewGroup) {
                int childCount = ((ViewGroup)v).getChildCount();
                System.out.println("Within ViewGroup " + getIdString(vg) + " Found child ViewGroup " + getIdString(v) + " (child count " + childCount + ")");
                if (childCount != 0)
                    debugListAllChildViews(v);
            }
            else {
                System.out.println("Within ViewGroup " + getIdString(vg) + " Found View " + getIdString(v));
            }
        }

        System.out.println("All views found.");
    }

    public static void debugPrintList(List<?> list) {
        System.out.println("LIST CONTENTS: ");
        for (Object o : list) {
            System.out.println(o.toString());
        }
        System.out.println("END OF LIST");
    }

}
