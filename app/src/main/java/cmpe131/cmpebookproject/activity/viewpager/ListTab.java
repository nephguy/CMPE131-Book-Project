package cmpe131.cmpebookproject.activity.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.book.Book;

public class ListTab extends TabBaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_tab_list, container, false);

        ScrollView recommendedBooks = view.findViewById(R.id.tab_list_scrollview_recommended);
        for (Book b : activeUser.getRecommendedList()) {
            View itemView = inflater.inflate(R.layout.view_listitem_book, null);

            TextView title = itemView.findViewById(R.id.listitem_label_title);
            title.setText(b.getTitle());
            TextView author = itemView.findViewById(R.id.listitem_label_author);
            author.setText(b.getAuthor());
            TextView yearGenre = itemView.findViewById(R.id.listitem_label_yearAndGenre);
            yearGenre.setText(b.getYearPublished() + " - " + b.getGenre().toString());
            TextView pageCount = itemView.findViewById(R.id.listitem_label_pagecount);
            pageCount.setText(b.getNumPages());
            TextView rating = itemView.findViewById(R.id.listitem_label_rating);
            rating.setText(Float.toString(b.getRating()) + "/5");

            recommendedBooks.addView(itemView);
        }

        Button customListsButtom = view.findViewById(R.id.tab_list_button_customlists);
        customListsButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast wip = new Toast(getContext());
                wip.setText("Custom Lists are a work in progress");
                wip.setDuration(Toast.LENGTH_LONG);
                wip.show();
            }
        });


        return view;
    }
}

