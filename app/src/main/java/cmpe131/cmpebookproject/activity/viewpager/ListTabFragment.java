package cmpe131.cmpebookproject.activity.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.database.DbHelper;
import cmpe131.cmpebookproject.recommender.Recommender;
import cmpe131.cmpebookproject.user.User;

public class ListTabFragment extends Fragment {

    public static final String KEY_DATA_ACTIVEUSER = "KEY_DATA_ACTIVEUSER";
    User activeUser;
    LayoutInflater inflater;

    // newInstance constructor for creating fragment with arguments
    public static ListTabFragment newInstance(User user) {
        ListTabFragment tabBaseFragment = new ListTabFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_DATA_ACTIVEUSER, user);
        tabBaseFragment.setArguments(args);
        return tabBaseFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activeUser = getArguments().getParcelable(KEY_DATA_ACTIVEUSER);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.activity_main_tab_list, container, false);

        final ScrollView recommendedBooks = view.findViewById(R.id.tab_list_scrollview_recommended);
        populateRecommendedList(recommendedBooks);


        Button customListsButton = view.findViewById(R.id.tab_list_button_customlists);
        customListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recommender r = new Recommender(activeUser, DbHelper.getInstance(getContext()).getAllBooks(),10);
                r.makeRecommendedBookList();
                Toast.makeText(getContext(),"SIZE OF RECOMMENDED BOOK LIST: " + activeUser.getRecommendedList().size(), Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

    private void populateRecommendedList(ScrollView recommendedList) {
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

            recommendedList.addView(itemView);
        }
    }
}
