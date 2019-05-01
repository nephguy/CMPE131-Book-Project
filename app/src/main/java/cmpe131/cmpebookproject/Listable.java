package cmpe131.cmpebookproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

public interface Listable {
    @LayoutRes int getListViewLayoutRes();
    void populateListView(View listView);
    @Nullable Intent getDisplayIntent(Context context);
}
