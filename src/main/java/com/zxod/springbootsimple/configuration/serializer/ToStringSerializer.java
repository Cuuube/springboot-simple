package com.zxod.springbootsimple.configuration.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;

public class ToStringSerializer implements ObjectSerializer {
    private static final long MAX_VALUE = 1L << 53;
    private static final long MIN_VALUE = -MAX_VALUE;
    public static final ToStringSerializer INSTANCE = new ToStringSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;

        if (object == null) {
            out.writeNull();
            return;
        }

        if (object instanceof Long) {
            long val = (Long) object;
            if (val >= MIN_VALUE && val <= MAX_VALUE) {
                out.writeLong(val);
                return;
            }
        }

        String strVal = object.toString();
        out.writeString(strVal);
    }
}