package com.powellapps.irc.firebase;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.model.User;
import com.powellapps.irc.utils.ConstantsUtils;

import java.util.List;

public class FirebaseRepository {

    private MutableLiveData<List<IrcChannel>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> mutableLiveDataUsers = new MutableLiveData<>();

    public static void add(String id, User user) {
        getUsers(id).document(user.getId()).set(user.returnUser());
    }

    public LiveData<List<IrcChannel>> getMutableLiveData() {
        getChannels().orderBy(ConstantsUtils.NAME).addSnapshotListener((queryDocumentSnapshots, e) -> {
            mutableLiveData.setValue(queryDocumentSnapshots.toObjects(IrcChannel.class));
        });
        return mutableLiveData;
    }

    public LiveData<List<User>> getMutableLiveDataUsersInChannel(String id) {
        getUsers(id).addSnapshotListener((queryDocumentSnapshots, e) -> {
            mutableLiveDataUsers.setValue(queryDocumentSnapshots.toObjects(User.class));
        });
        return mutableLiveDataUsers;
    }

    private static CollectionReference getUsers(String id) {
        return getChannels().document(id).collection(ConstantsUtils.USERS);
    }


    public static CollectionReference getChat(String id) {
       return getChannels().document(id).collection(ConstantsUtils.CHAT);
    }

    public static void save(IrcChannel ircChannel) {
        getChannels().add(ircChannel.map());
    }

    public static CollectionReference getChannels() {
        return FirebaseFirestore.getInstance().collection(ConstantsUtils.CHANNELS);
    }



}
