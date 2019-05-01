package cmpe131.cmpebookproject.activity.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import cmpe131.cmpebookproject.FieldFocusTools;
import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.book.Genre;

public class TabFragmentSearch extends TabFragmentBase {

    Button searchButton;
    FlexboxLayout includedGenresLayout;
    FlexboxLayout excludedGenresLayout;
    ArrayList<Genre> includedGenres;
    ArrayList<Genre> excludedGenres;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_tab_search, container, false);
        FieldFocusTools.setAllFieldsPassFocusOnFinish((ViewGroup)view.findViewById(R.id.search_layout_const));

        includedGenres = new ArrayList<>();
        includedGenresLayout = view.findViewById(R.id.search_flexbox_includedgenres);
        for (Genre g : Genre.values()) {
            CheckBox genreButton = new CheckBox(getContext());

            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(32, 0, 0, 16);
            genreButton.setLayoutParams(params);

            genreButton.setText(g.toString());
            genreButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Genre thisGenre = Genre.getEnum(buttonView.getText().toString());
                    if (isChecked)
                        includedGenres.add(thisGenre);
                    else
                        includedGenres.remove(thisGenre);
                }
            });
            includedGenresLayout.addView(genreButton);
        }

        excludedGenres = new ArrayList<>();
        excludedGenresLayout = view.findViewById(R.id.search_flexbox_excludedgenres);
        for (Genre g : Genre.values()) {
            CheckBox genreButton = new CheckBox(getContext());

            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(32, 0, 0, 16);
            genreButton.setLayoutParams(params);

            genreButton.setText(g.toString());
            genreButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Genre thisGenre = Genre.getEnum(buttonView.getText().toString());
                    if (isChecked)
                        excludedGenres.add(thisGenre);
                    else
                        excludedGenres.remove(thisGenre);
                }
            });
            excludedGenresLayout.addView(genreButton);
        }

        searchButton = view.findViewById(R.id.search_button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Searching is still a work in progress. Sorry!", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
}
