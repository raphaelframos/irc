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

    public static void add(IrcChannel channel, User user) {
        user.setOffice(Category.USER.getName());
        channel.add(user);
        updateUsers(channel);
        addChannel(channel, user);

    }
    public static void remove(IrcChannel channel, User user) {
        updateUsers(channel);
        getUser(user.getId()).collection(ConstantsUtils.ON_CHANNELS).document(channel.getId()).delete();
    }


    private static void updateUsers(IrcChannel channel) {
        getChannels().document(channel.getId()).update(channel.usersMap());
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
            DocumentReference ref = getChannels().document();
            ref.set(ircChannel);
            ircChannel.setId(ref.getId());
            addChannel(ircChannel, user);
        });
    }

    private static void addChannel(IrcChannel ircChannel, User user) {
        getUser(user.getId()).collection(ConstantsUtils.ON_CHANNELS).document(ircChannel.getId()).set(ircChannel);
    }

    public static CollectionReference getChannels() {
        return getDB().collection(ConstantsUtils.CHANNELS);
    }

    public static void banUserforChannel(String id, User user) {
        getChannels().document(id).collection(ConstantsUtils.BANNED).document(user.getId()).set(user);
    }

    public static CollectionReference getUsersBan(String id) {
        return FirebaseFirestore.getInstance().collection(ConstantsUtils.CHANNELS).document(id).collection(ConstantsUtils.BANNED);
    }


    public LiveData<List<IrcChannel>> getOnChannels(String userId) {
        getUser(userId).collection(ConstantsUtils.ON_CHANNELS).addSnapshotListener((queryDocumentSnapshots, e) -> {
            try{
                mutableLiveData.setValue(queryDocumentSnapshots.toObjects(IrcChannel.class));
            }catch (Exception e1){
                e1.printStackTrace();
            }

        });
        return mutableLiveData;
    }
}
