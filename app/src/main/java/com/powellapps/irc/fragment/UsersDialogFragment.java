package com.powellapps.irc.fragment;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.powellapps.irc.R;
import com.powellapps.irc.adapter.ChannelAdapter;
import com.powellapps.irc.adapter.UsersDialogAdapter;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.model.User;
import com.powellapps.irc.viewmodel.ViewModelChannel;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersDialogFragment extends DialogFragment {

    private UsersDialogAdapter adapter;
    private int codigo;



    public static UsersDialogFragment newInstance(IrcChannel channel) {
        UsersDialogFragment fragment = new UsersDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("channel", channel);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users_dialog, container, false);
        Toolbar myToolbar = view.findViewById(R.id.toolbar_users_dialog);

        myToolbar.setTitle("Escolha o usuario!");
        Bundle bundle = getArguments();
        IrcChannel channel = (IrcChannel) bundle.getSerializable("channel");
        RecyclerView recyclerViewUsersDialog = view.findViewById(R.id.recycler_view_users_dialog);
        recyclerViewUsersDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsersDialog.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new UsersDialogAdapter(channel, this);
        recyclerViewUsersDialog.setAdapter(adapter);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null) {
            ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        }

    }
}
