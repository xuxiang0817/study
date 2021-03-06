package com.xuxiang.study.thread;

/**
 * ThreadPool
 *
 * @author xuxiang
 * @since 16/5/15
 */
public interface ThreadPool<Job extends Runnable> {

    void execute(Job job);

    void shutdown();

    void addWorkers(int num);

    void removeWorkers(int num);

    int getJobSize();
}
