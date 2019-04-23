package cmpe131.cmpebookproject.user;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> userDataBase;

    public UserList()
    {
        userDataBase = new ArrayList<>();
    }

    public void addUser(User u)
    {
        if(userDataBase.size() < 100)
            userDataBase.add(u);
        else
            System.out.print("DataBase Full");

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
    }
}
