package com.currency.qrcode.currency.model;

/**
 * @author: 曹文
 * @create: 2019/10/23
 * @update: 20:41
 * @version: V1.0
 * @detail: 官网网站的 响应码
 **/
public enum ApiCodeGemu {

    SUCCESS(200, "操作成功","success"),

    NOT_EXIST(3001,"不存在","Non-existent"),
    MORE_EXIST(3002,"存在多个","more exist");

    private final Integer code;
    private final String zh;
    private final String en;


    ApiCodeGemu(Integer code, String zh, String en){
        this.code = code;
        this.en = en;
        this.zh = zh;
    }
    public int getCode() {
        return code;
    }

    public String getZh() {
        return zh;
    }

    public String getEn() {
        return en;
    }


    public String getMessage(Integer code,String language){
        for(ApiCodeGemu apiCodeGemu:ApiCodeGemu.values()){
            if(code == apiCodeGemu.getCode()){
                if(language.equals("zh")){
                    return apiCodeGemu.getZh();
                }else {
                    return apiCodeGemu.getEn();
                }
            }
        }
        return null;
    }
}
