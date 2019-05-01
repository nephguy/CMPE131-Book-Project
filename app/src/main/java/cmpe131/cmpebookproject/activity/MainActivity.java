package cmpe131.cmpebookproject.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.activity.viewpager.TabFragmentPagerAdapter;
import cmpe131.cmpebookproject.user.User;

import static cmpe131.cmpebookproject.Util.INTENT_DATA_USER;

public class MainActivity extends AppCompatActivity {

    User activeUser;
    TabFragmentPagerAdapter tabFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        activeUser = getIntent().getParcelableExtra(INTENT_DATA_USER);

        ViewPager viewPager = findViewById(R.id.main_viewpager);
        tabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), activeUser);
        viewPager.setAdapter(tabFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = findViewById(R.id.main_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }



}
