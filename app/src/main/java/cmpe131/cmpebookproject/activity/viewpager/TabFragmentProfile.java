package cmpe131.cmpebookproject.activity.viewpager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
    boolean confirmDeleteAccount = false;

    ArrayList<Genre> likedGenres;
    ArrayList<Genre> dislikedGenres;

    DbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = DbHelper.getInstance(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        FieldFocusTools.setAllFieldsClearFocusOnFinish((ViewGroup)getView().findViewById(R.id.profile_layout_const));

        usernameField = getView().findViewById(R.id.profile_field_username);
        usernameField.setHint(activeUser.getName());

        ageField = getView().findViewById(R.id.profile_field_age);
        ageField.setHint(Integer.toString(activeUser.getAge()));

        genderSpinner = getView().findViewById(R.id.profile_spinner_gender);
        genderSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, Gender.values()));
        Util.setSpinnerSelection(genderSpinner,activeUser.getGender());

        readingHabitsSpinner = getView().findViewById(R.id.profile_spinner_readinghabits);
        readingHabitsSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, ReadingHabits.values()));
        Util.setSpinnerSelection(readingHabitsSpinner,activeUser.getReadingHabits());

        likedGenres = new ArrayList<>();
        likedGenresLayout = getView().findViewById(R.id.profile_flexbox_likedgenres);
        Util.populateGenreSelector(likedGenres, likedGenresLayout, activeUser.getLikedGenres());

        dislikedGenres = new ArrayList<>();
        dislikedGenresLayout = getView().findViewById(R.id.profile_flexbox_dislikedgenres);
        Util.populateGenreSelector(dislikedGenres, dislikedGenresLayout, activeUser.getDislikedGenres());

        updateButton = getView().findViewById(R.id.profile_button_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = usernameField.getText().toString();
                Gender gender = (Gender) genderSpinner.getSelectedItem();
                String ageString = ageField.getText().toString();
                ReadingHabits readingHabits = (ReadingHabits) readingHabitsSpinner.getSelectedItem();

                if (dbHelper.usernameTaken(name)) {
                    Util.shortToast(getContext(), "A user with that name already exists!");
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

        deleteButton = getView().findViewById(R.id.profile_button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmDeleteAccount) {
                    AlertDialog deleteDialog = Util.styleFixedAlertDialogBuilder(getContext())
                            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dbHelper.deleteUser(activeUser);
                                    Util.shortToast(getContext(),"Account deleted");
                                    ApplicationManager.logout();
                                }
                            })
                            .create();
                    deleteDialog.show();
                }
                else {
                    Util.longToast(getContext(),"Press again to delete your account");
                    confirmDeleteAccount = true;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        confirmDeleteAccount = false;
    }
}
