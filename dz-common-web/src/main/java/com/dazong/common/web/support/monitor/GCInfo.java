package com.dazong.common.web.support.monitor;

/**
 * @author huqichao
 * @date 2018-01-17 16:29
 **/
public class GCInfo {

    private String name;

    private long count;

    private long time;

    public GCInfo(String name, long count, long time) {
        this.name = name;
        this.count = count;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "GCInfo{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", time=" + time +
                '}';
    }
}
