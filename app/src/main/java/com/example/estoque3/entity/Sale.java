package com.example.estoque3.entity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Iterator;
import java.util.List;

public class Sale {
    private List<EconomicOperation> economicOperationList;
    private List<EconomicOperationForSaleVo> economicOperationForSaleVos;
    private String id,date,paymentType;
    private Client client;
    private Double totalValue,discount;

    public List<EconomicOperation> getEconomicOperationList() {
        return economicOperationList;
    }

    public void setEconomicOperationList(List<EconomicOperation> economicOperationList) {
        this.economicOperationList = economicOperationList;
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

    public List<EconomicOperationForSaleVo> getEconomicOperationForSaleVos() {
        return economicOperationForSaleVos;
    }

    public void setEconomicOperationForSaleVos(List<EconomicOperationForSaleVo> economicOperationForSaleVos) {
        this.economicOperationForSaleVos = economicOperationForSaleVos;
    }
}
