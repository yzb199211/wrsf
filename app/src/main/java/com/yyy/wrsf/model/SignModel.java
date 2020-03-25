package com.yyy.wrsf.model;

public enum SignModel {
    NONE(1, 0), PAPER(2, 5.00), ELECTRONIC(3, 3.00);

    private int type;
    private double price;

    SignModel(int type, double price) {
        this.type = type;
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
