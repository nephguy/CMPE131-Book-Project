package cmpe131.cmpebookproject.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.book.BookList;
import cmpe131.cmpebookproject.database.DbHelper;

public class ListActivityUserLists extends ListActivityBase {

    AlertDialog createListDialog;
    EditText nameField;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_newitem, null);
        nameField = dialogView.findViewById(R.id.dialog_field_itemname);
        nameField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String newListName = ((EditText)view).getText().toString();
                    boolean listCreated = createNewList(newListName);
                    if (listCreated)
                        createListDialog.dismiss();
                    return listCreated;
                } else {
                    return false;
                }
            }
        });

        createListDialog = new AlertDialog.Builder(this)
                .setTitle("Create New List")
                .setView(dialogView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newListName = nameField.getText().toString();
                        createNewList(newListName);
                    }
                })
                .create();

        actionButton.setText("Add New List");
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameField.setText("");
                createListDialog.show();
            }
        });
    }

    private boolean createNewList(String listName) {
        if (listName.equals("")) {
            Util.shortToast(getApplicationContext(),"Your list must have a name");
            return false;
        }
        BookList newList = new BookList(listName);
        activeUser.addCustomList(newList);
        DbHelper.getInstance(getApplicationContext()).appendUser(activeUser);
        updateList(activeUser.getCustomLists());
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateList(activeUser.getCustomLists());
    }
}
