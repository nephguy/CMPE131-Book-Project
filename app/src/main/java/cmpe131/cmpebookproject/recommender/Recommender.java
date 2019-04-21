package cmpe131.cmpebookproject.recommender;

import java.util.ArrayList;
import java.util.Comparator;
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
                                   return book1.getAverageRating() - book2.getAverageRating(); }
                                });
        this.relevantOrderedBooks = new ArrayList<Book>();
        this.numberOfBooksRecommended = numberOfBooksRecommended;
    }

    /**
     * 
     */
    //TODO: setup the methods that are inside
    public ArrayList<Book> produceRecommendedBooks(){
        filterBooks();
        makeRecommendedBookList();
        putRecommendedBooksInRelevantOrder();

        return relevantOrderedBooks;
    }

    /**
     * Finds books that matches the Users data
     */
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
     * puts the top rated elements in reverse heap order (lowest rated are "at the top")
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
     * and stores the elements into relevantOrderedBooks array list
     */
    public void putRecommendedBooksInRelevantOrder(){
        PriorityQueue<Book> orderedRecommended = new PriorityQueue<Book>(new Comparator<Book>()
            {
                @Override
                public int compare(Book book1, Book book2) {
                return book2.getAverageRating() - book1.getAverageRating(); }
            });

        relevantOrderedBooks.addAll(orderedRecommended);
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


