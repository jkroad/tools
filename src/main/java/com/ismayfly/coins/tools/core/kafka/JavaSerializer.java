package com.ismayfly.coins.tools.core.kafka;

import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class JavaSerializer implements Serializer<Object> {
    @Override
    public byte[] serialize(String topic, Object data) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(data);
            objectStream.flush();
            objectStream.close();
            return byteStream.toByteArray();
        }catch (IOException e){
            throw new IllegalStateException("Can't serialize object: " + data, e);

        }
    }
}
