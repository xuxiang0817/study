package com.xuxiang.study.thread;

/**
 * MyRunnable
 *
 * @author xuxiang
 * @since 2018/8/6
 */
public abstract class MyRunnable implements Runnable{

    public void run() {
        runBiz();
    }

    public abstract void runBiz() ;
}
