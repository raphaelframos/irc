package com.powellapps.irc.firebase;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.powellapps.irc.model.IrcChannel;

import java.util.List;

public class FirebaseRepository {

    private MutableLiveData<List<IrcChannel>> mutableLiveData = new MutableLiveData<>();

    public LiveData<List<IrcChannel>> getMutableLiveData() {
        return mutableLiveData;
    }
}
