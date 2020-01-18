package com.powellapps.irc;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.powellapps.irc.firebase.FirebaseRepository;
import com.powellapps.irc.fragment.ChannelsFragment;
import com.powellapps.irc.utils.FirebaseUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        NavigationFragmentPagerAdapter adapter = new NavigationFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout_navigation);
        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setElevation(0);

    }


    class NavigationFragmentPagerAdapter extends FragmentStatePagerAdapter {

        private final Context context;

        public NavigationFragmentPagerAdapter(Context context, @NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.context = context;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return ChannelsFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {
                case 0:
                    return context.getString(R.string.ligados);
                case 1:
                    return context.getString(R.string.acessados);
                case 2:
                    return context.getString(R.string.todos);

                default:
                    return context.getString(R.string.disponivel);
            }
        }
    }
}
