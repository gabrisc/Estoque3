package com.example.estoque3.entity;

public class EconomicOperationForSaleVo {
    private EconomicOperation economicOperation;
    private int quantitySelect;

    public EconomicOperationForSaleVo(EconomicOperation economicOperation, int quantitySelect) {
        this.economicOperation = economicOperation;
        this.quantitySelect = quantitySelect;
    }

    public void calcTotalValue(){

    }

    public EconomicOperation getEconomicOperation() {
        return economicOperation;
    }

    public void setEconomicOperation(EconomicOperation economicOperation) {
        this.economicOperation = economicOperation;
    }

    public int getQuantitySelect() {
        return quantitySelect;
    }

    public void setQuantitySelect(int quantitySelect) {
        this.quantitySelect = quantitySelect;
    }
}
