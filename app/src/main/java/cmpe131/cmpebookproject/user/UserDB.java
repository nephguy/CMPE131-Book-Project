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

import cmpe131.cmpebookproject.ApplicationContextProvider;

import static android.content.Context.MODE_PRIVATE;

public class UserDB {


    public void writeToUserDB(UserList L1)
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

    public ArrayList<User> readUserList()
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
}
