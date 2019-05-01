package cmpe131.cmpebookproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cmpe131.cmpebookproject.ApplicationManager;
import cmpe131.cmpebookproject.user.User;

public abstract class UserActivityBase extends AppCompatActivity {

    User activeUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activeUser = ApplicationManager.getActiveUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activeUser = ApplicationManager.getActiveUser();
    }

}
