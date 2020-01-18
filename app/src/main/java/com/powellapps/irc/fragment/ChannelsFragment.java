package com.powellapps.irc.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.powellapps.irc.R;
import com.powellapps.irc.adapter.ChannelAdapter;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.utils.ConstantsUtils;
import com.powellapps.irc.utils.FirebaseUtils;
import com.powellapps.irc.viewmodel.ViewModelChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelsFragment extends Fragment {

    private static final int ALL = 2;
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
        setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        int position = getArguments().getInt(ConstantsUtils.POSITION);
        Toast.makeText(getContext(), "Posicao " + position, Toast.LENGTH_LONG).show();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem mSearch = menu.findItem(R.id.item_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(getString(R.string.busca_canal));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findChannel(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                findChannel(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void findChannel(String newText) {
        FirebaseUtils.findChannels(newText).addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<IrcChannel> channels = new ArrayList<>();
            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                channels.add(documentSnapshot.toObject(IrcChannel.class));
            }
            adapter.update(channels, recyclerViewChannels);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.item_new_channel:
                NewChannelDialogFragment.newInstance(FirebaseUtils.getUserId()).show(getFragmentManager(), "newChannel");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
