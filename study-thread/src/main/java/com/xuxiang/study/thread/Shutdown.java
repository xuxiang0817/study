package com.xuxiang.study.thread;

import java.util.concurrent.TimeUnit;

/**
 * Shutdown
 *
 * @author xuxiang
 * @since 16/5/8
 */
public class Shutdown {
    public static void main(String[] args) throws Exception{
        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();

        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt(); //终止one线程

        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();

        TimeUnit.SECONDS.sleep(1);
        two.cancel();//终止two线程
    }

    private static class Runner implements Runnable{
        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && Thread.currentThread().isInterrupted())
                i++;

            System.out.println("Count i = " + i);
        }

        private void cancel(){
            on = false;
        }
    }
}
