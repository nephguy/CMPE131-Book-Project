package cmpe131.cmpebookproject.user;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import cmpe131.cmpebookproject.ApplicationContextProvider;

import static android.content.Context.MODE_PRIVATE;

public class UserDB {


    public static void writeToUserDB(ArrayList<User> L1)
    {
        try {
            FileOutputStream fos = ApplicationContextProvider.getContext().openFileOutput("UserDB,", MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(L1);
            os.close();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static ArrayList<User> readUserList()
    {
        ArrayList<User> UserList = null;
        try{
            FileInputStream fis =  ApplicationContextProvider.getContext().openFileInput("UserDB");
            ObjectInputStream is = new ObjectInputStream(fis);
            UserList = (ArrayList<User>)is.readObject();
            is.close();
            fis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return UserList;
    }

    public static boolean nameExists(String name) {
        ArrayList<String> userNames = new ArrayList<>();
        for (User u : readUserList())
            userNames.add(u.getName());
        return (userNames.contains(name));
    }

    /* DEPRECATED

    public static boolean verifyCredentials(String username, String password) {
        TreeMap<String, Integer> userPassMap = new TreeMap<>();
        for (User u : readUserList())
            userPassMap.put(u.getName(), u.getPasswordHash());
        Integer passHash = userPassMap.get(username);
        if (passHash == null)
            return false;
        else if (passHash == password.hashCode())
            return true;
        else
            return false;
    }
    */

    public static User getUser (String username, String password) {
        for (User u : readUserList()) {
            boolean userMatch = u.getName().equals(username);
            boolean passMatch = (u.getPasswordHash() == password.hashCode());
            if (userMatch && passMatch)
                return u;
        }
        return null;
    }
}
