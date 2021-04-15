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
