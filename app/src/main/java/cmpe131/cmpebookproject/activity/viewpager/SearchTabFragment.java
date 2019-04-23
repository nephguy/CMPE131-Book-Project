package cmpe131.cmpebookproject.activity.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.user.User;

public class SearchTabFragment extends Fragment {

    public static final String KEY_DATA_ACTIVEUSER = "KEY_DATA_ACTIVEUSER";
    User activeUser;

    // newInstance constructor for creating fragment with arguments
    public static SearchTabFragment newInstance(User user) {
        SearchTabFragment tabBaseFragment = new SearchTabFragment();
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
        View view = inflater.inflate(R.layout.activity_main_tab_search, container, false);




        return view;
    }
}
