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
        getChannels().document(channel.getId()).update(channel.usersMap());
        setChannelLists(channel, user);
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

    public LiveData<IrcChannel> getMutableLiveDataOnChannel(String id) {

        getChannels().document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mutableOnChannelLiveData.setValue(documentSnapshot.toObject(IrcChannel.class));
            }
        });

        return mutableOnChannelLiveData;
    }

    public LiveData<List<String>> getMutableLiveDataOnChannelIds(String userId) {

        List<String> channels = new ArrayList<>();

        getDB().collection("channelList").document(userId).collection("accessed").addSnapshotListener((queryDocumentSnapshots, e) -> {
            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                channels.add((String) documentSnapshot.get("id"));
            }
            mutableOnChannelIdsLiveData.setValue(channels);
        });

        return mutableOnChannelIdsLiveData;
    }
    public LiveData<List<String>> getMutableLiveDataVisitedChannelIds(String userId) {

        List<String> channels = new ArrayList<>();

        getDB().collection("channelList").document(userId).collection("visited").addSnapshotListener((queryDocumentSnapshots, e) -> {
            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                if(documentSnapshot != null && documentSnapshot.get("id") != null){
                    channels.add((String) documentSnapshot.get("id"));
                }
            }
            visitedIdsLiveData.setValue(channels);
        });

        return visitedIdsLiveData;
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
            user.setOffice(Category.CREATOR.getName());
            ircChannel.add(user);
            getChannels().document().set(ircChannel);
        });
    }

    public static CollectionReference getChannels() {
        return getDB().collection(ConstantsUtils.CHANNELS);
    }

    public static void banUserforChannel(String id, User user) {
        getChannels().document(id).collection(ConstantsUtils.BANNED).document(user.getId()).set(user);
    }


    public LiveData<List<IrcChannel>> getAccessedChannels(List<String> ids) {
        List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
        for(String id : ids ){
            tasks.add(getChannels().document(id).get());
        }
        List<IrcChannel> channels = new ArrayList<>();
        Tasks.whenAllSuccess(tasks).addOnSuccessListener(list -> {
            for(Object o : list){
                channels.add(((DocumentSnapshot) o).toObject(IrcChannel.class));
            }
            accessedChannels.setValue(channels);
        });

        return accessedChannels;
    }

    private Task<Void> getChannels(final List<String> ids) {

        return getDB().runTransaction(transaction -> {
            List<IrcChannel> channels = new ArrayList<>();
            IrcChannel ircChannel = transaction.get(getChannels().document(ids.get(0))).toObject(IrcChannel.class);
            channels.add(ircChannel);

            return null;
        });
    }
}
