package com.dazong.common.monitor;

/**
 * Created by huqichao on 17/5/25.
 */
public class CheckResult {

    private String name;

    private boolean success;

    private int status;

    private String errorMsg;

    private long cost;

    public CheckResult(String name) {
        this.name = name;
        this.success = true;
        this.status = Monitor.SUCCESS;
        this.errorMsg = "";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "CheckResult{" +
                "name='" + name + '\'' +
                ", success=" + success +
                ", status=" + status +
                ", errorMsg='" + errorMsg + '\'' +
                ", cost=" + cost +
                '}';
    }
}
