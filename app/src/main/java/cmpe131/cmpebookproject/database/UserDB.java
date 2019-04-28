package cmpe131.cmpebookproject.database;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import cmpe131.cmpebookproject.user.User;

public class UserDB {

    private Context context;
    private File dbFile;
    private UserList userList;

    protected UserDB(Context context, String fileName) {
        this.context = context;
        dbFile = new File (context.getFilesDir(), fileName);
        try {
            if (dbFile.createNewFile())
                userList = new UserList(new ArrayList<User>(0));
            else
                userList = new UserList(readUserList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void write() {
        try {
            if (dbFile.exists())
                dbFile.delete();
            dbFile.createNewFile();

            FileOutputStream fos = context.openFileOutput(dbFile.getName(), Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(userList);
            os.close();
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<User> readUserList() {
        ArrayList<User> userList = new ArrayList<>();
        try{
            FileInputStream fis =  context.openFileInput(dbFile.getName());
            ObjectInputStream ois = new ObjectInputStream(fis);
            userList = (ArrayList<User>)ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }

    UserList getUserList() {
        return userList;
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
}
