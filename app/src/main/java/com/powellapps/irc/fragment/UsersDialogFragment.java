package com.powellapps.irc.fragment;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
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
import com.powellapps.irc.adapter.UsersDialogAdapter;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.model.User;
import com.powellapps.irc.viewmodel.ViewModelChannel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersDialogFragment extends DialogFragment {

    private UsersDialogAdapter adapter;
    private ViewModelChannel viewModelChannel;
    private List<User> userList;
    private String channelId;


    public static UsersDialogFragment newInstance() {
        UsersDialogFragment fragment = new UsersDialogFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_dialog, container, false);

        RecyclerView recyclerViewUsersDialog = view.findViewById(R.id.recycler_view_users_dialog);
        recyclerViewUsersDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsersDialog.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new UsersDialogAdapter(userList, channelId);
        recyclerViewUsersDialog.setAdapter(adapter);
        setHasOptionsMenu(true);




        return view;
    }

    public UsersDialogFragment setList(List<User> userList) {
      this.userList = userList;
      return this;
    }

    public UsersDialogFragment setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

}
