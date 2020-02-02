package com.powellapps.irc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.powellapps.irc.ChatActivity;
import com.powellapps.irc.R;
import com.powellapps.irc.firebase.FirebaseRepository;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.model.User;
import com.powellapps.irc.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;

public class UsersDialogAdapter extends RecyclerView.Adapter<UsersDialogAdapter.ViewHolder>{

    private List<User> users;
    private String channelId;
    private DialogFragment dialogFragment;
    private int codigo;


    public UsersDialogAdapter(List<User> users, String channelId, DialogFragment dialogFragment, int codigo) {
        this.users = users;
        this.channelId = channelId;
        this.dialogFragment = dialogFragment;
        this.codigo = codigo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_users_dialog, parent, false);

       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = users.get(position);

        holder.textViewName.setText(user.getName());

      holder.itemView.setOnClickListener((View v) -> {

          switch (codigo) {
              case 1:
                  FirebaseRepository.banUserforChannel(channelId,user);
                  dialogFragment.dismiss();
                  break;
              case 2:
                  
          }

        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textView_users_name_dialog);
        }
    }
}
