package cmpe131.cmpebookproject.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.book.BookList;
import cmpe131.cmpebookproject.database.DbHelper;

public class ListActivityBookList extends ListActivityBase {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AlertDialog deleteListDialog = new AlertDialog.Builder(this)
                .setTitle("Delete List")
                .setMessage("Are you sure you want to delete this list?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (activeUser.removeCustomList( (BookList)dataset )) {
                            DbHelper.getInstance(getApplicationContext()).appendUser(activeUser);
                            finish();
                        }
                        else {
                            Util.shortToast(getApplicationContext(), "Error: could not delete list - no list found with this name");
                        }
                    }
                })
                .create();

        actionButton.setText("Delete This List");
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListDialog.show();
            }
        });
    }
}
