package com.currency.qrcode.currency.logging.patch.converter;

/**
 * 日志转换接口
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
public interface CustomConverter {

    public String convert(String fullMessage);

    public String typeName();

}
