package cmpe131.cmpebookproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cmpe131.cmpebookproject.Listable;
import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.RecyclerViewListableAdapter;
import cmpe131.cmpebookproject.Util;

public abstract class ListActivityBase extends AppCompatActivity {

    ArrayList<? extends Listable> dataToList;
    int viewLayoutRes;

    TextView listTitle;
    RecyclerView listRecyclerView;
    RecyclerViewListableAdapter recyclerViewAdapter;
    TextView emptyLabel;
    Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listTitle = findViewById(R.id.list_label_title);
        listRecyclerView = findViewById(R.id.list_layout_listrecyclerview);
        emptyLabel = findViewById(R.id.list_label_listempty);
        actionButton = findViewById(R.id.list_button_actionbutton);

        listTitle.setText(getIntent().getStringExtra(Util.INTENT_DATA_LIST_LISTTITLE));
        dataToList = getIntent().getParcelableArrayListExtra(Util.INTENT_DATA_LIST_DATASET);
        viewLayoutRes = getIntent().getIntExtra(Util.INTENT_DATA_LIST_LAYOUTRES, 0);

        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        recyclerViewAdapter = new RecyclerViewListableAdapter(dataToList, viewLayoutRes, true);
        listRecyclerView.setAdapter(recyclerViewAdapter);

        checkListEmpty("empty");
    }

    protected void checkListEmpty(String textIfEmpty) {
        emptyLabel.setText(textIfEmpty);
        if (dataToList.size() == 0)
            emptyLabel.setVisibility(View.VISIBLE);
        else
            emptyLabel.setVisibility(View.GONE);
    }

}