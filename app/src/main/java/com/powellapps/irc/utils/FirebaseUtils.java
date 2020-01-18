package com.powellapps.irc.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.powellapps.irc.firebase.FirebaseRepository;
import com.powellapps.irc.model.User;

public class FirebaseUtils extends AppCompatActivity {

    public static FirebaseFirestore getBanco() {
        return FirebaseFirestore.getInstance();
    }

    public static void saveUser(final User user) {
        FirebaseUser userfirebase = FirebaseAuth.getInstance().getCurrentUser();
        String id = userfirebase.getUid();
        FirebaseUtils.getBanco().collection(ConstantsUtils.USERS).document(id).set(user.returnUser());
    }

    public static String getUserId(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static String getIdUser() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static CollectionReference getConversas(String id) {
        return FirebaseFirestore.getInstance().collection(ConstantsUtils.CHANNELS).document(id).collection(ConstantsUtils.CHAT);
    }

    public static Query findChannels(String substring) {
        Query query = FirebaseRepository.getChannels().whereGreaterThanOrEqualTo(ConstantsUtils.NAME, substring);
        return query;
    }

    public static CollectionReference getSizeChat(String id) {
        return FirebaseFirestore.getInstance().collection(ConstantsUtils.CHANNELS).document(id).collection(ConstantsUtils.USERS);
    }

    public static boolean isUser() {
        return getUser() != null && !getUserId().isEmpty();
    }
}
