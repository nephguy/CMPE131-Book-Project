package cmpe131.cmpebookproject.activity.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.RecyclerViewListableAdapter;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.activity.ListActivityUserLists;
import cmpe131.cmpebookproject.book.Book;

public class TabFragmentList extends TabFragmentBase {

    ArrayList<Book> recommendedBooks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recommendedBooks = activeUser.getRecommendedList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_list, container, false);

        RecyclerView recommendedList = view.findViewById(R.id.tab_list_layout_listrecyclerview);
        recommendedList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewListableAdapter recyclerViewAdapter = new RecyclerViewListableAdapter(activeUser.getRecommendedList(), R.layout.listitem_book/*recommendedBooks.get(0).getListViewLayoutRes()*/, true);
        recommendedList.setAdapter(recyclerViewAdapter);

        Button customListsButton = view.findViewById(R.id.tab_list_button_customlists);
        customListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userListsIntent = new Intent(getContext(), ListActivityUserLists.class);
                userListsIntent.putExtra(Util.INTENT_DATA_LIST_LISTTITLE, "Custom Lists");
                userListsIntent.putExtra(Util.INTENT_DATA_LIST_LAYOUTRES, R.layout.listitem_booklist);
                userListsIntent.putExtra(Util.INTENT_DATA_LIST_DATASET, activeUser.getCustomLists());
                userListsIntent.putExtra(Util.INTENT_DATA_LIST_LABELIFEMPTY, "You don't have any lists yet");
                startActivity(userListsIntent);
            }
        });

        return view;
    }

}
