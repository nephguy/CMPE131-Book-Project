package cmpe131.cmpebookproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.user.User;

import static cmpe131.cmpebookproject.activity.LoginActivity.INTENT_DATA_USER;

public class MainActivity extends AppCompatActivity {

    User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activeUser = getIntent().getParcelableExtra(INTENT_DATA_USER);
    }

}
