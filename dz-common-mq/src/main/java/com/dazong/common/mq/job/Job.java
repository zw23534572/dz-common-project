package com.dazong.common.mq.job;

/**
 * mq 任务
 * @author huqichao
 * @date 2018-01-22 15:22
 **/
public interface Job {

    /**
     * 指定任务
     */
    void execute();
}
