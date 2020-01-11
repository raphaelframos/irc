package com.powellapps.irc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.powellapps.irc.R;
import com.powellapps.irc.model.MensagemChat;
import com.powellapps.irc.utils.FirebaseUtils;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<MensagemChat> messages;
    private static final int TYPE_SEND = 0;
    private static final int TYPE_RECEIVE = 1;

    public ChatAdapter(List<MensagemChat> mensagemChats) {
        messages = mensagemChats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_RECEIVE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_receive, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_send, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MensagemChat mensagemChat = messages.get(position);
        holder.set(mensagemChat);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        MensagemChat message = messages.get(position);
        String idUser = FirebaseUtils.getIdUser();

        if (idUser.equals(message.getIdUser())){
            return TYPE_SEND;
        }

        return TYPE_RECEIVE;
    }

    public void update(List<MensagemChat> mensagens) {
        this.messages = mensagens;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        private TextView textViewName;
        private TextView textViewDate;
        private TextView textViewMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          textViewName = itemView.findViewById(R.id.textView_name_chat);
          textViewMessage = itemView.findViewById(R.id.textView_message);
          textViewDate = itemView.findViewById(R.id.textView_date_chat);
        }

        public void set(MensagemChat mensagemChat) {
            textViewDate.setText(mensagemChat.getCreationDate().toString());
            textViewName.setText(mensagemChat.getNameUser());
            textViewMessage.setText(mensagemChat.getText());
        }
    }
}
