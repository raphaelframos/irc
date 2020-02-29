package com.powellapps.irc;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private List<MensagemChat> messagelist;
    private ChatAdapter adapter;
    private User user;
    private FloatingActionButton button;
    private UserChannelAdapter usersAdapter;
    private User usuario;
    private String[] COMANDOS = new String[] {
            "/kick", "/quit"
    };
    private AutoCompleteTextView editTextMessage;
    private IrcChannel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        RecyclerView recyclerViewChat = findViewById(R.id.recyclerView_chat);
        RecyclerView recyclerViewUsers = findViewById(R.id.recyclerView_users);


        button = findViewById(R.id.fab_send);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, COMANDOS);
        editTextMessage = findViewById(R.id.editText_mensagem);
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
        channel = (IrcChannel) getIntent().getSerializableExtra(ConstantsUtils.CHANNEL);
        getSupportActionBar().setTitle(channel.getName());
        usersAdapter.update(channel.getUsers());
        FirebaseRepository.getUser(FirebaseUtils.getUserId()).addSnapshotListener((documentSnapshot, e) -> {
            user = documentSnapshot.toObject(User.class);

            if (!channel.contain(user)) {
                try{
                    FirebaseRepository.add(channel, user);
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

        FirebaseRepository.getUser(FirebaseUtils.getUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                usuario = documentSnapshot.toObject(User.class);
            }
        });

        FirebaseRepository.getChat(channel.getId()).orderBy(ConstantsUtils.CREATION_DATE).addSnapshotListener((queryDocumentSnapshots, e) -> {

            messagelist.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                if (documentSnapshot.exists()) {
                    messagelist.add(documentSnapshot.toObject(MensagemChat.class));
                }
            }

            if (messagelist.size() > 0) {
                adapter.update(messagelist);
            }
        });

        button.setOnClickListener(v -> {

            String message = editTextMessage.getText().toString();

            switch (message) {
                case "/kick":
                    UsersDialogFragment.newInstance(channel).setUser(usuario).setCodeComando(1).show(getSupportFragmentManager(), "users");
                    break;
                case "/quit":
                    FirebaseRepository.remove(channel,user);
                    finish();
                    break;
                    default:
                        save(message);
            }
        });
    }

    private void save(String message){
        try {
            MensagemChat mensagemChat = new MensagemChat();
            mensagemChat.setNameUser(user.getNickname());
            mensagemChat.setIdUser(user.getId());
            mensagemChat.setText(message);
            mensagemChat.setCreationDate(Calendar.getInstance().getTimeInMillis());
            editTextMessage.setText("");
            FirebaseUtils.getConversas(channel.getId()).add(mensagemChat.getMap());
            MessageUtils.closeKeyboard(this, editTextMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.chat_menu, menu);
            return super.onCreateOptionsMenu(menu);
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_exit:
                channel.remove(user);
                FirebaseRepository.remove(channel, user);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
