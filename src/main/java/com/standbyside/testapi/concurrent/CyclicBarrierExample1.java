package com.standbyside.testapi.concurrent;

import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CyclicBarrierExample1<P, D> {

    /**
     * 订单队列.
     */
    Vector<P> pos;
    /**
     * 派送单队列.
     */
    Vector<D> dos;
    /**
     * 执行回调的线程池.
     */
    Executor executor = Executors.newFixedThreadPool(1);

    final CyclicBarrier barrier = new CyclicBarrier(2, () -> executor.execute(() -> check()));


    void check() {
        P p = pos.remove(0);
        D d = dos.remove(0);
        // 执行对账操作
        Object diff = check(p, d);
        // 差异写入差异库
        save(diff);
    }


    void checkAll() {
        // 循环查询订单库
        Thread T1 = new Thread(() -> {
            while (hasOrder()) {
                // 查询订单库
                pos.add(getPOrders());
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        T1.start();
        // 循环查询运单库
        Thread T2 = new Thread(() -> {
            while (hasOrder()) {
                // 查询运单库
                dos.add(getDOrders());
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        T2.start();
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
    P getPOrders() {
        return null;
    }

    /**
     * 查询查询派送单.
     */
    D getDOrders() {
        return null;
    }

    /**
     * 对账.
     */
    Object check(P pos, D dos) {
        return null;
    }

    /**
     * 差异写入差异库.
     */
    Object save(Object diff) {
        return null;
    }
}
