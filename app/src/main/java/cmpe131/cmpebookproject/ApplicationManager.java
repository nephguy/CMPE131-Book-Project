package cmpe131.cmpebookproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import cmpe131.cmpebookproject.activity.LoginActivity;
import cmpe131.cmpebookproject.database.DbHelper;
import cmpe131.cmpebookproject.database.UserReadWriteException;
import cmpe131.cmpebookproject.user.User;

public class ApplicationManager extends Application {
    /**
     * Keeps a reference of the application context and active user
     */
    private static Context sContext;
    private static User sActiveUser;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }

    public static User getActiveUser() {
        if (sActiveUser == null)
            throw new UserReadWriteException("WARNING: ACTIVE USER IS NULL. WILL CAUSE FATAL ERRORS.");
        return sActiveUser;
    }

    public static boolean login(String username, String password) {
        User loginUser = DbHelper.getInstance(sContext).getUser(username, password);
        if (loginUser == null) {
            return false;
        }
        else {
            sActiveUser = loginUser;
            return true;
        }
    }

    public static void logout() {
        sActiveUser = null;
        Intent logoutIntent = new Intent(sContext, LoginActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sContext.startActivity(logoutIntent);
    }



}
