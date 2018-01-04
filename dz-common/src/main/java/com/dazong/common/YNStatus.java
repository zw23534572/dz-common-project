package com.dazong.common;

/**
 * @author huqichao
 * @create 2017-10-24 10:45
 **/
public enum YNStatus implements IResultStatus {

    YES() {
        @Override
        public int getCode() {
            return 1;
        }

        @Override
        public String getMessage() {
            return "有效";
        }
    },
    NO() {
        @Override
        public int getCode() {
            return 0;
        }

        @Override
        public String getMessage() {
            return "无效";
        }
    };
}
