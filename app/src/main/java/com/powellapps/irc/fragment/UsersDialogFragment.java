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
    private ViewModelChannel viewModelChannel;
    private String channelId;
    private int codigo;
    private User user;



    public static UsersDialogFragment newInstance(List<User> userlist) {
        UsersDialogFragment fragment = new UsersDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userList", (Serializable) userlist);
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
        List<User> userList = (List<User>) bundle.getSerializable("userList");


        RecyclerView recyclerViewUsersDialog = view.findViewById(R.id.recycler_view_users_dialog);
        recyclerViewUsersDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsersDialog.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new UsersDialogAdapter(userList, channelId, this, codigo);
        recyclerViewUsersDialog.setAdapter(adapter);

        setHasOptionsMenu(true);

        return view;
    }

    public UsersDialogFragment setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public UsersDialogFragment setCodeComando(int codigo){
        this.codigo = codigo;
        return this;
    }
    public UsersDialogFragment setUser(User user) {
        this.user = user;
        return this;
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
