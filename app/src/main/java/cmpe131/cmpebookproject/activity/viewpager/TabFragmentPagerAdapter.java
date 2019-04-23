package cmpe131.cmpebookproject.activity.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cmpe131.cmpebookproject.user.User;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    static final int numTabs = 3;
    private User activeUser;

    public TabFragmentPagerAdapter(FragmentManager fm, User activeUser) {
        super(fm);
        this.activeUser = activeUser;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ListTab.newInstance(activeUser);
            case 1:
                return SearchTab.newInstance(activeUser);
            case 2:
                return ProfileTab.newInstance(activeUser);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
