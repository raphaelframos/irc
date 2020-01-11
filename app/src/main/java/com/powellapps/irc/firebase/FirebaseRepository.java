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
import com.powellapps.irc.utils.ConstantsUtils;

import java.util.List;

public class FirebaseRepository {

    private MutableLiveData<List<IrcChannel>> mutableLiveData = new MutableLiveData<>();

    public LiveData<List<IrcChannel>> getMutableLiveData() {
        getChannels().addSnapshotListener((queryDocumentSnapshots, e) -> {
            mutableLiveData.setValue(queryDocumentSnapshots.toObjects(IrcChannel.class));
        });
        return mutableLiveData;
    }


    public static CollectionReference getChat(String id) {
       return FirebaseFirestore.getInstance().collection("ConstantUtils.CHANNELS").document(id).collection("conversas");
    }

    public static void save(IrcChannel ircChannel) {
        getChannels().add(ircChannel.map());
    }

    public static CollectionReference getChannels() {
        return FirebaseFirestore.getInstance().collection(ConstantsUtils.CHANNELS);
    }



}
