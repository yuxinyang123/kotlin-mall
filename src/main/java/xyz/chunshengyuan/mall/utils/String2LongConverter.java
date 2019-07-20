package xyz.chunshengyuan.mall.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

/**
 * @description: String2Long
 * @date: 2019-07-18 00:28
 * @author: yuxinyang
 * @version: 1.0
 */
public class String2LongConverter implements Converter<String, Long> {
    @Override
    public Long convert(String value) {
        return Long.valueOf(value);
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Long.class);
    }

}
