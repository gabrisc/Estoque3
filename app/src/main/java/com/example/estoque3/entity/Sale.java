package com.example.estoque3.entity;

import androidx.annotation.NonNull;

import com.example.estoque3.util.Base64Custom;
import com.example.estoque3.util.TypeOfProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.example.estoque3.Activity.AddActivitys.AddSaleActivity.economicOperationForSaleVoArrayList;
import static com.example.estoque3.util.FireBaseConfig.firebaseAuth;
import static com.example.estoque3.util.FireBaseConfig.firebaseInstance;

public class Sale {
    private List<EconomicOperationForSaleVo> economicOperationForSaleVoList;
    private String id,date,paymentType;
    private Client client;
    private Double totalValue,discount;

    public Sale(String id, String date, Client client, Double totalValue, Double discount) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.totalValue = totalValue;
    }

    public void save(){
        firebaseInstance.getReference()
                .child(getIdUser())
                .child("Sales")
                .child(this.getId())
                .setValue(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }});
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public List<EconomicOperationForSaleVo> getEconomicOperationForSaleVoList() {
        return economicOperationForSaleVoList;
    }

    public void setEconomicOperationForSaleVoList(List<EconomicOperationForSaleVo> economicOperationForSaleVoList) {
        this.economicOperationForSaleVoList = economicOperationForSaleVoList;
    }
}
