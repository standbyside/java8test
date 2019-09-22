package com.standbyside.testapi.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample1 {

    private final Lock lock = new ReentrantLock();

    int value;

    public int get() {
        // 获取锁
        lock.lock();
        try {
            return value;
        } finally {
            // 保证锁能释放
            lock.unlock();
        }
    }

    public void addOne() {
        // 获取锁
        lock.lock();
        try {
            value = 1 + get();
        } finally {
            // 保证锁能释放
            lock.unlock();
        }
    }
}
