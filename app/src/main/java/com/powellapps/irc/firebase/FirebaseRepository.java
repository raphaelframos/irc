package com.powellapps.irc.firebase;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.powellapps.irc.model.Category;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.model.User;
import com.powellapps.irc.utils.ConstantsUtils;
import java.util.ArrayList;
import java.util.List;

public class FirebaseRepository {

    private MutableLiveData<List<IrcChannel>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String>> mutableOnChannelIdsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String>> visitedIdsLiveData = new MutableLiveData<>();
    private MutableLiveData<IrcChannel> mutableOnChannelLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> mutableLiveDataUsers = new MutableLiveData<>();
    private MutableLiveData<List<IrcChannel>> accessedChannels = new MutableLiveData<>();

    public static void add(IrcChannel channel, User user) {
        user.setOffice(Category.USER.getName());
        channel.add(user);
        updateUsers(channel);
    }
    public static void remove(IrcChannel channel) {
        updateUsers(channel);
    }


    private static void updateUsers(IrcChannel channel) {
        getChannels().document(channel.getId()).update(channel.usersMap());
    }

    private static void setChannelLists(IrcChannel channel, User user) {
        getDB().collection("channelList").document(user.getId()).collection("on").add(channel.idMap());
        getDB().collection("channelList").document(user.getId()).collection("visited").add(channel.idMap());
    }

    public static DocumentReference getUser(String userId) {
        return getDB().collection(ConstantsUtils.USERS).document(userId);
    }

    private static FirebaseFirestore getDB() {
        return FirebaseFirestore.getInstance();
    }

    public LiveData<List<IrcChannel>> getMutableLiveData() {
        getChannels().orderBy(ConstantsUtils.NAME).addSnapshotListener((queryDocumentSnapshots, e) -> {
            try{
                mutableLiveData.setValue(queryDocumentSnapshots.toObjects(IrcChannel.class));
            }catch (Exception e1){
                e1.printStackTrace();
            }

        });
        return mutableLiveData;
    }


    public static CollectionReference getChat(String id) {
       return getChannels().document(id).collection(ConstantsUtils.CHAT);
    }

    public static void save(IrcChannel ircChannel) {
        getUser(ircChannel.getCreator()).addSnapshotListener( (documentSnapshot, e) -> {
            User user = documentSnapshot.toObject(User.class);
            user.setOffice(Category.CREATOR.getName());
            ircChannel.add(user);
            getChannels().document().set(ircChannel);
            getUser(user.getId()).collection("on_channels").add(ircChannel);
        });
    }

    public static CollectionReference getChannels() {
        return getDB().collection(ConstantsUtils.CHANNELS);
    }

    public static void banUserforChannel(String id, User user) {
        getChannels().document(id).collection(ConstantsUtils.BANNED).document(user.getId()).set(user);
    }

    public static void exitChannel(String id, User user) {
    }

    public static CollectionReference getUsersBan(String id) {
        return FirebaseFirestore.getInstance().collection(ConstantsUtils.CHANNELS).document(id).collection(ConstantsUtils.BANNED);
    }


    public LiveData<List<IrcChannel>> getOnChannels(String userId) {
        getUser(userId).collection("on_channels").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                mutableLiveData.setValue(queryDocumentSnapshots.toObjects(IrcChannel.class));
            }
        });
        return mutableLiveData;
    }
}
