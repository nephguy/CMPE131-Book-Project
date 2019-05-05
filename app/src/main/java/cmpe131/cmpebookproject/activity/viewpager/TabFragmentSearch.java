package cmpe131.cmpebookproject.activity.viewpager;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import cmpe131.cmpebookproject.FieldFocusTools;
import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.activity.ListActivitySearch;
import cmpe131.cmpebookproject.book.Genre;
import cmpe131.cmpebookproject.search.InvalidSearchCriteriaException;
import cmpe131.cmpebookproject.search.SearchCriteria;

public class TabFragmentSearch extends TabFragmentBase {

    EditText title;
    EditText author;
    EditText publishYearFloor;
    EditText publishYearCeiling;
    EditText pcountFloor;
    EditText pcountCeiling;
    FlexboxLayout preferredGenresLayout;
    FlexboxLayout excludedGenresLayout;
    ArrayList<Genre> preferredGenres;
    ArrayList<Genre> excludedGenres;
    Button searchButton;

    @Override
    public void onStart() {
        super.onStart();
        FieldFocusTools.setAllFieldsPassFocusOnFinish((ViewGroup)getView().findViewById(R.id.search_layout_const));

        title = getView().findViewById(R.id.search_field_title);
        author = getView().findViewById(R.id.search_field_author);
        publishYearFloor = getView().findViewById(R.id.search_field_publishfloor);
        publishYearCeiling = getView().findViewById(R.id.search_field_publishceiling);
        pcountFloor = getView().findViewById(R.id.search_field_pagefloor);
        pcountCeiling = getView().findViewById(R.id.search_field_pageceiling);

        preferredGenres = new ArrayList<>();
        preferredGenresLayout = getView().findViewById(R.id.search_flexbox_preferredgenres);
        Util.populateGenreSelector(preferredGenres, preferredGenresLayout, null);

        excludedGenres = new ArrayList<>();
        excludedGenresLayout = getView().findViewById(R.id.search_flexbox_excludedgenres);
        Util.populateGenreSelector(excludedGenres, excludedGenresLayout, null);

        searchButton = getView().findViewById(R.id.search_button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchCriteria searchCriteria;
                try {
                    searchCriteria = new SearchCriteria(
                            title.getText().toString(),
                            author.getText().toString(),
                            publishYearFloor.getText().toString(),
                            publishYearCeiling.getText().toString(),
                            pcountFloor.getText().toString(),
                            pcountCeiling.getText().toString(),
                            preferredGenres,
                            excludedGenres);
                } catch (InvalidSearchCriteriaException e) {
                    Util.shortToast(getContext(),e.getSearchError().toString());
                    return;
                }

                Intent searchIntent = new Intent(getContext(), ListActivitySearch.class);
                searchIntent.putExtra(Util.INTENT_DATA_LIST_LISTTITLE, "Search Results");
                searchIntent.putExtra(Util.INTENT_DATA_LIST_LAYOUTRES, R.layout.listitem_book);
                searchIntent.putExtra(Util.INTENT_DATA_LIST_LABELIFEMPTY, "No books match your search");
                searchIntent.putExtra(Util.INTENT_DATA_SEARCHCRITERIA, searchCriteria);
                startActivity(searchIntent);
            }
        });
    }
}
