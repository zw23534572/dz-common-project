package com.dazong.common.web.support.monitor;

import org.junit.Test;

/**
 * 获取jvm线程堆栈
 *
 * @author huqichao
 * @date 2018-01-17 09:30
 **/
public class ThreadDumpServletTest {

    @Test
    public void getThreadDumpTest() {
        ServerInfo serverInfo = new ServerInfo();
        ThreadDumpServlet threadDumpServlet = new ThreadDumpServlet();
        threadDumpServlet.getThreadDump(serverInfo);
        System.out.println("serverInfo:" + serverInfo);
    }

    @Test
    public void getGCTest() {
        ServerInfo serverInfo = new ServerInfo();
        ThreadDumpServlet threadDumpServlet = new ThreadDumpServlet();
        threadDumpServlet.getGC(serverInfo);
        System.out.println("serverInfo:" + serverInfo);
    }

    @Test
    public void getMemoryTest() {
        ServerInfo serverInfo = new ServerInfo();
        ThreadDumpServlet threadDumpServlet = new ThreadDumpServlet();
        threadDumpServlet.getGC(serverInfo);
        System.out.println("serverInfo:" + serverInfo);
    }

    @Test
    public void getThreadTest() {
        ServerInfo serverInfo = new ServerInfo();
        ThreadDumpServlet threadDumpServlet = new ThreadDumpServlet();
        threadDumpServlet.getThread(serverInfo);
        System.out.println("serverInfo:" + serverInfo);
    }

    @Test
    public void getGitRepositoryStateTest() {
        ServerInfo serverInfo = new ServerInfo();
        ThreadDumpServlet threadDumpServlet = new ThreadDumpServlet();
        threadDumpServlet.getGitRepositoryState(serverInfo);
        System.out.println("serverInfo:" + serverInfo);
    }

}
