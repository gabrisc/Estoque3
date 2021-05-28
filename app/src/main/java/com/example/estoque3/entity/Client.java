package com.example.estoque3.entity;

import androidx.annotation.NonNull;

import com.example.estoque3.util.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.example.estoque3.util.FireBaseConfig.firebaseAuth;
import static com.example.estoque3.util.FireBaseConfig.firebaseInstance;

public class Client {
    private String mensage;
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String date;


    public Client(String id, String nome, String email, String telefone,String date) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.date=date;
    }

    public Client() {
    }

    public String delete(){
        firebaseInstance.getReference()
                .child(getIdUser())
                .child("Clients")
                .child(String.valueOf(this.getId()))
                .removeValue();
        return "Cliente Removido";
    }

    public String save() {
        firebaseInstance.getReference()
                .child(getIdUser())
                .child("Clients")
                .child(String.valueOf(this.getId()))
                .setValue(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mensage = "Cadastrado com sucesso";
                    }
                });
        return mensage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public static String getIdUser() {
        return Base64Custom.Code64(firebaseAuth.getCurrentUser().getEmail());
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
