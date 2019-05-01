package cmpe131.cmpebookproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cmpe131.cmpebookproject.Listable;
import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.Util;

public class ListActivity extends AppCompatActivity {

    ArrayList<Listable> dataToList = new ArrayList<>();

    TextView listTitle;
    LinearLayout listContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<?> data = getIntent().getParcelableArrayListExtra(Util.INTENT_DATA_ARRAYTOLIST);
        boolean forceShowEmptyList = getIntent().getBooleanExtra(Util.INTENT_DATA_LIST_FORCESHOWEMPTYLIST, false);
        for (Object o : data) {
            if (o instanceof Listable)
                dataToList.add((Listable)o);
        }
        if (dataToList.size() == 0 && !forceShowEmptyList) {
            Util.longToast(getApplicationContext(),"Error: un-listable data");
            finish();
        }

        listTitle = findViewById(R.id.list_label_title);
        listContainer = findViewById(R.id.list_layout_listcontainer);

        listTitle.setText(getIntent().getStringExtra(Util.INTENT_DATA_LISTTITLE));
        for (Listable l : dataToList)
            listContainer.addView(l.getListView(getLayoutInflater()));
    }
}
