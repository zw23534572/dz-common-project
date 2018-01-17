package com.dazong.common.web.support.monitor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

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
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] infos = bean.dumpAllThreads(true, true);

        StringBuffer sb = new StringBuffer(102400);
        for (ThreadInfo info : infos) {
            sb.append(info);
        }
        PrintWriter out = resp.getWriter();
        out.append(sb);

        out.flush();
        out.close();
    }
}
