package com.example.estoque3.entity;

import androidx.annotation.NonNull;
import com.example.estoque3.util.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import static com.example.estoque3.util.FireBaseConfig.firebaseAuth;
import static com.example.estoque3.util.FireBaseConfig.firebaseDbReferenceRootPath;

public class Service extends EconomicOperation {
    private String id;
    private String Name;
    private double SealValue;
    private double ExpenseValue;
    private String type;
    private String mensage;

    public Service(String name, double sealValue, double expenseValue, String type) {
        Name = name;
        SealValue = sealValue;
        ExpenseValue = expenseValue;
        this.type = type;
    }

    @Override
    public String save() {
        firebaseDbReferenceRootPath.child("SERVICES")
                .child(this.getIdUser())
                .child(String.valueOf(this.id))
                .setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mensage= "Serviço Adicionado";
            }
        });
        return mensage;
    }

    @Override
    public String update() {
        firebaseDbReferenceRootPath.child("SERVICES").child(getIdUser()).child(String.valueOf(this.id)).setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mensage = "Serviço Atualizado";
            }
        });
    return mensage;
    }

    @Override
    public String remove() {
        firebaseDbReferenceRootPath.child("SERVICES").child(this.getIdUser()).child(this.id).removeValue();
        return "Serviço Removido";
    }

    private String getIdUser() {
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
}
