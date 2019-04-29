package cmpe131.cmpebookproject.book;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import cmpe131.cmpebookproject.AdapterSelectable;
import cmpe131.cmpebookproject.Listable;

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
    public View getListView(LayoutInflater inflater) {
        return null;
    }
}
