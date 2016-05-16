package com.xuxiang.study.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConditionUseCase
 *
 * @author xuxiang
 * @since 16/5/15
 */
public class ConditionUseCase {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void conditionWait() throws InterruptedException{
        lock.lock();
        try {
            condition.await();
        }finally {
            lock.unlock();
        }
    }

    public void conditionSignal() throws InterruptedException{
        lock.lock();
        try {
            condition.signal();
        }finally {
            lock.unlock();
        }
    }
}

