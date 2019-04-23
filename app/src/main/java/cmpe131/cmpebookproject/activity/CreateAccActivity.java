package cmpe131.cmpebookproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.book.BookList;
import cmpe131.cmpebookproject.book.Genre;
import cmpe131.cmpebookproject.database.DbHelper;
import cmpe131.cmpebookproject.recommender.Recommender;
import cmpe131.cmpebookproject.user.Gender;
import cmpe131.cmpebookproject.user.ReadingHabits;
import cmpe131.cmpebookproject.user.User;
import cmpe131.cmpebookproject.user.UserDB;

import static cmpe131.cmpebookproject.activity.LoginActivity.INTENT_DATA_USERNAME;

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

        usernameField = findViewById(R.id.createacc_field_username);
        passwordField = findViewById(R.id.createacc_field_pass);
        ageField = findViewById(R.id.createacc_field_age);
        genderSpinner = findViewById(R.id.createacc_spinner_gender);
        genderSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,Gender.values()));
        readingHabitsSpinner = findViewById(R.id.createacc_spinner_readinghabits);
        readingHabitsSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,ReadingHabits.values()));


        likedGenres = new ArrayList<>();
        likedGenresLayout = findViewById(R.id.createAcc_flexbox_likedgenres);
        for (Genre g : Genre.values()) {
            final View radiopairView = getLayoutInflater().inflate(R.layout.view_radiopair, likedGenresLayout);
            final TextView buttonLabel = radiopairView.findViewById(R.id.radiopair_label);
            buttonLabel.setText(g.toString());
            final RadioButton radioButton = radiopairView.findViewById(R.id.radiopair_button);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Genre thisGenre = Genre.getEnum(buttonLabel.getText().toString());
                    if (isChecked)
                        likedGenres.add(thisGenre);
                    else
                        likedGenres.remove(thisGenre);
                }
            });
        }

        dislikedGenres = new ArrayList<>();
        dislikedGenresLayout = findViewById(R.id.createAcc_flexbox_dislikedgenres);
        for (Genre g : Genre.values()) {
            final View radiopairView = getLayoutInflater().inflate(R.layout.view_radiopair, dislikedGenresLayout);
            final TextView buttonLabel = radiopairView.findViewById(R.id.radiopair_label);
            buttonLabel.setText(g.toString());
            final RadioButton radioButton = radiopairView.findViewById(R.id.radiopair_button);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Genre thisGenre = Genre.getEnum(buttonLabel.getText().toString());
                    if (isChecked)
                        dislikedGenres.add(thisGenre);
                    else
                        dislikedGenres.remove(thisGenre);
                }
            });
        }


        createButton = findViewById(R.id.createacc_button_create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = usernameField.getText().toString();
                String pass = passwordField.getText().toString();
                Gender gender = (Gender) genderSpinner.getSelectedItem();
                int age = Integer.parseInt(ageField.getText().toString());
                ReadingHabits readingHabits = (ReadingHabits) readingHabitsSpinner.getSelectedItem();

                User newUser = new User (name, pass, gender, age, readingHabits, likedGenres, dislikedGenres, new ArrayList<Book>(), new BookList("Recommended"), new ArrayList<BookList>());

                if (UserDB.nameExists(name)) {
                    Toast failedAccToast = new Toast(getApplicationContext());
                    failedAccToast.setText("A user with this name already exists!");
                    failedAccToast.setDuration(Toast.LENGTH_LONG);
                    failedAccToast.show();
                    return;
                }

                Recommender r = new Recommender(newUser, DbHelper.getInstance(getApplicationContext()).getAllBooks(), 10);
                r.makeRecommendedBookList();

                ArrayList<User> users = UserDB.readUserList();
                users.add(newUser);
                UserDB.writeToUserDB(users);

                Intent returnIntent = new Intent();
                returnIntent.putExtra(INTENT_DATA_USERNAME, name);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
