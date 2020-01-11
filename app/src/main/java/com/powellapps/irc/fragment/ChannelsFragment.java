package com.powellapps.irc.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powellapps.irc.R;
import com.powellapps.irc.adapter.ChannelAdapter;
import com.powellapps.irc.viewmodel.ViewModelChannel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelsFragment extends Fragment {

    private ChannelAdapter adapter;
    private ViewModelChannel viewModelChannel;


    public ChannelsFragment() {
        // Required empty public constructor
    }

    public static ChannelsFragment newInstance(int position) {
        return new ChannelsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_channels, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerViewChannels = getView().findViewById(R.id.reciclerChannels);
        recyclerViewChannels.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewChannels.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
}
