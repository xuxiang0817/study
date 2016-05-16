package com.xuxiang.study.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * MultiThread
 *
 * @author xuxiang
 * @since 16/5/8
 */
public class MultiThread {
    public static void main(String[] args) {
        //获取线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        //获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        for(ThreadInfo info : threadInfos){
            System.out.println("[" + info.getThreadId() + "]" + info.getThreadName());
        }
    }
}
