package com.powellapps.irc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.powellapps.irc.adapter.ChatAdapter;
import com.powellapps.irc.adapter.UserChannelAdapter;
import com.powellapps.irc.firebase.FirebaseRepository;
import com.powellapps.irc.fragment.UsersDialogFragment;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.model.MensagemChat;
import com.powellapps.irc.model.User;
import com.powellapps.irc.utils.ConstantsUtils;
import com.powellapps.irc.utils.FirebaseUtils;
import com.powellapps.irc.utils.MessageUtils;
import com.powellapps.irc.viewmodel.ViewModelChannel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private List<MensagemChat> messagelist;
    private ChatAdapter adapter;
    private FirebaseUser user;
    private Button button;
    private UserChannelAdapter usersAdapter;
    private List<User> list = new ArrayList<>();
    private String[] COMANDOS = new String[] {
            "/kick", "/sussurrar", "/silenciar"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        user = FirebaseUtils.getFirebaseUser();
        RecyclerView recyclerViewChat = findViewById(R.id.recyclerView_chat);
        RecyclerView recyclerViewUsers = findViewById(R.id.recyclerView_users);


        button = findViewById(R.id.button);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, COMANDOS);
        AutoCompleteTextView editTextMessage = findViewById(R.id.editText_mensagem);

        editTextMessage.setAdapter(arrayAdapter);

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

        IrcChannel channel = (IrcChannel) getIntent().getSerializableExtra(ConstantsUtils.CHANNEL);
        FirebaseRepository.getUser(FirebaseUtils.getUserId()).addSnapshotListener((documentSnapshot, e) -> {
            User user = documentSnapshot.toObject(User.class);

            if(!channel.contain(user)){
                FirebaseRepository.add(channel, user);
            }

        });




        FirebaseRepository.getChat(channel.getId()).orderBy(ConstantsUtils.CREATION_DATE).addSnapshotListener((queryDocumentSnapshots, e) -> {
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

        ViewModelProviders.of(this).get(ViewModelChannel.class).getUsersInChannel(channel.getId()).observe(this, users -> {
            usersAdapter.update(users);
            list = users;
        });


        button.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString();

            if(message.equals("/kick")) {
                UsersDialogFragment.newInstance().setList(list).setChannelId(channel.getId()).show(getSupportFragmentManager(), "users");
            } else if (message.equals("/sussurrar")) {


            }

            try{
                    MensagemChat mensagemChat = new MensagemChat();
                    mensagemChat.setNameUser(user.getDisplayName());
                    mensagemChat.setIdUser(user.getUid());
                    mensagemChat.setText(message);
                    mensagemChat.setCreationDate(Calendar.getInstance().getTimeInMillis());
                    editTextMessage.setText("");
                    FirebaseUtils.getConversas(channel.getId()).add(mensagemChat.getMap());

            }catch (Exception e){
                e.printStackTrace();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }





}
