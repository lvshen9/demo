package com.lvshen.demo.distributelock;

import com.google.common.base.Charsets;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/15 20:48
 * @since JDK 1.8
 */
public class MyZkSerializer implements ZkSerializer {
    @Override
    public byte[] serialize(Object o) throws ZkMarshallingError {
        return String.valueOf(o).getBytes(Charsets.UTF_8);

    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        return new String(bytes, Charsets.UTF_8);
    }
}
