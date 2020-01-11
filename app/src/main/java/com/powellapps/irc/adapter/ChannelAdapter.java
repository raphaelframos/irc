package com.powellapps.irc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.powellapps.irc.R;
import com.powellapps.irc.model.IrcChannel;

import java.util.ArrayList;
import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder>{

    private List<IrcChannel> channels = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(channels.get(position));
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public void update(List<IrcChannel> channels) {
        this.channels = channels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewChannelName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewChannelName = itemView.findViewById(R.id.textView_channel_name);
        }

        public void bind(IrcChannel ircChannel) {
            textViewChannelName.setText("#" + ircChannel.getName());
        }
    }
}
