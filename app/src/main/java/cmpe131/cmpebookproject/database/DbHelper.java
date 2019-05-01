package cmpe131.cmpebookproject.database;

import android.content.Context;


import java.util.ArrayList;
import java.util.Collections;

import cmpe131.cmpebookproject.ApplicationManager;
import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.book.Genre;
import cmpe131.cmpebookproject.user.User;

public class DbHelper {

    private Context context;
    //private SerializableDb<User> userDb;
    private UserDB userDb;
    private CsvDb bookDb;
    private ArrayList<Book> allBooks;

    private static DbHelper singletonInstance;
    public static synchronized DbHelper getInstance(Context context) {
        if (singletonInstance == null)
            singletonInstance = new DbHelper(context);
        return singletonInstance;
    }
    public static synchronized DbHelper getInstance() {
        return getInstance(ApplicationManager.getContext());
    }

    private DbHelper(Context context) {
        this.context = context;
        //userDb = new SerializableDb<>(context, context.getString(R.string.database_user_foldername), ".usr");
        userDb = new UserDB(context, "UserDb.ser");
        bookDb = new CsvDb(context, "bookDB.csv");
    }






    private Book parseBook (String bookData) {
        String[] bookparam = parseCsvData(bookData);
        return new Book(bookparam[0],
                bookparam[1],
                bookparam[2],
                Float.parseFloat(bookparam[3]),
                bookparam[4],
                Integer.parseInt(bookparam[5]),
                Integer.parseInt(bookparam[6]),
                Genre.getEnum(bookparam[7]));
    }

    // because some data values have commas in them, so they can't easily be retrieved.
    // those that do are surrounded by quotes
    private String[] parseCsvData (String csvData) {
        ArrayList<String> data = new ArrayList<>();

        // find each value and cut it out
        // if there are quotes, use them as reference
        // if not, use the comma as reference
        while(!csvData.equals("")) {
            if (csvData.indexOf('"') < 0) {
                Collections.addAll(data,csvData.split(","));
                break;
            }
            else if (csvData.indexOf('"') < csvData.indexOf(',')) {
                data.add(csvData.substring(1,csvData.indexOf('"',1)));
                csvData = csvData.substring(csvData.indexOf('"',1)+2);
            }
            else {
                data.add(csvData.substring(0,csvData.indexOf(',')));
                csvData = csvData.substring(csvData.indexOf(',')+1);
            }
        }

        /* FOR DEBUGGING

        for (String s : data)
            System.out.print(s + "|");
        System.out.println();
        */

        String[] dataArray = new String[data.size()];
        return data.toArray(data.toArray(dataArray));
    }

    public ArrayList<Book> getAllBooks() {
        if (allBooks != null)
            return allBooks;
        else
            allBooks = new ArrayList<>();

        ArrayList<String> bookData = bookDb.readEntireFile();
        for (int i = 1; i < bookData.size(); i++) {
            allBooks.add(parseBook(bookData.get(i)));
        }

        return allBooks;
    }

    public User getUser (String username, String password) {
        return getUser(username, password.hashCode());
    }

    public User getUser (String username, int passwordHash) {
        for (User u : userDb.getUserList()) {
            boolean userMatch = u.getName().equals(username);
            boolean passMatch = (u.getPasswordHash() == passwordHash);
            if (userMatch && passMatch)
                return u;
        }
        return null;
    }


    public boolean addUser(User user) {
        if (usernameTaken(user.getName()))
            return false;
        else if (userDb.getUserList().add(user)) {
            userDb.write();
            return true;
        }
        return false;
    }


    public boolean appendUser (User oldUserData, User newUserData) {
        boolean delete = userDb.getUserList().remove(oldUserData);
        boolean add = userDb.getUserList().add(newUserData);
        userDb.write();
        return delete && add;
    }

    public boolean appendUser (User userData) {
        User oldUser = null;
        for (User u : userDb.getUserList()) {
            if (u.getName().equals(userData.getName()))
                oldUser = u;
        }
        if (oldUser == null)
            return false;

        boolean delete = userDb.getUserList().remove(oldUser);
        boolean add = userDb.getUserList().add(userData);
        userDb.write();
        return delete && add;
    }


    public boolean deleteUser (User user) {
        boolean success = userDb.getUserList().remove(user);
        userDb.write();
        return success;
    }

    public boolean usernameTaken(String name) {
        ArrayList<String> userNames = new ArrayList<>();
        for (User u : userDb.getUserList())
            userNames.add(u.getName());
        return (userNames.contains(name));
    }

}
