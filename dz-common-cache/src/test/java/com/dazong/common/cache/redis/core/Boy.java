package com.dazong.common.cache.redis.core;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author jianhao84
 * @date 2018-02-02
 * @time: 14:42
 */
public class Boy {
    private String name;
    private Integer age;
    private List<String> wifeList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getWifeList() {
        return wifeList;
    }

    public void setWifeList(List<String> wifeList) {
        this.wifeList = wifeList;
    }
}
