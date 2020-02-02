package com.powellapps.irc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.powellapps.irc.R;
import com.powellapps.irc.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserChannelAdapter extends RecyclerView.Adapter<UserChannelAdapter.ViewHolder> {

    private List<User> users = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_users_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewName.setText(users.get(position).getChannelName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void update(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textView_user_name);
        }
    }
}
