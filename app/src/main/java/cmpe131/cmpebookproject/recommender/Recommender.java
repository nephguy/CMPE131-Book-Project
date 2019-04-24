package cmpe131.cmpebookproject.recommender;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.user.User;

/**
 * Recommender finds books in the database that match the Users preferences
 */
public class Recommender {

    private User currentUser;
    private ArrayList<Book> database;
    private ArrayList<Book> filteredBooks;
    private PriorityQueue<Book> recommendedBooks;
    private ArrayList<Book> relevantOrderedBooks;
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
        //! need to add getAverageRating method in Book
        //this is a min heap priority queue ordered by rating
        this.recommendedBooks = new PriorityQueue<Book>(numberOfBooksRecommended + 1, new Comparator<Book>()
                                {
                                @Override
                                public int compare(Book book1, Book book2) {
                                   return (int)(book1.getRating() - book2.getRating()); }
                                });
        this.relevantOrderedBooks = new ArrayList<Book>();
        this.numberOfBooksRecommended = numberOfBooksRecommended;
    }

    /**
     * Returns an array list of books that match the users preferences loosely ordered by highest rating
     * @return - books recommended to the user as an ArrayList<Book>
     */
    public ArrayList<Book> produceRecommendedBooks(){
        filterBooks();
        makeRecommendedBookList();
        putRecommendedBooksInRelevantOrder();

        return relevantOrderedBooks;
    }

    /**
     * Finds books from the database that matches the Users preferences
     * and filters them into an ArrayList<Book> called filteredBooks
     */
    //! Only filters by genre as of now
    public void filterBooks(){
        //filter in books that contain a liked genre of the user
        for(Book book : database){
            if(currentUser.getLikedGenres().contains(book.getGenre())) {
                filteredBooks.add(book);
            }
        }

        //!SHOULD ADD MORE Ways to filter here by taking into account only the users profile
    }

    /**
     * puts the top rated elements in reverse heap order (min to max rating)
     * This algorithm uses constant memory which is the number of books to recommend.
     */
    public void makeRecommendedBookList()
    {
        for(Book book : filteredBooks){
            recommendedBooks.add(book);
            if(recommendedBooks.size() > numberOfBooksRecommended){
                recommendedBooks.poll();
            }
        }

    }

    /**
     * Reorderes the books so that they are ordered from greatest to least rating
     * and stores the elements into ArrayList<Book> relevantOrderedBooks
     */
    public void putRecommendedBooksInRelevantOrder(){
        PriorityQueue<Book> orderedRecommended = new PriorityQueue<Book>(numberOfBooksRecommended, new Comparator<Book>()
            {
                @Override
                public int compare(Book book1, Book book2) {
                return (int)(book2.getRating() - book1.getRating()); }
            });

        orderedRecommended.addAll(recommendedBooks);

        relevantOrderedBooks.addAll(orderedRecommended);
    }

    /**
     * Gets the currentUser
     * @return - currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }
    /**
     * Sets a new currentUser (probably shouldn't be used)
     * @param currentUser - new user to be currentUser
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    /**
     * Gets the database of books
     * @return - the database of books
     */
    public ArrayList<Book> getDatabase() {
        return database;
    }
    /**
     * Sets a new databse with specified ArrayList (probably shouldn't be used)
     * @param database - new database to be set to database
     */
    public void setDatabase(ArrayList<Book> database) {
        this.database = database;
    }
    /**
     * Gets the filtered books array list
     * @return - the filtered books (unordered)
     */
    public ArrayList<Book> getFilteredBooks() {
        return filteredBooks;
    }
    /**
     * Sets new filteredBooks with specified ArrayList (probably shouldn't be used)
     * @param filteredBooks - new filtered books to be set to filteredBooks
     */
    public void setFilteredBooks(ArrayList<Book> filteredBooks) {
        this.filteredBooks = filteredBooks;
    }
    /**
     * Gets the recommendedBooks
     * @return - the recommended books ordered by rating (highest to lowest)
     */
    public PriorityQueue<Book> getRecommendedBooks() {
        return recommendedBooks;
    }
    /**
     * Sets new recommendedBooks with specified ArrayList (probably shouldn't be used)
     * @param recommendedBooks - new recommended books to be set to recommendedBooks
     */
    public void setRecommendedBooks(PriorityQueue<Book> recommendedBooks) {
        this.recommendedBooks = recommendedBooks;
    }
    /**
     * Gets the max number of books to be recommended to the user
     * @return - the number of books that should be recommended to the user
     */
    public int getNumberOfBooksRecommended() {
        return numberOfBooksRecommended;
    }
    /**
     * Sets the max number of books to be recommended with specified int value
     * @param numberOfBooksRecommended - new number of books to be recommended to numberOfBooksRecommended
     */
    public void setNumberOfBooksRecommended(int numberOfBooksRecommended) {
        this.numberOfBooksRecommended = numberOfBooksRecommended;
    }
}


