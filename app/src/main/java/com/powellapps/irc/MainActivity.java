package com.powellapps.irc;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.powellapps.irc.firebase.FirebaseRepository;
import com.powellapps.irc.fragment.ChannelsFragment;
import com.powellapps.irc.fragment.NewChannelDialogFragment;
import com.powellapps.irc.utils.FirebaseUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
       // ViewPager viewPager = findViewById(R.id.viewPager);
   //     NavigationFragmentPagerAdapter adapter = new NavigationFragmentPagerAdapter(this, getSupportFragmentManager());
    //    viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout_navigation);
      //  tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Ligados"));
        tabLayout.addTab(tabLayout.newTab().setText("Todos"));
        ChannelsFragment fragment = ChannelsFragment.newInstance(FirebaseUtils.getUserId());
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_channels, fragment).commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == 0){
                  //  fragment.findOnChannels(FirebaseUtils.getUserId());
                }else{
                    fragment.findChannels();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
