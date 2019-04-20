package cmpe131.cmpebookproject.database;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class SerializableDb<T> {

    private Context context;
    private File dbDir;
    private String fileSuffix;

    protected SerializableDb(Context context, String dirName, String fileSuffix) {
        this.context = context;
        dbDir = new File (context.getFilesDir(), dirName);
        dbDir.mkdir();
        this.fileSuffix = fileSuffix;
    }


    /** write a new serializable object
     *
     * if the object already exists, this returns false and does not modify anything
     * otherwise, it creates a file for and stores the object **/
    boolean write (String fileName, T obj) {
        try {
            File newFile = new File(dbDir, fileName + fileSuffix);
            if (!newFile.createNewFile())
                return false;
            FileOutputStream fileOut = context.openFileOutput(newFile.getName(), Context.MODE_PRIVATE);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(obj);
            objOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /** appends an existing file
     *
     * if the file does not already exists, returns false
     * otherwise, deletes the old file and replaces it with a new one containing newObj**/
    boolean append(String fileName, T newObj) {
        try {
            File appendedFile = new File(dbDir, fileName + fileSuffix);
            if (!appendedFile.delete())
                return false;
            appendedFile.createNewFile();
            FileOutputStream fileOut = context.openFileOutput(appendedFile.getName(), Context.MODE_PRIVATE);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(newObj);
            objOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /** deletes a file
     *
     * returns true if the file existed and was deleted
     * returns false otherwise **/
    boolean delete(String fileName) {
        File deletedFile = new File (dbDir, fileName + fileSuffix);
        return deletedFile.delete();
    }

    /** given a file name, reads that file
     *
     * if it does not exist, returns null
     * otherwise, returns an object of that file's type **/
    T read (String fileName) {
        T readObj = null;
        File readFile = new File(dbDir, fileName + fileSuffix);
        if (!readFile.exists())
            return null;
        try {
            FileInputStream fileIn = context.openFileInput(readFile.getName());
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            readObj = (T) objIn.readObject();
            objIn.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return readObj;
    }

    /** returns an ArrayList of every file **/
    ArrayList<T> readAll() {
        ArrayList<T> all = new ArrayList<>();
        for (String fileName : dbDir.list())
            all.add(read(fileName));
        return all;
    }
}
