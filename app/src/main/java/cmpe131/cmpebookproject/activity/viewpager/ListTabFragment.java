package cmpe131.cmpebookproject.activity.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.database.DbHelper;
import cmpe131.cmpebookproject.user.User;

public class ListTabFragment extends Fragment {

    User activeUser;
    LayoutInflater inflater;

    // newInstance constructor for creating fragment with arguments
    public static ListTabFragment newInstance(User user) {
        ListTabFragment tabBaseFragment = new ListTabFragment();
        Bundle args = new Bundle();
        args.putParcelable(Util.KEY_DATA_ACTIVEUSER, user);
        tabBaseFragment.setArguments(args);
        return tabBaseFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activeUser = getArguments().getParcelable(Util.KEY_DATA_ACTIVEUSER);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.activity_main_tab_list, container, false);

        final LinearLayout recommendedBooksListView = view.findViewById(R.id.tab_list_recommended);
        populateRecommendedList(recommendedBooksListView);


        Button customListsButton = view.findViewById(R.id.tab_list_button_customlists);
        customListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - MAKE DIALOG TO CREATE NEW LIST, THEN GO TO CUSTOM LISTS OR CLOSE
                Util.longToast(getContext(),"Custom Lists are a work in progress");

                /*
                Recommender r = new Recommender(activeUser, DbHelper.getInstance(getContext()).getAllBooks(),10);
                r.makeRecommendedBookList();
                System.out.println("SIZE OF RECOMMENDED BOOK LIST: " + activeUser.getRecommendedList().size());
                populateRecommendedList(recommendedBooksListView);
                */
            }
        });

        return view;
    }

    private void populateRecommendedList(LinearLayout recommendedList) {
        ArrayList<Book> testBooks = new ArrayList<>();
        ArrayList<Book> allBooks = DbHelper.getInstance(getContext()).getAllBooks();
        testBooks.add(allBooks.get(0));
        testBooks.add(allBooks.get(1));
        testBooks.add(allBooks.get(2));
        testBooks.add(allBooks.get(3));
        testBooks.add(allBooks.get(4));
        testBooks.add(allBooks.get(5));

        for (Book b : testBooks)
        //for (Book b : activeUser.getRecommendedList())
            recommendedList.addView(b.getListView(inflater));
    }
}