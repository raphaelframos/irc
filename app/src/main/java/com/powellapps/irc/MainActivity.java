package com.powellapps.irc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.powellapps.irc.adapter.ChannelAdapter;
import com.powellapps.irc.fragment.NewChannelDialogFragment;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.viewmodel.ViewModelChannel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ChannelAdapter adapter;
    private ViewModelChannel viewModelChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewChannels = findViewById(R.id.reciclerChannels);
        recyclerViewChannels.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChannels.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new ChannelAdapter();
        recyclerViewChannels.setAdapter(adapter);

        viewModelChannel = ViewModelProviders.of(this).get(ViewModelChannel.class);
        getChannels();

    }

    private void getChannels() {
        viewModelChannel.getChannelsAccesseds().observe(this, ircChannels -> {
            adapter.update(ircChannels);
        });
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
}
