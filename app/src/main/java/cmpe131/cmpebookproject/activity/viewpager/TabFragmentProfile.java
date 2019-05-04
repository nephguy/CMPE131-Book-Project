package cmpe131.cmpebookproject.activity.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import cmpe131.cmpebookproject.ApplicationManager;
import cmpe131.cmpebookproject.FieldFocusTools;
import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.book.Genre;
import cmpe131.cmpebookproject.database.DbHelper;
import cmpe131.cmpebookproject.recommender.Recommender;
import cmpe131.cmpebookproject.user.Gender;
import cmpe131.cmpebookproject.user.ReadingHabits;
import cmpe131.cmpebookproject.user.User;

public class TabFragmentProfile extends TabFragmentBase {

    EditText usernameField;
    Spinner genderSpinner;
    EditText ageField;
    Spinner readingHabitsSpinner;
    FlexboxLayout likedGenresLayout;
    FlexboxLayout dislikedGenresLayout;
    Button updateButton;
    Button deleteButton;
    boolean delete = false;

    ArrayList<Genre> likedGenres;
    ArrayList<Genre> dislikedGenres;

    DbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = DbHelper.getInstance(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_tab_profile, container, false);
        FieldFocusTools.setAllFieldsClearFocusOnFinish((ViewGroup)view.findViewById(R.id.profile_layout_const));


        usernameField = view.findViewById(R.id.profile_field_username);
        usernameField.setHint(activeUser.getName());

        ageField = view.findViewById(R.id.profile_field_age);
        ageField.setHint(new Integer(activeUser.getAge()).toString());

        genderSpinner = view.findViewById(R.id.profile_spinner_gender);
        genderSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, Gender.values()));
        Util.setSpinnerSelection(genderSpinner,activeUser.getGender());

        readingHabitsSpinner = view.findViewById(R.id.profile_spinner_readinghabits);
        readingHabitsSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, ReadingHabits.values()));
        Util.setSpinnerSelection(readingHabitsSpinner,activeUser.getReadingHabits());

        likedGenres = new ArrayList<>();
        likedGenresLayout = view.findViewById(R.id.profile_flexbox_likedgenres);
        for (Genre g : Genre.values()) {
            CheckBox genreButton = new CheckBox(getContext());
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 32, 16);
            genreButton.setLayoutParams(params);

            if (activeUser.getLikedGenres().contains(g)) {
                likedGenres.add(g);
                genreButton.setChecked(true);
            }

            genreButton.setText(g.toString());
            genreButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Genre thisGenre = Genre.getEnum(buttonView.getText().toString());
                    if (isChecked)
                        likedGenres.add(thisGenre);
                    else
                        likedGenres.remove(thisGenre);
                }
            });
            likedGenresLayout.addView(genreButton);
        }

        dislikedGenres = new ArrayList<>();
        dislikedGenresLayout = view.findViewById(R.id.profile_flexbox_dislikedgenres);
        for (Genre g : Genre.values()) {
            CheckBox genreButton = new CheckBox(getContext());
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 32, 16);
            genreButton.setLayoutParams(params);

            if (activeUser.getDislikedGenres().contains(g)){
                dislikedGenres.add(g);
                genreButton.setChecked(true);
            }


            genreButton.setText(g.toString());
            genreButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Genre thisGenre = Genre.getEnum(buttonView.getText().toString());
                    if (isChecked)
                        dislikedGenres.add(thisGenre);
                    else
                        dislikedGenres.remove(thisGenre);
                }
            });
            dislikedGenresLayout.addView(genreButton);
        }


        updateButton = view.findViewById(R.id.profile_button_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = usernameField.getText().toString();
                Gender gender = (Gender) genderSpinner.getSelectedItem();
                String ageString = ageField.getText().toString();
                ReadingHabits readingHabits = (ReadingHabits) readingHabitsSpinner.getSelectedItem();

                if (dbHelper.usernameTaken(name)) {
                    Toast.makeText(getContext(), "A user with that name already exists!", Toast.LENGTH_LONG).show();
                    return;
                }

                User editedUser = new User(activeUser);
                if (!name.equals(""))
                    editedUser.setName(name);
                editedUser.setGender(gender);
                if (!ageString.equals(""))
                    editedUser.setAge(Integer.parseInt(ageString));
                editedUser.setReadingHabits(readingHabits);
                editedUser.setLikedGenres(likedGenres);
                editedUser.setDislikedGenres(dislikedGenres);

                if (editedUser.equals(activeUser)) {
                    Util.shortToast(getContext(), "No changes made - did not update user");
                    return;
                }
                Recommender r = new Recommender(editedUser, DbHelper.getInstance(getContext()).getAllBooks(), 10);
                editedUser.setRecommendedList(r.produceRecommendedBooks());
                dbHelper.appendUser(activeUser, editedUser);

                Util.longToast(getContext(), "User information updated. Please login again");
                ApplicationManager.logout();
            }
        });

        deleteButton = view.findViewById(R.id.profile_button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delete) {
                    dbHelper.deleteUser(activeUser);
                    Util.shortToast(getContext(),"Account deleted");
                    ApplicationManager.logout();
                }
                else {
                    Util.longToast(getContext(),"Press again to delete account");
                    delete = true;
                }
            }
        });

        return view;
    }
}
