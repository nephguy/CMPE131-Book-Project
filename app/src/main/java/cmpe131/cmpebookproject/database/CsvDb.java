package cmpe131.cmpebookproject.database;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class CsvDb {

    private Context context;
    private String csvFileName;

    protected CsvDb(Context context, String csvFileName) {
        this.context = context;
        this.csvFileName = csvFileName;
    }

    /** returns the desired line from the file as a String **/
    String readSpecificLine (int line) throws IOException {
    // open readers
        InputStreamReader isr = new InputStreamReader(context.getAssets().open(csvFileName));
        BufferedReader reader = new BufferedReader(isr);

    // skip to desired line and read it
        for (int i = 0; i < line; i++) {
            reader.readLine();
        }
        String data = reader.readLine();

    // throw an exception if trying to read beyond the file
        if (data == null)
            throw new IllegalArgumentException("There are less than " + line + " lines in the file!");

    // close readers and return line
        reader.close();
        isr.close();
        return data;
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
