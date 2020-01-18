package com.powellapps.irc.firebase;


import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.model.User;
import com.powellapps.irc.utils.ConstantsUtils;
import com.powellapps.irc.utils.MessageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirebaseRepository {

    private MutableLiveData<List<IrcChannel>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> mutableLiveDataUsers = new MutableLiveData<>();

    public static void add(String id, User user) {
        user.add(id);
        getChannelUsers(id).document(user.getId()).set(user);
        getUser(user.getId()).set(user);
    }

    public static DocumentReference getUser(String userId) {
        return getDB().collection(ConstantsUtils.USERS).document(userId);
    }

    private static FirebaseFirestore getDB() {
        return FirebaseFirestore.getInstance();
    }
    

    public static void getOnChannels(String userId) {
        getUser(userId).get().addOnSuccessListener(documentSnapshot -> {

            User user = documentSnapshot.toObject(User.class);
            if(user != null) {
                List<String> channelsIds = (List<String>) documentSnapshot.get(ConstantsUtils.CHANNELS);
                if(channelsIds != null) {
                    user.setChannels(channelsIds);
                    for (String channelId : user.getChannels()) {
                        getChannels().document(channelId).addSnapshotListener((documentSnapshot1, e) -> {
                            IrcChannel channel = documentSnapshot1.toObject(IrcChannel.class);

                        });
                    }
                }
            }
        });
    }

    public LiveData<List<IrcChannel>> getMutableLiveData() {
        getChannels().orderBy(ConstantsUtils.NAME).addSnapshotListener((queryDocumentSnapshots, e) -> {
            mutableLiveData.setValue(queryDocumentSnapshots.toObjects(IrcChannel.class));
        });
        return mutableLiveData;
    }

    public LiveData<List<User>> getMutableLiveDataUsersInChannel(String id) {
        getChannelUsers(id).addSnapshotListener((queryDocumentSnapshots, e) -> {
            mutableLiveDataUsers.setValue(queryDocumentSnapshots.toObjects(User.class));
        });
        return mutableLiveDataUsers;
    }

    private static CollectionReference getChannelUsers(String id) {
        return getChannels().document(id).collection(ConstantsUtils.USERS);
    }


    public static CollectionReference getChat(String id) {
       return getChannels().document(id).collection(ConstantsUtils.CHAT);
    }

    public static void save(IrcChannel ircChannel) {
        getUser(ircChannel.getCreator()).addSnapshotListener( (documentSnapshot, e) -> {
            User user = documentSnapshot.toObject(User.class);
            ircChannel.add(user);
            getChannels().add(ircChannel);
        });
        /*
        getChannels().add(ircChannel.map()).addOnSuccessListener(documentReference -> {
            HashMap<String, Object> map = new HashMap<>();
            ArrayList<String> channels = new ArrayList<>();
            channels.add(documentReference.getId());
            map.put(ConstantsUtils.CHANNELS, channels);
            getUser(ircChannel.getCreator()).update(map);
        });

         */
    }

    public static CollectionReference getChannels() {
        return getDB().collection(ConstantsUtils.CHANNELS);
    }



}
