package com.powellapps.irc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.google.android.material.tabs.TabLayout;
import com.powellapps.irc.adapter.ChannelAdapter;
import com.powellapps.irc.fragment.ChannelsFragment;
import com.powellapps.irc.fragment.NewChannelDialogFragment;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.viewmodel.ViewModelChannel;

import java.util.ArrayList;
import java.util.List;

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

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.item_search:
                break;

            case R.id.item_new_channel:
                NewChannelDialogFragment.newInstance("1").show(getSupportFragmentManager(), "newChannel");
                break;
        }
        return super.onOptionsItemSelected(item);
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
                    return context.getString(R.string.disponivel);
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
