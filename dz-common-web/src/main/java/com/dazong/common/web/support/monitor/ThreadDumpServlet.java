package com.dazong.common.web.support.monitor;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 获取jvm线程堆栈
 *
 * @author huqichao
 * @date 2018-01-17 09:30
 **/
@WebServlet(name = "ThreadDump", urlPatterns = "/threadDump")
public class ThreadDumpServlet extends HttpServlet {


    private static final Logger logger = LoggerFactory.getLogger(ThreadDumpServlet.class);

    private static final String MEMORY_CARD = "1";
    private static final String THEAD_INFO = "2";
    private static final String THREAD_DUMP = "3";
    private static final String GC_INFO = "4";
    private static final String GIT_REPOSITORY_STATE = "5";
    private static final String ALL_TYPE = "all";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        ServerInfo serverInfo = new ServerInfo();

        //获取内存信息
        if (type.contains(MEMORY_CARD) || ALL_TYPE.equals(type)) {
            serverInfo.setJvmArgs(ManagementFactory.getRuntimeMXBean().getInputArguments().toString());
            getMemory(serverInfo);
        }
        //获取线程信息
        if (type.contains(THEAD_INFO) || ALL_TYPE.equals(type)) {
            getThread(serverInfo);
        }
        //获取threadDump信息
        if (type.contains(THREAD_DUMP) || ALL_TYPE.equals(type)) {
            getThreadDump(serverInfo);
        }
        //获取gc信息
        if (type.contains(GC_INFO) || ALL_TYPE.equals(type)) {
            getGC(serverInfo);
        }
        //获取gc信息
        if (type.contains(GIT_REPOSITORY_STATE) || "all".equals(type)) {
            getGitRepositoryState(serverInfo);
        }

        PrintWriter out = resp.getWriter();
        out.append(JSON.toJSONString(serverInfo));

        out.flush();
        out.close();
    }

    private void getThreadDump(ServerInfo serverInfo) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] infos = bean.dumpAllThreads(true, true);

        StringBuilder dumpStr = new StringBuilder(102400);
        for (ThreadInfo info : infos) {
            dumpStr.append(info);
        }

        serverInfo.setThreadDump(dumpStr.toString());
    }

    private void getGC(ServerInfo serverInfo) {
        List<GarbageCollectorMXBean> gcList = ManagementFactory.getGarbageCollectorMXBeans();
        List<GCInfo> gcInfos = new ArrayList<>(gcList.size());
        for (GarbageCollectorMXBean gc : gcList) {
            gcInfos.add(new GCInfo(gc.getName(), gc.getCollectionCount(), gc.getCollectionTime()));
        }

        serverInfo.setGcInfoList(gcInfos);
    }

    private void getMemory(ServerInfo serverInfo) {
        MemoryMXBean memoryMBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage headUsage = memoryMBean.getHeapMemoryUsage();
        serverInfo.setHeadJvmInit(headUsage.getInit());
        serverInfo.setHeadJvmUsed(headUsage.getUsed());
        serverInfo.setHeadJvmCommitted(headUsage.getCommitted());
        serverInfo.setHeadJvmMax(headUsage.getMax());

        MemoryUsage nonHeadUsage = memoryMBean.getNonHeapMemoryUsage();
        serverInfo.setNonHeadJvmInit(nonHeadUsage.getInit());
        serverInfo.setNonHeadJvmUsed(nonHeadUsage.getUsed());
        serverInfo.setNonHeadJvmCommitted(nonHeadUsage.getCommitted());
        serverInfo.setNonHeadJvmMax(nonHeadUsage.getMax());
    }

    private void getThread(ServerInfo serverInfo) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        serverInfo.setThreadCount(threadMXBean.getThreadCount());
        serverInfo.setDaemonThreadCount(threadMXBean.getDaemonThreadCount());
        serverInfo.setPeakThreadCount(threadMXBean.getPeakThreadCount());
        serverInfo.setTotalStartedThreadCount(threadMXBean.getTotalStartedThreadCount());
    }


    private void getGitRepositoryState(ServerInfo serverInfo) {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("git.properties"));
            serverInfo.setBranch(properties.getProperty("git.branch"));
            serverInfo.setCommitId(properties.getProperty("git.commit.id"));
            serverInfo.setCommitTime(properties.getProperty("git.commit.time"));
            serverInfo.setBuildTime(properties.getProperty("git.build.time"));
            serverInfo.setBuildVersion(properties.getProperty("git.build.version"));
            serverInfo.setCommitUserName(properties.getProperty("git.commit.user.name"));
            serverInfo.setCommitMessageFull(properties.getProperty("git.commit.message.full"));
        } catch (Exception e) {
            logger.warn("this application does not configure the Maven plugin: git-commit-id-plugin");
        }
    }
}
