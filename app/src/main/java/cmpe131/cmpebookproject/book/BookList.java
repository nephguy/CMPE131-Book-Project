package cmpe131.cmpebookproject.book;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import cmpe131.cmpebookproject.AdapterSelectable;
import cmpe131.cmpebookproject.Listable;
import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.activity.BookListActivity;
import cmpe131.cmpebookproject.activity.ListActivityBase;

public class BookList extends ArrayList<Book> implements Parcelable, Listable, AdapterSelectable {

    private String listName;

    public BookList(String listName) {
        super();
        this.listName = listName;
    }
    public BookList(String listName, ArrayList<Book> bookList) {
        super(bookList);
        this.listName = listName;
    }

    public void addBook(Book book)
    {
        this.add(book);
    }
    public String getListName() {return listName;}
    public void setListName(String listName) {this.listName = listName;}

    


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(listName);
        dest.writeTypedList(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookList> CREATOR = new Creator<BookList>() {
        @Override
        public BookList createFromParcel(Parcel in) {
            return new BookList(in.readString(), in.createTypedArrayList(Book.CREATOR));
        }

        @Override
        public BookList[] newArray(int size) {
            return new BookList[size];
        }
    };


    @Override
    public int getListViewLayoutRes() {
        return R.layout.listitem_booklist;
    }

    @Override
    public void populateListView(View listView) {
        TextView listTitle = listView.findViewById(R.id.listitem_booklist_label_listtitle);
        listTitle.setText(listName);
        TextView numBooks = listView.findViewById(R.id.listitem_booklist_label_numbooks);
        int size = this.size();
        if (size == 1) numBooks.setText("1 book");
        else numBooks.setText(size + " books");
    }

    @Nullable @Override
    public Intent getDisplayIntent(Context context) {
        Intent displayIntent = new Intent(context, BookListActivity.class);
        displayIntent.putExtra(Util.INTENT_DATA_LIST_DATASET, (Parcelable)this);
        displayIntent.putExtra(Util.INTENT_DATA_LIST_LISTTITLE, this.listName);
        displayIntent.putExtra(Util.INTENT_DATA_LIST_LAYOUTRES, getListViewLayoutRes());
        return displayIntent;
    }

}
