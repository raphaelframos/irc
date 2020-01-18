package com.powellapps.irc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.powellapps.irc.adapter.UserChannelAdapter;
import com.powellapps.irc.firebase.FirebaseRepository;
import com.powellapps.irc.model.MensagemChat;
import com.powellapps.irc.model.User;
import com.powellapps.irc.utils.ConstantsUtils;
import com.powellapps.irc.utils.FirebaseUtils;
import com.powellapps.irc.viewmodel.ViewModelChannel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private List<MensagemChat> messagelist;
    private ChatAdapter adapter;
    private FirebaseUser user;
    private EditText editTextMessage;
    private Button button;
    private UserChannelAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        user = FirebaseUtils.getUser();
        RecyclerView recyclerViewChat = findViewById(R.id.recyclerView_chat);
        RecyclerView recyclerViewUsers = findViewById(R.id.recyclerView_users);

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

        recyclerViewUsers.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        usersAdapter = new UserChannelAdapter();
        recyclerViewUsers.setAdapter(usersAdapter);

        String id = getIntent().getStringExtra(ConstantsUtils.ID);

        FirebaseRepository.add(id, new User(FirebaseUtils.getUser()));

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

        ViewModelProviders.of(this).get(ViewModelChannel.class).getUsersInChannel(id).observe(this, users -> {
            usersAdapter.update(users);
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
