package com.standbyside.testapi.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CountDownLatchExample1 {

    public static void main(String[] args) throws InterruptedException {
        // 创建 2 个线程的线程池
        Executor executor = Executors.newFixedThreadPool(2);

        while (hasOrder()) {

            final Object[] pos = new Object[1];
            final Object[] dos = new Object[1];

            // 计数器初始化为 2
            CountDownLatch latch = new CountDownLatch(2);
            // 查询未对账订单
            executor.execute(() -> {
                pos[0] = getPOrders();
                latch.countDown();
            });
            // 查询派送单
            executor.execute(() -> {
                dos[0] = getDOrders();
                latch.countDown();
            });
            // 等待两个查询操作结束
            latch.await();
            // 执行对账操作
            Object diff = check(pos[0], dos[0]);
            // 差异写入差异库
            save(diff);
        }
    }

    /**
     * 是否存在未对账账单.
     */
    static boolean hasOrder() {
        return true;
    }

    /**
     * 查询未对账订单.
     */
    static Object getPOrders() {
        return null;
    }

    /**
     * 查询查询派送单.
     */
    static Object getDOrders() {
        return null;
    }

    /**
     * 对账.
     */
    static Object check(Object pos, Object dos) {
        return null;
    }

    /**
     * 差异写入差异库.
     */
    static Object save(Object diff) {
        return null;
    }
}
