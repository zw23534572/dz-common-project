package com.dazong.common.cache.constants;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */
public interface IExpire {
    int EXPIRE_MAX = Integer.MAX_VALUE;

    int ONE_MILL_SECOND = 1;
    int ONE_SEC = 1000 * ONE_MILL_SECOND;

    int FIVE_SEC = 5 * ONE_SEC;

    int TEN_SEC = 2 * FIVE_SEC;

    int ONE_MIN = 6 * TEN_SEC;

    int FIVE_MIN = 5 * ONE_MIN;

    int TEN_MIN = 2 * FIVE_MIN;

    int HALF_HOUR = 30 * TEN_MIN;

    int ONE_HOUR = 2 * HALF_HOUR;

    int TWO_HOUR = 2 * ONE_HOUR;

    int SIX_HOUR = 3 * TWO_HOUR;

    int TWELVE_HOUR = 2 * SIX_HOUR;

    int ONE_DAY = 2 * TWELVE_HOUR;

    int TWO_DAY = 2 * ONE_DAY;

    int ONE_WEEK = 7 * ONE_DAY;
}