package com.xuxiang.study.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LockUseCase
 * 特性
 * 1.尝试非阻塞地获取锁
 * 2.能被中断地获取锁
 * 3.超时获取锁
 * @author xuxiang
 * @since 16/5/15
 */
public class LockUseCase {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            //TODO do something
        }finally {
            lock.unlock();
        }
    }
}
