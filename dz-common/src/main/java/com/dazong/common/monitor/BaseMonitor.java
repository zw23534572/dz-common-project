package com.dazong.common.monitor;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author huqichao
 * Created by  on 17/5/25.
 *
 */
public abstract class BaseMonitor implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int ERROR = 500;
    public static final int SUCCESS = 200;

    public static final String NAME_DATABASE = "db";
    public static final String NAME_REDIS= "redis";
    public static final String NAME_DUBBO = "dubbo";
    public static final String NAME_MQ = "mq";
    public static final String NAME_VERSION = "version";
    public static final String NAME_TIME = "time";


    protected static Logger logger = LoggerFactory.getLogger(BaseMonitor.class);

    protected transient Object obj;

    public BaseMonitor(Object obj){
        this.obj = obj;
    }

    /**
     * 检测
     * @return 检测结果
     */
    public abstract CheckResult check();
}
