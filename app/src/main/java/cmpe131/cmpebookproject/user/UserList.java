package cmpe131.cmpebookproject.user;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import cmpe131.cmpebookproject.ApplicationContextProvider;

public class UserList implements Serializable {
    private static final long serialVersionUID = 2112091920L;
    private ArrayList<User> userDataBase;
    private int maxSize = 100;

    public UserList()
    {
        userDataBase = new ArrayList<>();
    }


    public void addUser(User u)
    {
        if(userDataBase.size() < maxSize)
            userDataBase.add(u);
        else {
            System.out.println("ERROR: DATABASE FULL");
            Toast.makeText(ApplicationContextProvider.getContext(), "User database full! Cannot add new user", Toast.LENGTH_LONG).show();
        }

         //UserDB.addUser(userDataBase);//write changed list to UserDB

    }

    public void removeUser(User u)
    {
        if(userDataBase.remove(u))
        {
            System.out.print("User Deleted");
        }
        else
        {
            System.out.print("User cannot be deleted");
        }
        //UserDB.addUser(userDataBase);//write changed list to UserDB
    }

}
