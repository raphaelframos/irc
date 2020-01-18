package com.powellapps.irc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.powellapps.irc.firebase.FirebaseRepository;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.model.User;

import java.util.List;


public class ViewModelChannel extends AndroidViewModel {

    FirebaseRepository firebaseRepository;

    public ViewModelChannel(@NonNull Application application) {
        super(application);
        firebaseRepository = new FirebaseRepository();
    }

    public LiveData<List<IrcChannel>> getChannelsAccesseds() {
        return firebaseRepository.getMutableLiveData();
    }

    public LiveData<List<User>> getUsersInChannel(String id) {
        return firebaseRepository.getMutableLiveDataUsersInChannel(id);
    }

    public LiveData<List<String>> getOnChannelsIds(String userId) {
        return firebaseRepository.getMutableLiveDataOnChannelIds(userId);
    }

    public LiveData<IrcChannel> getOnChannel(String id) {
        return firebaseRepository.getMutableLiveDataOnChannel(id);
    }

    public LiveData<List<IrcChannel>> getAccessedChannels(List<String> ids) {
        return firebaseRepository.getAccessedChannels(ids);
    }
}