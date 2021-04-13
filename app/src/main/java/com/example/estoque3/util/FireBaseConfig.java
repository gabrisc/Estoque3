package com.example.estoque3.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseConfig {

    public static FirebaseDatabase firebaseInstance = FirebaseDatabase.getInstance();
    public static DatabaseReference firebaseDbReference = firebaseInstance.getReference();
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static DatabaseReference firebaseDbReferenceRootPath = firebaseInstance.getReference().child(getIdUser()).child("ProductsAndServices");
    public static DatabaseReference firebaseDbReferenceProductPath = firebaseInstance.getReference().child("ProductsAndServices").child("PRODUCTS").child(getIdUser());
    public static DatabaseReference firebaseDbReferenceServicePath = firebaseInstance.getReference().child(getIdUser()).child("ProductsAndServices").child("SERVICES");

    public static String getIdUser() {
        return Base64Custom.Code64(firebaseAuth.getCurrentUser().getEmail());
    }
}
