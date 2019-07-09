package xyz.chunshengyuan.mall.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

/**
 * @author leemaster
 * @Title: Long2StringConveter
 * @Package xyz.chunshengyuan.mall.utils
 * @Description:
 * @date 2019-07-1001:24
 */
public class Long2StringConveter implements Converter<Long,String> {
    @Override
    public String convert(Long value) {
        return value.toString();
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Long.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }
}
