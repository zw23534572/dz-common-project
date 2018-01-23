package com.dazong.common.web.support.monitor;

import java.util.List;

/**
 * @author huqichao
 * @date 2018-01-17 14:24
 **/
public class ServerInfo {

    private String jvmArgs;

    private long headJvmInit;

    private long headJvmMax;

    private long headJvmUsed;

    private long headJvmCommitted;

    private long nonHeadJvmInit;

    private long nonHeadJvmMax;

    private long nonHeadJvmUsed;

    private long nonHeadJvmCommitted;

    private int threadCount;

    private int daemonThreadCount;

    private int peakThreadCount;

    private long totalStartedThreadCount;

    private String threadDump;

    private List<GCInfo> gcInfoList;

    private String branch;

    private String commitId;

    private String buildVersion;

    private String commitTime;

    private String buildTime;

    private String commitUserName;

    private String commitMessageFull;

    public String getJvmArgs() {
        return jvmArgs;
    }

    public void setJvmArgs(String jvmArgs) {
        this.jvmArgs = jvmArgs;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getDaemonThreadCount() {
        return daemonThreadCount;
    }

    public void setDaemonThreadCount(int daemonThreadCount) {
        this.daemonThreadCount = daemonThreadCount;
    }

    public int getPeakThreadCount() {
        return peakThreadCount;
    }

    public void setPeakThreadCount(int peakThreadCount) {
        this.peakThreadCount = peakThreadCount;
    }

    public long getTotalStartedThreadCount() {
        return totalStartedThreadCount;
    }

    public void setTotalStartedThreadCount(long totalStartedThreadCount) {
        this.totalStartedThreadCount = totalStartedThreadCount;
    }

    public String getThreadDump() {
        return threadDump;
    }

    public void setThreadDump(String threadDump) {
        this.threadDump = threadDump;
    }

    public long getHeadJvmInit() {
        return headJvmInit;
    }

    public void setHeadJvmInit(long headJvmInit) {
        this.headJvmInit = headJvmInit;
    }

    public long getHeadJvmMax() {
        return headJvmMax;
    }

    public void setHeadJvmMax(long headJvmMax) {
        this.headJvmMax = headJvmMax;
    }

    public long getHeadJvmUsed() {
        return headJvmUsed;
    }

    public void setHeadJvmUsed(long headJvmUsed) {
        this.headJvmUsed = headJvmUsed;
    }

    public long getHeadJvmCommitted() {
        return headJvmCommitted;
    }

    public void setHeadJvmCommitted(long headJvmCommitted) {
        this.headJvmCommitted = headJvmCommitted;
    }

    public long getNonHeadJvmInit() {
        return nonHeadJvmInit;
    }

    public void setNonHeadJvmInit(long nonHeadJvmInit) {
        this.nonHeadJvmInit = nonHeadJvmInit;
    }

    public long getNonHeadJvmMax() {
        return nonHeadJvmMax;
    }

    public void setNonHeadJvmMax(long nonHeadJvmMax) {
        this.nonHeadJvmMax = nonHeadJvmMax;
    }

    public long getNonHeadJvmUsed() {
        return nonHeadJvmUsed;
    }

    public void setNonHeadJvmUsed(long nonHeadJvmUsed) {
        this.nonHeadJvmUsed = nonHeadJvmUsed;
    }

    public long getNonHeadJvmCommitted() {
        return nonHeadJvmCommitted;
    }

    public void setNonHeadJvmCommitted(long nonHeadJvmCommitted) {
        this.nonHeadJvmCommitted = nonHeadJvmCommitted;
    }

    public List<GCInfo> getGcInfoList() {
        return gcInfoList;
    }

    public void setGcInfoList(List<GCInfo> gcInfoList) {
        this.gcInfoList = gcInfoList;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getCommitUserName() {
        return commitUserName;
    }

    public void setCommitUserName(String commitUserName) {
        this.commitUserName = commitUserName;
    }

    public String getCommitMessageFull() {
        return commitMessageFull;
    }

    public void setCommitMessageFull(String commitMessageFull) {
        this.commitMessageFull = commitMessageFull;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "jvmArgs='" + jvmArgs + '\'' +
                ", headJvmInit=" + headJvmInit +
                ", headJvmMax=" + headJvmMax +
                ", headJvmUsed=" + headJvmUsed +
                ", headJvmCommitted=" + headJvmCommitted +
                ", nonHeadJvmInit=" + nonHeadJvmInit +
                ", nonHeadJvmMax=" + nonHeadJvmMax +
                ", nonHeadJvmUsed=" + nonHeadJvmUsed +
                ", nonHeadJvmCommitted=" + nonHeadJvmCommitted +
                ", threadCount=" + threadCount +
                ", daemonThreadCount=" + daemonThreadCount +
                ", peakThreadCount=" + peakThreadCount +
                ", totalStartedThreadCount=" + totalStartedThreadCount +
                ", threadDump='" + threadDump + '\'' +
                ", gcInfoList=" + gcInfoList +
                ", branch='" + branch + '\'' +
                ", commitId='" + commitId + '\'' +
                ", buildVersion='" + buildVersion + '\'' +
                ", commitTime='" + commitTime + '\'' +
                ", buildTime='" + buildTime + '\'' +
                ", commitUserName='" + commitUserName + '\'' +
                ", commitMessageFull='" + commitMessageFull + '\'' +
                '}';
    }
}
