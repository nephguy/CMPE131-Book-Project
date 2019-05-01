package cmpe131.cmpebookproject.activity.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cmpe131.cmpebookproject.ApplicationManager;
import cmpe131.cmpebookproject.user.User;

public abstract class TabFragmentBase extends Fragment {

    User activeUser;

    /** Block commented code is deprecated, but kept for posterity. It was good code. RIP. **/

    /*
    // newInstance constructor for creating fragment with arguments
    public static TabFragmentList newInstance(User user) {
        TabFragmentList tabBaseFragment = new TabFragmentList();
        Bundle args = new Bundle();
        args.putParcelable(Util.KEY_DATA_ACTIVEUSER, user);
        tabBaseFragment.setArguments(args);
        return tabBaseFragment;
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activeUser = getArguments().getParcelable(Util.KEY_DATA_ACTIVEUSER);
        activeUser = ApplicationManager.getActiveUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        activeUser = ApplicationManager.getActiveUser();
    }
}
