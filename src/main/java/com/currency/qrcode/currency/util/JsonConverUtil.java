package com.currency.qrcode.currency.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JsonConverUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJSON(Object obj) throws IOException {
        StringWriter writer = new StringWriter();
        MAPPER.writeValue(writer,obj);
        return writer.toString();
    }

    public static <T> T fromJSON(String json,Class<T> clazz) throws JsonProcessingException {
        return MAPPER.readValue(json,clazz);
    }
}
