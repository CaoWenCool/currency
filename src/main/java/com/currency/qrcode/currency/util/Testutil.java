package com.currency.qrcode.currency.util;


import com.fasterxml.jackson.core.JsonProcessingException;

public class Testutil {

    public static void main(String args[]) throws JsonProcessingException {
        String str = "{code=1.0, msg=成功, data=0.452878638825502756}";
        str = str.replace("{","");
        str = str.replace("}","");
        String[] strList = str.split("=");
        System.out.println(strList[3]);
    }
}
