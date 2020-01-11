package com.powellapps.irc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.powellapps.irc.R;
import com.powellapps.irc.model.MensagemChat;
import com.powellapps.irc.utils.FirebaseUtils;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<MensagemChat> mensagens;
    private static final int TIPO_ENVIA = 0;
    private static final int TIPO_RECEBE = 1;

    public ChatAdapter(List<MensagemChat> mensagemChats) {
        mensagens = mensagemChats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == TIPO_RECEBE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recebe, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_envia, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MensagemChat mensagemChat = mensagens.get(position);
        holder.set(mensagemChat);
    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }

    @Override
    public int getItemViewType(int position) {
        MensagemChat mensagem = mensagens.get(position);
        String idUser = FirebaseUtils.getIdUser();

        if (idUser.equals(mensagem.getIdUser())){
            return TIPO_ENVIA;
        }

        return TIPO_RECEBE;
    }

    public void update(List<MensagemChat> mensagens) {
        this.mensagens = mensagens;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        private TextView textViewName;
        private TextView textViewDate;
        private TextView textViewMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          textViewName = itemView.findViewById(R.id.textView_nome_chat);
          textViewMessage = itemView.findViewById(R.id.textView_mensagem);
          textViewDate = itemView.findViewById(R.id.textView_data_chat);
        }

        public void set(MensagemChat mensagemChat) {
            textViewDate.setText(mensagemChat.getCreationDate().toString());
            textViewName.setText(mensagemChat.getNameUser());
            textViewMessage.setText(mensagemChat.getText());
        }
    }
}
