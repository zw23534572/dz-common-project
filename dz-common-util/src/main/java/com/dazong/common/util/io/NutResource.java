package com.dazong.common.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 *  @author zisong.wang
 *  @date 2018/01/12
 */
public abstract class NutResource {

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj instanceof NutResource && this.toString().equals(obj.toString());
    }

    /**使用完毕后,务必关闭*/
    public abstract InputStream getInputStream() throws IOException;

    public String getName() {
        return name;
    }

    /**使用完毕后,务必关闭*/
    public Reader getReader() throws IOException {
        return StreamUtil.utf8r(getInputStream());
    }

    @Override
    public int hashCode() {
        return null == name ? "NULL".hashCode() : name.hashCode();
    }

    public NutResource setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return String.format("NutResource[%s]", name);
    }

    protected String name;

}
