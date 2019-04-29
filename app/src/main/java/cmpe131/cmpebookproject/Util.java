package cmpe131.cmpebookproject;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Util {

    public static final int REQUEST_CREATE_ACCOUNT = 17371;
    public static final String KEY_DATA_ACTIVEUSER = "KEY_DATA_ACTIVEUSER";
    public static final String INTENT_DATA_USER = "INTENT_DATA_USER";
    public static final String INTENT_DATA_USERNAME = "INTENT_DATA_USERNAME";
    public static final String INTENT_DATA_LISTTITLE = "INTENT_DATA_LISTTITLE";
    public static final String INTENT_DATA_ARRAYTOLIST = "INTENT_DATA_ARRAYTOLIST";
    public static final String INTENT_DATA_LIST_FORCESHOWEMPTYLIST = "INTENT_DATA_LIST_FORCESHOWEMPTYLIST";
    public static final String INTENT_DATA_BOOK = "INTENT_DATA_BOOK";


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
        shortToast(ApplicationContextProvider.getContext(), text);
    }

    public static void longToast (Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void longToast(String text) {
        longToast(ApplicationContextProvider.getContext(), text);
    }

}
