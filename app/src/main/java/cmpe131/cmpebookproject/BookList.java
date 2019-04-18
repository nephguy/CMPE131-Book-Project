package cmpe131.cmpebookproject;

import java.util.ArrayList;

public class BookList extends ArrayList<Book> {

    private String listName;

    public BookList() {
        super();
        listName = "";
    }

    public BookList(String listName) {
        super();
        this.listName = listName;
    }

    public String getListName() {return listName;}
    public void setListName(String listName) {this.listName = listName;}
}
