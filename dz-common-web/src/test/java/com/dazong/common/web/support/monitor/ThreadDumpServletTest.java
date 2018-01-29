package com.dazong.common.web.support.monitor;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 获取jvm线程堆栈
 *
 * @author huqichao
 * @date 2018-01-17 09:30
 **/
public class ThreadDumpServletTest {

    private static final String MEMORY_CARD = "1";
    private static final String THEAD_INFO = "2";
    private static final String THREAD_DUMP = "3";
    private static final String GC_INFO = "4";
    private static final String GIT_REPOSITORY_STATE = "5";

    public HttpServletRequest getRequest(String type) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("type", type);
        return request;
    }

    @Test
    public void getThreadDumpTest() throws ServletException, IOException {
        ThreadDumpServlet threadDumpServlet = new ThreadDumpServlet();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        threadDumpServlet.doGet(getRequest(THREAD_DUMP), httpServletResponse);
    }

    @Test
    public void getGCTest() throws ServletException, IOException {
        ThreadDumpServlet threadDumpServlet = new ThreadDumpServlet();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        threadDumpServlet.doGet(getRequest(GC_INFO), httpServletResponse);
    }

    @Test
    public void getMemoryTest() throws ServletException, IOException {
        ThreadDumpServlet threadDumpServlet = new ThreadDumpServlet();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        threadDumpServlet.doGet(getRequest(MEMORY_CARD), httpServletResponse);
    }

    @Test
    public void getThreadTest() throws ServletException, IOException {
        ThreadDumpServlet threadDumpServlet = new ThreadDumpServlet();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        threadDumpServlet.doGet(getRequest(THEAD_INFO), httpServletResponse);
    }

    @Test
    public void getGitRepositoryStateTest() throws ServletException, IOException {
        ThreadDumpServlet threadDumpServlet = new ThreadDumpServlet();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        threadDumpServlet.doGet(getRequest(GIT_REPOSITORY_STATE), httpServletResponse);
    }

}
