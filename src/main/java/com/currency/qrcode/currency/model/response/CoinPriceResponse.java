package com.currency.qrcode.currency.model.response;

import java.math.BigDecimal;

public class CoinPriceResponse {

    private BigDecimal price;

    private BigDecimal hightPirce;

    private BigDecimal lowPrice;


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getHightPirce() {
        return hightPirce;
    }

    public void setHightPirce(BigDecimal hightPirce) {
        this.hightPirce = hightPirce;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }
}
