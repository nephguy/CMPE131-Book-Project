package cmpe131.cmpebookproject.book;

import android.os.Parcel;
import android.os.Parcelable;

import cmpe131.cmpebookproject.user.ReadingLevel;

public class Book implements Parcelable {


    private String title;
    private String author;
    private String publisher;
    private int yearPublished;
    private int isbn;
    private Genre genre;
    private int numPages;
    private float rating;

    public Book(String title, String author, int isbn, float rating, String publisher,  int numPages, int yearPublished, Genre genre) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.isbn = isbn;
        this.genre = genre;
        this.numPages = numPages;
        this.rating = rating;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public String getPublisher() {return publisher;}
    public void setPublisher(String publisher) {this.publisher = publisher;}
    public int getYearPublished() {return yearPublished;}
    public void setYearPublished(int yearPublished) {this.yearPublished = yearPublished;}
    public int getIsbn() {return isbn;}
    public void setIsbn(int isbn) {this.isbn = isbn;}
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
        dest.writeString(publisher);
        dest.writeInt(yearPublished);
        dest.writeInt(isbn);
        dest.writeInt(numPages);
    }

    public Book(Parcel in)
        {
            title = in.readString();
            author = in.readString();
            publisher = in.readString();
            yearPublished = in.readInt();
            isbn = in.readInt();
            numPages = in.readInt();
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

}


