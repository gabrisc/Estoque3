package com.example.estoque3.entity;

import com.example.estoque3.util.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.estoque3.util.FireBaseConfig.firebaseAuth;
import static com.example.estoque3.util.FireBaseConfig.firebaseDbReference;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public void save(){
        firebaseDbReference.child("Users")
                .child(this.id)
                .setValue(this);
    }

    public static String getId() {
        return Base64Custom.Code64(firebaseAuth.getCurrentUser().getEmail());
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
