package cmpe131.cmpebookproject.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.user.User;
import cmpe131.cmpebookproject.user.UserDB;

public class LoginActivity extends AppCompatActivity {

    EditText usernameField;
    EditText passwordField;
    Button loginButton;
    Button createaccButton;

    Intent createAccIntent;
    Intent loginIntent;

    public static final int REQUEST_CREATE_ACCOUNT = 10101;
    public static final String INTENT_DATA_USER = "INTENT_DATA_USER";
    public static final String INTENT_DATA_USERNAME = "INTENT_DATA_USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.login_field_username);
        passwordField = findViewById(R.id.login_field_pass);

        createAccIntent = new Intent(this, CreateAccActivity.class);
        loginIntent = new Intent(this, MainActivity.class);


        loginButton = findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User loginUser = UserDB.getUser(usernameField.getText().toString(), passwordField.getText().toString());
                if (loginUser == null) {
                    Toast.makeText(getApplicationContext(),"Invalid Login Credentials", Toast.LENGTH_LONG).show();
                }
                else {
                    loginIntent.putExtra(INTENT_DATA_USER, (Parcelable) loginUser);
                    startActivity(loginIntent);
                }
            }
        });


        createaccButton = findViewById(R.id.login_button_createacc);
        createaccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(createAccIntent, REQUEST_CREATE_ACCOUNT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CREATE_ACCOUNT) {
            if (resultCode == RESULT_OK) {
                String newUserName = data.getStringExtra(INTENT_DATA_USERNAME);
                usernameField.setText(newUserName);

                Toast.makeText(getApplicationContext(),"Account created successfully!",Toast.LENGTH_LONG).show();
            }
        }
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Canceled Account Creation", Toast.LENGTH_SHORT).show();
        }
    }
}
