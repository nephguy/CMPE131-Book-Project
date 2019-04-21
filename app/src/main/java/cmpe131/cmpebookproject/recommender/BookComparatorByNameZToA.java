package cmpe131.cmpebookproject.recommender;

import java.util.Comparator;

import cmpe131.cmpebookproject.book.Book;

public class BookComparatorByNameZToA implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return b2.getTitle().compareTo(b1.getTitle());
    }
}
