package cmpe131.cmpebookproject.activity;

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

public abstract class ListActivityBase extends UserActivityBase {

    ArrayList<? extends Listable> dataset;
    int viewLayoutRes;
    String textIfEmpty;

    TextView listTitle;
    RecyclerView listRecyclerView;
    TextView emptyLabel;
    Button actionButton;
    private RecyclerViewListableAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listTitle = findViewById(R.id.list_label_title);
        listRecyclerView = findViewById(R.id.list_layout_listrecyclerview);
        emptyLabel = findViewById(R.id.list_label_listempty);
        actionButton = findViewById(R.id.list_button_actionbutton);

        listTitle.setText(getIntent().getStringExtra(Util.INTENT_DATA_LIST_LISTTITLE));
        dataset = getIntent().getParcelableArrayListExtra(Util.INTENT_DATA_LIST_DATASET);
        viewLayoutRes = getIntent().getIntExtra(Util.INTENT_DATA_LIST_LAYOUTRES, 0);
        textIfEmpty = getIntent().getStringExtra(Util.INTENT_DATA_LIST_LABELIFEMPTY);
        if (textIfEmpty == null)
            textIfEmpty = "empty";

        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        recyclerViewAdapter = new RecyclerViewListableAdapter(dataset, viewLayoutRes, true);
        listRecyclerView.setAdapter(recyclerViewAdapter);

        checkListEmpty();
    }

    private void checkListEmpty() {
        emptyLabel.setText(textIfEmpty);
//        if (dataset == null)
//            emptyLabel.setVisibility(View.VISIBLE);
        if (dataset.size() == 0)
            emptyLabel.setVisibility(View.VISIBLE);
        else
            emptyLabel.setVisibility(View.GONE);
    }

    protected void updateList(ArrayList<? extends Listable> dataset) {
        this.dataset = dataset;
        recyclerViewAdapter.updateList(dataset);
        checkListEmpty();
    }

}