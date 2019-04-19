package cmpe131.cmpebookproject;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private Gender gender;
    private int age;
    private reading_Habits readingHabits;
    private Genre genre;
    private ArrayList<Book> ratedBooks;
    private ArrayList<BookList> userLists;

    public User(String firstName, String lastName , Gender gender, int age , reading_Habits readingHabits, Genre genre)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age =  age;
        this.readingHabits = readingHabits;
        this.genre =  genre;
        ratedBooks =  new ArrayList<>();
        userLists =  new ArrayList<>();
    }

    public void addRatedBooks(Book b)
    {
        ratedBooks.add(b);
    }

    public void addBookList(BookList b)
    {
        userLists.add(b);
    }

    public void removeRatedBooks(Book b)
    {
        ratedBooks.remove(b);
    }

    public void removeBookList( BookList b)
    {
        userLists.remove(b);
    }



}
