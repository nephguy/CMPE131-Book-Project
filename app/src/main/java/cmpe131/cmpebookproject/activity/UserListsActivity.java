package cmpe131.cmpebookproject.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import cmpe131.cmpebookproject.FocusFixer;
import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.book.BookList;
import cmpe131.cmpebookproject.database.DbHelper;
import cmpe131.cmpebookproject.user.User;

public class UserListsActivity extends ListActivityBase {

    User activeUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activeUser = getIntent().getParcelableExtra(Util.INTENT_DATA_USER);
        checkListEmpty("You don't have any lists yet");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_newitem, null);
        final EditText nameField = dialogView.findViewById(R.id.dialog_field_itemname);
        FocusFixer.setOnKeyListener_clearFocusOnFinish(nameField);
        final AlertDialog createListDialog = new AlertDialog.Builder(this)
                .setTitle("Create New List")
                .setView(dialogView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newListName = nameField.getText().toString();
                        if (newListName.equals("")) {
                            Util.shortToast(getApplicationContext(),"Your list must have a name");
                            return;
                        }
                        BookList newList = new BookList(newListName);
                        activeUser.addCustomList(newList);
                        DbHelper.getInstance(getApplicationContext()).appendUser(activeUser.getName(), activeUser);
                        checkListEmpty("You don't have any lists yet");
                        recyclerViewAdapter.updateList(activeUser.getCustomLists());
                    }
                })
                .create();

        actionButton.setText("Add New List");
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createListDialog.show();
            }
        });
    }
}
