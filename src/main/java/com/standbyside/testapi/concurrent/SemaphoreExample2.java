package com.standbyside.testapi.concurrent;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 用信号量实现限流器.
 */
public class SemaphoreExample2 {

    public static void main(String[] args) throws InterruptedException {
        // 创建对象池
        ObjPool<Long, String> pool = new ObjPool<>(10, 2L);
        // 通过对象池获取 t，之后执行
        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }

    /**
     * 限流器.
     */
    static class ObjPool<T, R> {

        final List<T> pool;
        final Semaphore semaphore;

        ObjPool(int size, T t) {
            pool = new Vector<T>() {
            };
            for (int i = 0; i < size; i++) {
                pool.add(t);
            }
            semaphore = new Semaphore(size);
        }

        /**
         * 利用对象池的对象，调用 func.
         */
        R exec(Function<T, R> func) throws InterruptedException {
            T t = null;
            semaphore.acquire();
            try {
                t = pool.remove(0);
                return func.apply(t);
            } finally {
                pool.add(t);
                semaphore.release();
            }
        }
    }
}
