package com.powellapps.irc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.powellapps.irc.adapter.ChatAdapter;
import com.powellapps.irc.firebase.FirebaseRepository;
import com.powellapps.irc.model.MensagemChat;
import com.powellapps.irc.utils.ConstantsUtils;
import com.powellapps.irc.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private List<MensagemChat> messagelist;
    private ChatAdapter adapter;
    private FirebaseUser user;
    private EditText editTextMessage;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        user = FirebaseUtils.getUser();
        RecyclerView recyclerViewChat = findViewById(R.id.recyclerView_chat);
        button = findViewById(R.id.button);

        editTextMessage = findViewById(R.id.editText_mensagem);

        messagelist = new ArrayList<>();
        adapter = new ChatAdapter(messagelist);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewChat.setLayoutManager(layoutManager);
        recyclerViewChat.setHasFixedSize(true);
        recyclerViewChat.setAdapter(adapter);
        recyclerViewChat.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom) {
                recyclerViewChat.post(() -> recyclerViewChat.scrollToPosition(messagelist.size()));
            }
        });

        String id = getIntent().getStringExtra(ConstantsUtils.ID);

        FirebaseRepository.getChat(id).orderBy(ConstantsUtils.CREATION_DATE).addSnapshotListener((queryDocumentSnapshots, e) -> {
                messagelist.clear();
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    if(documentSnapshot.exists()) {
                        messagelist.add(documentSnapshot.toObject(MensagemChat.class));
                    }
                }

                if(messagelist.size() > 0) {
                adapter.update(messagelist);
            }
        });


        button.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString();

            try{
                    MensagemChat mensagemChat = new MensagemChat();
                    mensagemChat.setNameUser(user.getDisplayName());
                    mensagemChat.setIdUser(user.getUid());
                    mensagemChat.setText(message);
                    mensagemChat.setCreationDate(Calendar.getInstance().getTimeInMillis());
                    editTextMessage.setText("");
                    FirebaseUtils.getConversas(id).add(mensagemChat.getMap());

            }catch (Exception e){
                e.printStackTrace();

            }
        });



    }





}
