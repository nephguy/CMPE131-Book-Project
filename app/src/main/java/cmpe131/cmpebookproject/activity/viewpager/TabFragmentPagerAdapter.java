package cmpe131.cmpebookproject.activity.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cmpe131.cmpebookproject.R;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int numTabs = 3;

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TabFragmentBase.newInstance(TabFragmentList.class, R.layout.fragment_tab_list);
            case 1:
                return TabFragmentBase.newInstance(TabFragmentSearch.class, R.layout.fragment_tab_search);
            case 2:
                return TabFragmentBase.newInstance(TabFragmentProfile.class, R.layout.fragment_tab_profile);
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
