package com.example.estoque3.entity;

public class EconomicOperationForSaleVo {
    private EconomicOperation economicOperation;
    private int quantitySelect;
    private Client client;

    public EconomicOperationForSaleVo(EconomicOperation economicOperation, int quantitySelect, Client client) {
        this.economicOperation = economicOperation;
        this.quantitySelect = quantitySelect;
        this.client = client;
    }

    public void calcTotalValue(){

    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
