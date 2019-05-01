package cmpe131.cmpebookproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.FocusFixer;
import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.book.BookList;
import cmpe131.cmpebookproject.book.Genre;
import cmpe131.cmpebookproject.database.DbHelper;
import cmpe131.cmpebookproject.recommender.Recommender;
import cmpe131.cmpebookproject.user.Gender;
import cmpe131.cmpebookproject.user.ReadingHabits;
import cmpe131.cmpebookproject.user.User;

import static cmpe131.cmpebookproject.Util.INTENT_DATA_USERNAME;

public class CreateAccActivity extends AppCompatActivity {

    EditText usernameField;
    EditText passwordField;
    Spinner genderSpinner;
    EditText ageField;
    Spinner readingHabitsSpinner;
    FlexboxLayout likedGenresLayout;
    FlexboxLayout dislikedGenresLayout;
    Button createButton;

    ArrayList<Genre> likedGenres;
    ArrayList<Genre> dislikedGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createacc);
        FocusFixer.setAllFieldsPassFocusOnFinish((ViewGroup)findViewById(R.id.createacc_layout_const));

        usernameField = findViewById(R.id.createacc_field_username);
        passwordField = findViewById(R.id.createacc_field_pass);
        ageField = findViewById(R.id.createacc_field_age);
        genderSpinner = findViewById(R.id.createacc_spinner_gender);
        genderSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, Gender.values()));
        readingHabitsSpinner = findViewById(R.id.createacc_spinner_readinghabits);
        readingHabitsSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, ReadingHabits.values()));

        likedGenres = new ArrayList<>();
        likedGenresLayout = findViewById(R.id.createAcc_flexbox_likedgenres);
        for (Genre g : Genre.values()) {
            CheckBox genreButton = new CheckBox(getApplicationContext());

            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(32, 0, 0, 16);
            genreButton.setLayoutParams(params);

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
        dislikedGenresLayout = findViewById(R.id.createAcc_flexbox_dislikedgenres);
        for (Genre g : Genre.values()) {
            CheckBox genreButton = new CheckBox(getApplicationContext());

            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(32, 0, 0, 16);
            genreButton.setLayoutParams(params);

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


        createButton = findViewById(R.id.createacc_button_create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data
                String name = usernameField.getText().toString();
                String pass = passwordField.getText().toString();
                Gender gender = (Gender) genderSpinner.getSelectedItem();
                String ageString = ageField.getText().toString();
                ReadingHabits readingHabits = (ReadingHabits) readingHabitsSpinner.getSelectedItem();

                // final checks
                if (name.equals("")) {
                    Toast.makeText(getApplicationContext(), "No username chosen", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (DbHelper.getInstance(getApplicationContext()).usernameTaken(name)) {
                    Toast.makeText(getApplicationContext(), "A user with that name already exists!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "No password chosen", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (ageString.equals("")) {
                    Toast.makeText(getApplicationContext(), "No age specified", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (likedGenres.size() == 0) {
                    Toast.makeText(getApplicationContext(), "You must specify at least one liked genre", Toast.LENGTH_SHORT).show();
                    return;
                }

                // create user, save, and return
                User newUser = new User(name, pass, gender, Integer.parseInt(ageString), readingHabits, likedGenres, dislikedGenres, new ArrayList<Book>(), new BookList("Recommended"), new ArrayList<BookList>());
                Recommender r = new Recommender(newUser, DbHelper.getInstance(getApplicationContext()).getAllBooks(), 10);
                r.makeRecommendedBookList();

                DbHelper.getInstance(getApplicationContext()).addUser(newUser);

                Intent returnIntent = new Intent();
                returnIntent.putExtra(INTENT_DATA_USERNAME, name);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
