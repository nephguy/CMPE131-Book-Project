package cmpe131.cmpebookproject.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.activity.viewpager.TabFragmentPagerAdapter;

public class MainActivity extends UserActivityBase {

    TabFragmentPagerAdapter tabFragmentPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("onCreate called in " + this.getLocalClassName());

        viewPager = findViewById(R.id.main_viewpager);
        tabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        // useless code?
        // app seems to function just fine without it
        // but the guide I used said to include it??
//        TabLayout tabLayout = findViewById(R.id.main_tablayout);
//        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart called in " + this.getLocalClassName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume called in " + this.getLocalClassName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop called in " + this.getLocalClassName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart called in " + this.getLocalClassName());
    }
}
