package com.currency.qrcode.currency.model;

import com.currency.qrcode.currency.util.AddressCheck;

public enum OpeasenAttributeEnum {
    Close("Close"), HIGH("High"), LOW("Low"), COLOR("Color");

    private String type;

    OpeasenAttributeEnum(String type) {
        this.type = type;
    }

    public static OpeasenAttributeEnum getOpeasenAttributeEnum(String type){
        for(OpeasenAttributeEnum opeasenAttributeEnum:OpeasenAttributeEnum.values()){
            if(type.equals(opeasenAttributeEnum.getType())){
                return opeasenAttributeEnum;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
