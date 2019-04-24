package cmpe131.cmpebookproject.database;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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

    /** returns an ArrayList of book data strings **/
    ArrayList<String> readEntireFile () {
        ArrayList<String> file = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open(csvFileName));
            BufferedReader reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null)
                file.add(line);

            reader.close();
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
