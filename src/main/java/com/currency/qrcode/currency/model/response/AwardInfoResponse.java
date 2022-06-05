package com.currency.qrcode.currency.model.response;

import com.currency.qrcode.currency.mapper.opensea.model.OpeasenPO;

public class AwardInfoResponse {

    private OpeasenPO price;

    private OpeasenPO highPrice;

    private OpeasenPO lowPrice;

    public OpeasenPO getPrice() {
        return price;
    }

    public void setPrice(OpeasenPO price) {
        this.price = price;
    }

    public OpeasenPO getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(OpeasenPO highPrice) {
        this.highPrice = highPrice;
    }

    public OpeasenPO getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(OpeasenPO lowPrice) {
        this.lowPrice = lowPrice;
    }
}
