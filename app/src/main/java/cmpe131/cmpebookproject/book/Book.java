package cmpe131.cmpebookproject.book;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.activity.BookInfoActivity;
import cmpe131.cmpebookproject.Listable;

public class Book implements Parcelable, Listable {

    private String title;
    private String author;
    private String isbn;
    private float rating;
    private String publisher;
    private int numPages;
    private int yearPublished;
    private Genre genre;

    public Book(String title, String author, String isbn, float rating, String publisher,  int numPages, int yearPublished, Genre genre) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.rating = rating;
        this.publisher = publisher;
        this.numPages = numPages;
        this.yearPublished = yearPublished;
        this.genre = genre;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public String getPublisher() {return publisher;}
    public void setPublisher(String publisher) {this.publisher = publisher;}
    public int getYearPublished() {return yearPublished;}
    public void setYearPublished(int yearPublished) {this.yearPublished = yearPublished;}
    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}
    public Genre getGenre() {return genre;}
    public void setGenre(Genre genre) {this.genre = genre;}
    public int getNumPages() {return numPages;}
    public void setNumPages(int numPages) {this.numPages = numPages;}
    public float getRating() {return rating;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(isbn);
        dest.writeFloat(rating);
        dest.writeString(publisher);
        dest.writeInt(numPages);
        dest.writeInt(yearPublished);
        dest.writeString(genre.toString());
    }

    public Book(Parcel in)
        {
            title = in.readString();
            author = in.readString();
            isbn = in.readString();
            rating = in.readFloat();
            publisher = in.readString();
            numPages = in.readInt();
            yearPublished = in.readInt();
            genre = Genre.getEnum(in.readString());
        }

    public static final Parcelable.Creator<Book> CREATOR
                = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
            }

        public Book[] newArray(int size) {
                return new Book[size];
            }
        };



    @Override
    public void populateListView(View listView) {
        TextView title = listView.findViewById(R.id.listitem_book_label_title);
        title.setText(this.title);
        TextView author = listView.findViewById(R.id.listitem_book_label_author);
        author.setText(this.author);
        TextView yearGenre = listView.findViewById(R.id.listitem_book_label_yearANDgenre);
        yearGenre.setText(this.yearPublished + " - " + this.genre);
        TextView pageCount = listView.findViewById(R.id.listitem_book_label_pcount);
        pageCount.setText(this.numPages + " pages");
        TextView rating = listView.findViewById(R.id.listitem_book_label_rating);
        rating.setText(this.rating + "/5");
    }

    @Nullable
    @Override
    public Intent getDisplayIntent(Context context) {
        Intent displayIntent = new Intent(context, BookInfoActivity.class);
        displayIntent.putExtra(Util.INTENT_DATA_BOOK, this);
        return displayIntent;
    }

    @Override
    public int getListViewLayoutRes() {
        return R.layout.listitem_book;
    }
}


