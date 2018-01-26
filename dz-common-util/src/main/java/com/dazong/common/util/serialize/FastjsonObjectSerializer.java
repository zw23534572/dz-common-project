package com.dazong.common.util.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @author Sam
 * @version 1.0.0
 */
public class FastjsonObjectSerializer extends AbstractObjectSerializer  implements ObjectSerializer {

    @Override
    public <T> byte[] doSerialize(T object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T doDeserialize(byte[] bytes,Class<T> type) {
        return JSON.parseObject(bytes,type);
    }
}
