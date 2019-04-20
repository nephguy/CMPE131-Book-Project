package cmpe131.cmpebookproject.recommender;

import java.util.ArrayList;
import java.util.PriorityQueue;

import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.user.User;

/**
 *
 */
public class Recommender {

    private User currentUser;
    private ArrayList<Book> database;
    private ArrayList<Book> filteredBooks;
    private PriorityQueue<Book> recommendedBooks;
    private int numberOfBooksRecommended;

    /**
     * Constructor for Recommender
     * @param currentUser
     * @param database
     * @param numberOfBooksRecommended
     */
    public Recommender(User currentUser, ArrayList<Book> database, int numberOfBooksRecommended){
        this.currentUser = currentUser;
        this.database = database;
        this.filteredBooks = new ArrayList<>();
        this.recommendedBooks = new PriorityQueue<>();
        this.numberOfBooksRecommended = numberOfBooksRecommended;
    }

    public void produceRecommendedBooks(){

    }

    /**
     *
     * @return - currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }
    /**
     *
     * @param currentUser - new user to be currentUser
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    /**
     *
     * @return - the database of books
     */
    public ArrayList<Book> getDatabase() {
        return database;
    }
    /**
     *
     * @param database - new database to be set to database
     */
    public void setDatabase(ArrayList<Book> database) {
        this.database = database;
    }
    /**
     *
     * @return - the filtered books (unordered)
     */
    public ArrayList<Book> getFilteredBooks() {
        return filteredBooks;
    }
    /**
     *
     * @param filteredBooks - new filtered books to be set to filteredBooks
     */
    public void setFilteredBooks(ArrayList<Book> filteredBooks) {
        this.filteredBooks = filteredBooks;
    }
    /**
     *
     * @return - the recommended books ordered by rating (highest to lowest)
     */
    public PriorityQueue<Book> getRecommendedBooks() {
        return recommendedBooks;
    }
    /**
     *
     * @param recommendedBooks - new recommended books to be set to recommendedBooks
     */
    public void setRecommendedBooks(PriorityQueue<Book> recommendedBooks) {
        this.recommendedBooks = recommendedBooks;
    }
    /**
     *
     * @return - the number of books that should be recommended to the user
     */
    public int getNumberOfBooksRecommended() {
        return numberOfBooksRecommended;
    }
    /**
     *
     * @param numberOfBooksRecommended - new number of books to be recommended to numberOfBooksRecommended
     */
    public void setNumberOfBooksRecommended(int numberOfBooksRecommended) {
        this.numberOfBooksRecommended = numberOfBooksRecommended;
    }
}


