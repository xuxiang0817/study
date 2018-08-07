package com.xuxiang.study.thread;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * MyThreadExecutor
 *
 * @author xuxiang
 * @since 18/8/2
 */
public class MyThreadExecutor {

    private static final int MIN_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private static final int MAX_SIZE = MIN_SIZE * 20;
    private static final int BLOCK_SIZE = MIN_SIZE * 10;

    private static BaseThreadPoolExecutor EXECUTOR =
            new BaseThreadPoolExecutor(MIN_SIZE, MAX_SIZE,
                    60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(BLOCK_SIZE),
                    new CustomizableThreadFactory("TTpodExecutor.EXE"));


    public static void execute(MyRunnable runnable) {
        EXECUTOR.execute(runnable);
    }

    public static class BaseThreadPoolExecutor extends ThreadPoolExecutor {

        private int poolSize;
        private int activeSize;

        public int getActiveSize() {
            return activeSize;
        }

        public int getPoolSize() {
            return poolSize;
        }

        public BaseThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
                                      long keepAliveTime, TimeUnit unit,
                                      BlockingQueue<Runnable> workQueue,
                                      ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        protected void afterExecute(Runnable r, Throwable t) {
            this.poolSize = this.getPoolSize();
            this.activeSize = this.getActiveCount();
        }
    }
}
