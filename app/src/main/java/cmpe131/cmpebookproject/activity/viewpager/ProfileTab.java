package cmpe131.cmpebookproject.activity.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cmpe131.cmpebookproject.R;

public class ProfileTab extends TabBaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_tab_profile, container, false);



        return view;
    }
}

