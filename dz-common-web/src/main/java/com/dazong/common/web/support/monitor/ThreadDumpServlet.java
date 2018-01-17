package com.dazong.common.web.support.monitor;

import com.alibaba.fastjson.JSON;

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

/**
 * 获取jvm线程堆栈
 * @author huqichao
 * @date 2018-01-17 09:30
 **/
@WebServlet(name="ThreadDump",urlPatterns="/threadDump")
public class ThreadDumpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        ServerInfo serverInfo = new ServerInfo();

        //获取内存信息
        if ("1".equals(type) || "all".equals(type)) {
            serverInfo.setJvmArgs(ManagementFactory.getRuntimeMXBean().getInputArguments().toString());
            getMemory(serverInfo);
        }
        //获取线程信息
        if ("2".equals(type) || "all".equals(type)) {
            getThread(serverInfo);
        }
        //获取threadDump信息
        if ("3".equals(type) || "all".equals(type)){
            getThreadDump(serverInfo);
        }

        //获取gc信息
        if ("4".equals(type) || "all".equals(type)){
            getGC(serverInfo);
        }

        PrintWriter out = resp.getWriter();
        out.append(JSON.toJSONString(serverInfo));

        out.flush();
        out.close();
    }

    private void getThreadDump(ServerInfo serverInfo){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] infos = bean.dumpAllThreads(true, true);

        StringBuilder dumpStr = new StringBuilder(102400);
        for (ThreadInfo info : infos) {
            dumpStr.append(info);
        }

        serverInfo.setThreadDump(dumpStr.toString());
    }

    private void getGC(ServerInfo serverInfo){
        List<GarbageCollectorMXBean> gcList = ManagementFactory.getGarbageCollectorMXBeans();
        List<GCInfo> gcInfos = new ArrayList<>(gcList.size());
        for (GarbageCollectorMXBean gc : gcList){
            gcInfos.add(new GCInfo(gc.getName(), gc.getCollectionCount(), gc.getCollectionTime()));
        }

        serverInfo.setGcInfoList(gcInfos);
    }

    private void getMemory(ServerInfo serverInfo){
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

    private void getThread(ServerInfo serverInfo){
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        serverInfo.setThreadCount(threadMXBean.getThreadCount());
        serverInfo.setDaemonThreadCount(threadMXBean.getDaemonThreadCount());
        serverInfo.setPeakThreadCount(threadMXBean.getPeakThreadCount());
        serverInfo.setTotalStartedThreadCount(threadMXBean.getTotalStartedThreadCount());
    }
}
