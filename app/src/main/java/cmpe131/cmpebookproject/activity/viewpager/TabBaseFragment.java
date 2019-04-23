package cmpe131.cmpebookproject.activity.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import cmpe131.cmpebookproject.user.User;

public class TabBaseFragment extends Fragment {

    public static final String KEY_DATA_ACTIVEUSER = "KEY_DATA_ACTIVEUSER";
    User activeUser;

    // newInstance constructor for creating fragment with arguments
    public static TabBaseFragment newInstance(User user) {
        TabBaseFragment tabBaseFragment = new TabBaseFragment();
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
}
