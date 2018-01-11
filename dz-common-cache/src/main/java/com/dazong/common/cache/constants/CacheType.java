package com.dazong.common.cache.constants;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */
public enum CacheType {

    /**
     * Redis缓存框架
     */
    CACHE_REDIS(1,"Redis缓存框架"),

    /**
     * Memcache缓存框架
     */
    CACHE_MEMCACHE(2,"Redis缓存框架"),

    /**
     * Local缓存框架
     */
    CACHE_LOCALCACHE(3,"Redis缓存框架");

    private CacheType(Integer type, String typeDesc){
        this.type = type;
        this.typeDesc = typeDesc;
    }
    private Integer type;
    private String typeDesc;

    public final Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

}
