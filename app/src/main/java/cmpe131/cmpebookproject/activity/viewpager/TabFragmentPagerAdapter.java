package cmpe131.cmpebookproject.activity.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    static final int numTabs = 3;

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabFragmentList();
            case 1:
                return new TabFragmentSearch();
            case 2:
                return new TabFragmentProfile();
            default:
                return null;
        }
    }

    @Override
    public String getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Lists";
            case 1:
                return "Search";
            case 2:
                return "Profile";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
