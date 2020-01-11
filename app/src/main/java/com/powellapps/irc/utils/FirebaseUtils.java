package com.powellapps.irc.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.powellapps.irc.model.User;

public class FirebaseUtils extends AppCompatActivity {

    public static FirebaseFirestore getBanco() {
        return FirebaseFirestore.getInstance();
    }

    public static void saveUser(final User user) {
        FirebaseUser userfirebase = FirebaseAuth.getInstance().getCurrentUser();
        String id = userfirebase.getUid();
        FirebaseUtils.getBanco().collection(ConstantUtils.USERS).document(id).set(user.returnUser());
    }

    public static String getUserId(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}
