package com.standbyside.testapi.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample2 {

    private final Lock lock = new ReentrantLock();
    private int balance;

    /**
     * 转账.
     */
    void transfer(ReentrantLockExample2 tar, int amt) throws InterruptedException {
        while (true) {
            if (this.lock.tryLock()) {
                try {
                    if (tar.lock.tryLock()) {
                        try {
                            this.balance -= amt;
                            tar.balance += amt;
                            break;
                        } finally {
                            tar.lock.unlock();
                        }
                    }
                } finally {
                    this.lock.unlock();
                }
            }
            // sleep 一个随机时间避免活锁
            Thread.sleep(new Random().nextInt(20) + 1L);
        }
    }
}
