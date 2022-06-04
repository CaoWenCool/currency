package com.currency.qrcode.currency.model.response;

public class CoinPriceResponse {

    private Double price;

    private Double hightPirce;

    private Double lowPrice;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getHightPirce() {
        return hightPirce;
    }

    public void setHightPirce(Double hightPirce) {
        this.hightPirce = hightPirce;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }
}
