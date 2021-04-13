package com.example.estoque3.entity;

public abstract class EconomicOperation {

     private String id;
     private String Name;
     private double SealValue;
     private double ExpenseValue;
     private String type;
     private String mensage;
     private int quantity;

     abstract String save();
     abstract String update();
     abstract String remove();
}
