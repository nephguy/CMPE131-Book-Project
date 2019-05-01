package cmpe131.cmpebookproject.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmpe131.cmpebookproject.InvalSelectionIfContains;
import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.book.BookList;

public class BookInfoActivity extends AppCompatActivity {

    Book book;

    TextView title;
    TextView author;
    TextView pubAndYear;
    TextView isbn;
    TextView genreAndPcount;
    TextView rating;
    Button addToListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinfo);

        book = getIntent().getParcelableExtra(Util.INTENT_DATA_BOOK);

        title = findViewById(R.id.bookinfo_label_title);
        author = findViewById(R.id.bookinfo_label_author);
        pubAndYear = findViewById(R.id.bookinfo_label_publisherANDyear);
        isbn = findViewById(R.id.bookinfo_label_isbn);
        genreAndPcount = findViewById(R.id.bookinfo_label_genreANDpagecount);
        rating = findViewById(R.id.bookinfo_label_rating);
        addToListButton = findViewById(R.id.bookinfo_button_addtolist);

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        pubAndYear.setText("Published in " + book.getYearPublished() + " by " + book.getPublisher());
        isbn.setText("ISBN#: " + book.getIsbn());
        genreAndPcount.setText(book.getGenre().toString() + " - " + book.getNumPages() + " pages");
        rating.setText("Average rating: " + book.getRating() + "/5");


        DialogInterface.OnClickListener addToList = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };
        final AlertDialog addToListDialog = new AlertDialog.Builder(this)
                .setTitle(addToListButton.getText())
                .setNegativeButton("Cancel", null)
                //.setAdapter(new InvalSelectionIfContains<BookList>(this,R.layout.support_simple_spinner_dropdown_item,null,book),addToList)
                .create();
        addToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToListDialog.show();

                //Util.shortToast(getApplicationContext(), "need to implement");
            }
        });

    }
}
