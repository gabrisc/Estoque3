package com.example.estoque3.entity;

import androidx.annotation.NonNull;

import com.example.estoque3.util.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.example.estoque3.util.FireBaseConfig.firebaseAuth;
import static com.example.estoque3.util.FireBaseConfig.firebaseDbReferenceProductPath;

public class Product extends EconomicOperation {
    private int quantity;
    private String id;
    private String Name;
    private double SealValue;
    private double ExpenseValue;
    private String type;
    private String mensage;

    public Product(int quantity, String name, double sealValue, double expenseValue, String type) {
        this.quantity = quantity;
        Name = name;
        SealValue = sealValue;
        ExpenseValue = expenseValue;
        this.type = type;
    }

    public Product() {
    }

    public String save(){
        firebaseDbReferenceProductPath.child(this.getIdUser()).child(String.valueOf(this.id)).setValue(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    mensage= "Produto Adicionado";
                }
        });
        return mensage;
    }

    public String remove(){
        firebaseDbReferenceProductPath.child(this.getIdUser()).child(this.id).removeValue();
        return "Produto Removido";
    }

    public String update(){
        firebaseDbReferenceProductPath.child(String.valueOf(this.id)).setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mensage= "Produto Atualizado";
                        }
                });
        return mensage;
    }


    public static String getIdUser() {
        return Base64Custom.Code64(firebaseAuth.getCurrentUser().getEmail());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getSealValue() {
        return SealValue;
    }

    public void setSealValue(double sealValue) {
        SealValue = sealValue;
    }

    public double getExpenseValue() {
        return ExpenseValue;
    }

    public void setExpenseValue(double expenseValue) {
        ExpenseValue = expenseValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
