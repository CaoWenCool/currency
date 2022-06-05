package com.currency.qrcode.currency.logging.patch.converter;

import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日志转换工厂
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
public class CustomConverterFactory {

    private static final Map<String, CustomConverter> converts = new ConcurrentHashMap<>();

    static {
        register(new CustomEmailConverter());
        register(new CustomMobilePhoneConverter());
        List<CustomConverter> loadFactories = SpringFactoriesLoader.loadFactories(CustomConverter.class,
                Thread.currentThread().getContextClassLoader());
        for (CustomConverter customerConverter : loadFactories) {
            register(customerConverter);
        }
    }

    public static String convert(String type, String fullMessage) {
        CustomConverter customConverter = converts.get(type);
        if (customConverter == null) {
            return fullMessage;
        }
        return customConverter.convert(fullMessage);
    }

    public static boolean hasConvert(String type) {
        return converts.containsKey(type);
    }

    public static void register(CustomConverter customerConverter) {
        converts.put(customerConverter.typeName(), customerConverter);
    }

}
