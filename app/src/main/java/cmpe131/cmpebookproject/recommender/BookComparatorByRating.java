package cmpe131.cmpebookproject.recommender;

import java.util.Comparator;

import cmpe131.cmpebookproject.book.Book;

public class BookComparatorByRating implements Comparator {
    @Override
    public int compare(Book b1, Book b2) {
        return b1.getRating() - b2.Rating();
    }
}
