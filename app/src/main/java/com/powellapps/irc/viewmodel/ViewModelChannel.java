package com.powellapps.irc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.powellapps.irc.firebase.FirebaseRepository;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.utils.ConstantsUtils;

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

}