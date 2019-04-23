package cmpe131.cmpebookproject.database;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class CsvDb {

    private Context context;
    private String csvFileName;

    public CsvDb(Context context, String csvFileName) {
        this.context = context;
        this.csvFileName = csvFileName;
    }

    /** returns the desired line from the file as a String **/
    String readSpecificLine (int line) throws IOException {

        ArrayList<String> bookDb = readEntireFile();

        if(line < bookDb.size())
        {
            return bookDb.get(line);
        }
        else
        {
            System.out.print("Line does not exist in DB");
        }
        return null;
    }

    /** returns an ArrayList of strings, one string per line **/
    ArrayList<String> readEntireFile () throws IOException{
    // open readers
        InputStreamReader isr = new InputStreamReader(context.getAssets().open(csvFileName + ".csv"));
        BufferedReader reader = new BufferedReader (isr);

    // read file and store it in ArrayList
        ArrayList<String> file = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null)
            file.add(line);

    // close readers and return ArrayList
        reader.close();
        isr.close();
        return file;
    }
}
