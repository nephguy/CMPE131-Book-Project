package cmpe131.cmpebookproject.book;

import cmpe131.cmpebookproject.user.ReadingLevel;

public class Book {


    private String title;
    private String author;
    private String publisher;
    private int yearPublished;
    private String isbn;
    private Genre genre;
    private int numPages;
    private ReadingLevel readingLevel;

    public Book(String title, String author, String publisher, int yearPublished, String isbn, Genre genre, int numPages, ReadingLevel readingLevel) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.isbn = isbn;
        this.genre = genre;
        this.numPages = numPages;
        this.readingLevel = readingLevel;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public String getPublisher() {return publisher;}
    public void setPublisher(String publisher) {this.publisher = publisher;}
    public int getYearPublished() {return yearPublished;}
    public void setYearPublished(int yearPublished) {this.yearPublished = yearPublished;}
    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}
    public Genre getGenre() {return genre;}
    public void setGenre(Genre genre) {this.genre = genre;}
    public int getNumPages() {return numPages;}
    public void setNumPages(int numPages) {this.numPages = numPages;}
    public ReadingLevel getReadingLevel() {return readingLevel;}
    public void setReadingLevel(ReadingLevel readingLevel) {this.readingLevel = readingLevel;}

}

