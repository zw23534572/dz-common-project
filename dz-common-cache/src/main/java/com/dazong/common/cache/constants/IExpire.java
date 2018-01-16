package com.dazong.common.cache.constants;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */
public final class IExpire {

    public static int EXPIRE_MAX = Integer.MAX_VALUE;

    public static int ONE_MILL_SECOND = 1;

    public static int ONE_SEC = 1000 * ONE_MILL_SECOND;

    public static int FIVE_SEC = 5 * ONE_SEC;

    public static int TEN_SEC = 2 * FIVE_SEC;

    public static int ONE_MIN = 6 * TEN_SEC;

    public static int FIVE_MIN = 5 * ONE_MIN;

    public static int TEN_MIN = 2 * FIVE_MIN;

    public static int HALF_HOUR = 30 * TEN_MIN;

    public static int ONE_HOUR = 2 * HALF_HOUR;

    public static int TWO_HOUR = 2 * ONE_HOUR;

    public static int SIX_HOUR = 3 * TWO_HOUR;

    public static int TWELVE_HOUR = 2 * SIX_HOUR;

    public static int ONE_DAY = 2 * TWELVE_HOUR;

    public static int TWO_DAY = 2 * ONE_DAY;

    public static int ONE_WEEK = 7 * ONE_DAY;
}