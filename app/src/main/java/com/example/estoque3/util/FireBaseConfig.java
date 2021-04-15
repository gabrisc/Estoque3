package com.example.estoque3.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseConfig {

    public static FirebaseDatabase firebaseInstance = FirebaseDatabase.getInstance();
    public static DatabaseReference firebaseDbReference = firebaseInstance.getReference();
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static String getIdUser() {
        return Base64Custom.Code64(firebaseAuth.getCurrentUser().getEmail());
    }
}
