/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Json 和 对象间的转换.
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/2/4 上午10:58
 * @since 1.0.0
 */
public class JsonConvertUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json
     *
     * @param obj 转换对象
     *
     * @return json串
     */
    public static String toJSON(Object obj) {
        StringWriter writer = new StringWriter();
        try {
            MAPPER.writeValue(writer, obj);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    /**
     * 将json转换成对象
     *
     * @param json json串
     * @param clazz 转换类型
     * @param <T> 泛型
     *
     * @return 转换对象
     */
    public static <T> T fromJSON(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
