package com.currency.qrcode.currency.model;

import com.currency.qrcode.currency.util.AddressCheck;

public enum CurrencyEnum {
    BTC("bitcoin"), ETH("etherenum"), TRX("tron"), USDT_OMNI("tether"), USDT_ERC20("tether"), USDT_TRC20("tether");

    private String currency;

    CurrencyEnum(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public static boolean checkAddress(CurrencyEnum currencyEnum, String address) {
        switch (currencyEnum) {
            case USDT_OMNI:
            case BTC:
                return AddressCheck.isBTCValidAddress(address);
            case USDT_ERC20:
            case ETH:
                return AddressCheck.isETHValidAddress(address);
            case USDT_TRC20:
            case TRX:
               return AddressCheck.isTRXValidAddress(address);
        }
        return true;
    }
}
