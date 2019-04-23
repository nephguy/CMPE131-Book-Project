package cmpe131.cmpebookproject.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.book.BookList;
import cmpe131.cmpebookproject.book.Genre;

public class User implements Serializable, Parcelable {
    private String name;
    private int passwordHash;
    private Gender gender;
    private int age;
    private ReadingHabits readingHabits;
    private ArrayList<Genre> likedGenres;
    private ArrayList<Genre> dislikedGenres;
    private ArrayList<Book> ratedBooks;
    private BookList recommendedList;
    private ArrayList<BookList> customLists;

    public User(String name, int passwordHash, Gender gender, int age, ReadingHabits readingHabits, ArrayList<Genre> likedGenres, ArrayList<Genre> dislikedGenres, ArrayList<Book> ratedBooks, BookList recommendedList, ArrayList<BookList> customLists) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.gender = gender;
        this.age = age;
        this.readingHabits = readingHabits;
        this.likedGenres = likedGenres;
        this.dislikedGenres = dislikedGenres;
        this.ratedBooks = ratedBooks;
        this.recommendedList = recommendedList;
        this.customLists = customLists;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getPasswordHash() {return passwordHash;}
    public void setPassword(String password) {this.passwordHash = password.hashCode();}
    public Gender getGender() {return gender;}
    public void setGender(Gender gender) {this.gender = gender;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public ReadingHabits getReadingHabits() {return readingHabits;}
    public void setReadingHabits(ReadingHabits readingHabits) {this.readingHabits = readingHabits;}
    public ArrayList<Genre> getLikedGenres() {return likedGenres;}
    public void setLikedGenres(ArrayList<Genre> likedGenres) {this.likedGenres = likedGenres;}
    public ArrayList<Genre> getDislikedGenres() {return dislikedGenres;}
    public void setDislikedGenres(ArrayList<Genre> dislikedGenres) {this.dislikedGenres = dislikedGenres;}
    public ArrayList<Book> getRatedBooks() {return ratedBooks;}
    public void setRatedBooks(ArrayList<Book> ratedBooks) {this.ratedBooks = ratedBooks;}
    public BookList getRecommendedList() {return recommendedList;}
    public void setRecommendedList(BookList recommendedList) {this.recommendedList = recommendedList;}
    public ArrayList<BookList> getCustomLists() {return customLists;}
    public void setCustomLists(ArrayList<BookList> customLists) {this.customLists = customLists;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(passwordHash);
        dest.writeString(gender.toString());
        dest.writeInt(age);
        dest.writeString(readingHabits.toString());
        dest.writeTypedList(likedGenres);
        dest.writeTypedList(dislikedGenres);
        dest.writeTypedList(ratedBooks);
        dest.writeString(recommendedList.getListName());
        dest.writeTypedList(recommendedList);
        dest.writeTypedList(customLists);
    }

    protected User(Parcel in) {
        name = in.readString();
        passwordHash = in.readInt();
        gender = Gender.getEnum(in.readString());
        age = in.readInt();
        readingHabits = ReadingHabits.getEnum(in.readString());
        likedGenres = in.createTypedArrayList(Genre.CREATOR);
        dislikedGenres = in.createTypedArrayList(Genre.CREATOR);
        ratedBooks = in.createTypedArrayList(Book.CREATOR);
        recommendedList = new BookList(in.readString(), in.createTypedArrayList(Book.CREATOR));
        customLists = in.createTypedArrayList(BookList.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
