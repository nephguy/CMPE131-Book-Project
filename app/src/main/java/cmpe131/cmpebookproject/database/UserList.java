package cmpe131.cmpebookproject.database;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import cmpe131.cmpebookproject.ApplicationContextProvider;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.user.User;

public class UserList extends ArrayList<User> implements Serializable {
    private static final long serialVersionUID = Util.generateSerialUID("ulist_v1");
    private int maxSize = 100;

    public UserList(ArrayList<User> users) {
        super(users);
    }


    @Override
    public boolean add(User u) {
        if(size() >= maxSize) {
            System.out.println("ERROR: DATABASE FULL");
            Toast.makeText(ApplicationContextProvider.getContext(), "User database full! Cannot add new user", Toast.LENGTH_LONG).show();
            return false;
        }
        else
            return super.add(u);
    }

}
