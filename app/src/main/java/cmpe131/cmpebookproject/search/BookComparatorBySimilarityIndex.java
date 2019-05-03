package cmpe131.cmpebookproject.search;

import java.util.Comparator;

import cmpe131.cmpebookproject.book.Book;

public class BookComparatorBySimilarityIndex implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return (int)Math.signum(b2.getSimilarityIndex() - b1.getSimilarityIndex());
    }
}
