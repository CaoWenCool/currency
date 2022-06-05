package com.currency.qrcode.currency.model.entityenum;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

public class EnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    private static final Map<Class, Converter> CONVERTER_MAP = new WeakHashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter result = CONVERTER_MAP.get(targetType);
        if (result == null) {
            result = new IntegerStrToEnum(targetType);
            CONVERTER_MAP.put(targetType, result);
        }
        return result;
    }

    class IntegerStrToEnum<T extends BaseEnum> implements Converter<Object, T> {
        private final Class<T> enumType;

        IntegerStrToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(Object source) {
            if (null == source || this.enumType == null) {
                throw new IllegalArgumentException("The Request enum parameter is null");
            }
            T[] enums = enumType.getEnumConstants();
            for (T e : enums) {
                if (Objects.equals(e.convertValue(), source) || Objects
                        .equals(e.convertValue().toString(), source.toString())) {
                    return e;
                }
            }
            throw new IllegalArgumentException("The Request enum parameter convert error,error "
                    + "parameter's value is" + source);
        }
    }
}
