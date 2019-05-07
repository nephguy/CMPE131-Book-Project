package cmpe131.cmpebookproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import cmpe131.cmpebookproject.ApplicationManager;
import cmpe131.cmpebookproject.FieldFocusTools;
import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.database.DbHelper;

public class LoginActivity extends AppCompatActivity {

    EditText usernameField;
    EditText passwordField;
    Button loginButton;
    Button createaccButton;

    int failCount = 0;
    boolean preventLoginAttempt = false;
    Timer preventLoginTimer;

    Intent createAccIntent;
    Intent loginIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // this just loads the singleton instance of DbHelper.
        // makes the rest of the app ever-so-slightly faster
        DbHelper.getInstance(getApplicationContext());

        createAccIntent = new Intent(this, CreateAccActivity.class);
        loginIntent = new Intent(this, MainActivity.class);

        loginButton = findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preventLoginAttempt) {
                    Util.shortToast(getApplicationContext(), "Login attempt prevented. Please wait");
                    return;
                }

                boolean loginSuccessful = ApplicationManager.login(usernameField.getText().toString(), passwordField.getText().toString());
                if (loginSuccessful)
                    startActivity(loginIntent);
                else if ( ((++failCount)%5) != 0 ) // if this is not the 5nth failure, allow another attempt
                    Util.shortToast(getApplicationContext(), "Invalid Login Credentials");
                else { // if this is the 5nth failure, prevent login for n*10 seconds
                    int loginDelay = (failCount/5)*10;
                    Util.shortToast(getApplicationContext(), "Too many failed login attempts. Please wait " + loginDelay + " seconds before trying again");
                    preventLoginAttempt = true;
                    preventLoginTimer = new Timer();
                    preventLoginTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            preventLoginAttempt = false;
                            preventLoginTimer.cancel();
                        }
                    }, loginDelay*1000);
                }

            }
        });

        createaccButton = findViewById(R.id.login_button_createacc);
        createaccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(createAccIntent, Util.REQUEST_CREATE_ACCOUNT);
            }
        });

        usernameField = findViewById(R.id.login_field_username);
        FieldFocusTools.setOnKeyListener_passFocusOnFinish(usernameField);
        passwordField = findViewById(R.id.login_field_pass);
        Util.setOnKeyListener_fieldPressButtonOnFinish(passwordField,loginButton);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.REQUEST_CREATE_ACCOUNT) {
            if (resultCode == RESULT_OK) {
                String newUserName = data.getStringExtra(Util.INTENT_DATA_NEWUSER_USERNAME);
                usernameField.setText(newUserName);

                Util.shortToast(getApplicationContext(),"Account created successfully!");
            }
        }
        if (resultCode == RESULT_CANCELED) {
            Util.shortToast(getApplicationContext(), "Account creation canceled");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        passwordField.setText("");
    }
}
