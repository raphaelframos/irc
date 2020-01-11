package com.powellapps.irc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.powellapps.irc.adapter.ChannelAdapter;
import com.powellapps.irc.model.IrcChannel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ChannelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewChannels = findViewById(R.id.reciclerChannels);
        recyclerViewChannels.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChannels.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new ChannelAdapter();
        recyclerViewChannels.setAdapter(adapter);

        List<IrcChannel> channels = new ArrayList<>();
        channels.add(new IrcChannel());
        channels.add(new IrcChannel());
        channels.add(new IrcChannel());
        channels.add(new IrcChannel());
        channels.add(new IrcChannel());
        adapter.update(channels);



    }


}
