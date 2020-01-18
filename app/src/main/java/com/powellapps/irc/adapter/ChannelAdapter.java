package com.powellapps.irc.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.powellapps.irc.ChatActivity;
import com.powellapps.irc.R;
import com.powellapps.irc.model.IrcChannel;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.powellapps.irc.ChatActivity;
import com.powellapps.irc.R;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.utils.ConstantsUtils;
import com.powellapps.irc.utils.FirebaseUtils;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder>{

    private final FragmentActivity activity;
    private List<IrcChannel> channels = new ArrayList<>();

    public ChannelAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IrcChannel channel = channels.get(position);
        holder.bind(channel);
        holder.itemView.setOnClickListener(v -> {
            Intent it = new Intent(activity, ChatActivity.class);
            it.putExtra(ConstantsUtils.CHANNEL, channel);
            activity.startActivity(it);
        });
        
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public void update(List<IrcChannel> channels, RecyclerView recyclerViewChannels) {
        this.channels = channels;
        runLayoutAnimation(recyclerViewChannels);
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public void clean() {
        this.channels = new ArrayList<>();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewChannelName;
        TextView textViewChannelUsers;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewChannelName = itemView.findViewById(R.id.textView_channel_name);
            textViewChannelUsers = itemView.findViewById(R.id.textView_channel_quatidade);
        }

        public void bind(IrcChannel ircChannel) {
            textViewChannelName.setText("#" + ircChannel.getName());
            textViewChannelUsers.setText("("+ ircChannel.getUsers().size()+" usuários)");
        }



    }
}
