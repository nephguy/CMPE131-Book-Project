package cmpe131.cmpebookproject.database;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import cmpe131.cmpebookproject.ApplicationManager;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.user.User;

public class UserList extends ArrayList<User> implements Serializable {
    private static final long serialVersionUID = Util.generateSerialUID("ulist_v1");
    public static final int MAX_USERS = 100;

    public UserList(ArrayList<User> users) {
        super(users);
    }


    @Override
    public boolean add(User u) {
        if(size() >= MAX_USERS) {
            System.out.println("ERROR: DATABASE FULL");
            Util.shortToast(ApplicationManager.getContext(), "User database full! Cannot add new user");
            return false;
        }
        else
            return super.add(u);
    }

}
