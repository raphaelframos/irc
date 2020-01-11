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
import com.powellapps.irc.utils.ConstantsUtils;
import com.powellapps.irc.viewmodel.ViewModelChannel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelsFragment extends Fragment {

    private static final int ALL = 0;
    private ChannelAdapter adapter;
    private ViewModelChannel viewModelChannel;
    private RecyclerView recyclerViewChannels;


    public ChannelsFragment() {}

    public static ChannelsFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantsUtils.POSITION, position);
        ChannelsFragment fragment = new ChannelsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_channels, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerViewChannels = getView().findViewById(R.id.reciclerChannels);
        recyclerViewChannels.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewChannels.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new ChannelAdapter();
        recyclerViewChannels.setAdapter(adapter);
        viewModelChannel = ViewModelProviders.of(this).get(ViewModelChannel.class);

    }

    @Override
    public void onResume() {
        super.onResume();
        int position = getArguments().getInt(ConstantsUtils.POSITION);
        if(position == ALL){
            getAllChannels();
        }else{
            adapter.update(new ArrayList<>(), recyclerViewChannels);
        }

    }

    private void getAllChannels() {
        viewModelChannel.getChannelsAccesseds().observe(this, ircChannels -> {
            adapter.update(ircChannels, recyclerViewChannels);
        });
    }
}
