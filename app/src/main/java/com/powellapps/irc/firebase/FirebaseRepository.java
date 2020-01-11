package com.powellapps.irc.firebase;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.utils.ConstantUtils;

import java.util.List;

public class FirebaseRepository {

    private MutableLiveData<List<IrcChannel>> mutableLiveData = new MutableLiveData<>();

    public LiveData<List<IrcChannel>> getMutableLiveData() {
        return mutableLiveData;
    }

    public static CollectionReference getChannels() {
        return FirebaseFirestore.getInstance().collection("channels");
    }


    public static CollectionReference getChat(String id) {
       return FirebaseFirestore.getInstance().collection("channels").document(id).collection("conversas");
    }
}
