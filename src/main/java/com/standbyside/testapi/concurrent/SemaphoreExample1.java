package com.standbyside.testapi.concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreExample1 {

    static int count;

    /**
     * 初始化信号量.
     */
    static final Semaphore SEMAPHORE = new Semaphore(1);

    /**
     * 用信号量保证互斥.
     */
    static void addOne() throws InterruptedException {
        SEMAPHORE.acquire();
        try {
            count += 1;
        } finally {
            SEMAPHORE.release();
        }
    }
}
