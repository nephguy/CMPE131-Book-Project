package cmpe131.cmpebookproject.activity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.search.SearchCriteria;

public class ListActivitySearch extends ListActivityBase {

    SearchCriteria searchCriteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionButton.setVisibility(View.GONE);
        searchCriteria = getIntent().getParcelableExtra(Util.INTENT_DATA_SEARCHCRITERIA);
        ArrayList<Book> searchResults = searchCriteria.getSearchResults();
        updateList(searchResults);
//        for (Book b : searchResults) {
//            System.out.println(b.getSimilarityIndex() + " " + b.toString());
//        }
    }
}
