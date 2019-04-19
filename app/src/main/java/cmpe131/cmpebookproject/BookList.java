package cmpe131.cmpebookproject;

import java.util.ArrayList;

public class BookList extends ArrayList<Book> {

    private String listName;


    public BookList(String listName) {
        super();
        this.listName = listName;
    }

    public void addBook(Book book)
    {
        this.add(book);
    }
    public String getListName() {return listName;}
    public void setListName(String listName) {this.listName = listName;}
}
