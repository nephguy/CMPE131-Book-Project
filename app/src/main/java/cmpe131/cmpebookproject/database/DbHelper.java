package cmpe131.cmpebookproject.database;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.book.Genre;
import cmpe131.cmpebookproject.user.ReadingLevel;
import cmpe131.cmpebookproject.user.User;

public class DbHelper {

    private Context context;
    private SerializableDb<User> userDb;
    private CsvDb bookDb;
    private ArrayList<Book> allBooks;

    private static DbHelper singletonInstance;
    public static synchronized DbHelper getInstance(Context context) {
        if (singletonInstance == null)
            singletonInstance = new DbHelper(context);
        return singletonInstance;
    }

    private DbHelper(Context context) {
        this.context = context;
        userDb = new SerializableDb<>(context, context.getString(R.string.database_user_foldername), ".usr");
        bookDb = new CsvDb(context, "[BOOK DB NAME].csv");
    }






    /** used ONLY within this class
     *
     * parses a String of book data and turns it into a Book object **/
    private Book parseBook (String bookData) {

        String title = bookData.substring(0,bookData.indexOf(',')); // trims and returns a substring from the start of the string up to the first comma
        String author = bookData.substring(0,bookData.indexOf(','));
        String publisher = bookData.substring(0,bookData.indexOf(','));
        int yearPublished = Integer.parseInt( bookData.substring(0,bookData.indexOf(',')) ); // does the same, but parses the integer out of it.
        String isbn = ;
        Genre genre = ;
        int numPages = ;
        ReadingLevel readingLevel = ;

        Book newBook = new Book (title, author, publisher, yearPublished, isbn, genre, numPages, readingLevel);
        return newBook;
    }


    /** used globally
     *
     * returns an ArrayList of all books
     * if the books have already been loaded from the database, return them
     * otherwise, retrieve them, parse them into Book objects, store them in the ArrayList allBooks, and return it
     *
     * you will need to use the parseBook(String) method here
     *
     * this method is finished now, but try to understand it**/
    public ArrayList<Book> getAllBooks() {
        if (allBooks != null)
            return allBooks;
        else
            allBooks = new ArrayList<>();

        ArrayList<String> entireFile = null;
        try {
            entireFile = bookDb.readEntireFile();
        } catch (IOException e) {e.printStackTrace();}
        
        for (int i = 0; i < entireFile.size(); i++) {
            allBooks.add(parseBook(entireFile.get(i)));
        }

        return allBooks;
    }


    /** used when logging in
     *
     * returns a User based their name and password
     * if the user database contains a user with the specified name and password, return their user object
     * otherwise, return a null object to let the app know this was an invalid user/password combination **/
    public User getUser(String username, int password) {



    }


    /** used on the login page
     *
     * take a User object and store it within the user database
     * the file name for the new user should be the user's name
     * if a user with the same name already exists, throw an exception and do NOT store it
     *
     * example of throwing an exception:
     *    throw new IllegalArgumentException ("reason for the exception") **/
    public void addNewUser (User user) throws IllegalArgumentException {



    }


    /** used on the profile page
     *
     * edit a user's data within the user database
     * given their name, find the corresponding row in the user database and replace it with the new User object **/
    public void editUser (String userName, User newUserData) {



    }


    /** used on the profile page
     *
     * delete a user from the user database
     * given their name, find the corresponding  in the user database and delete it **/
    public void deleteUser (String userName) {



    }
}
