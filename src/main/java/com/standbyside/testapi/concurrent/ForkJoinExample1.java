package com.standbyside.testapi.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExample1 {

    public static void main(String[] args) {
        // 创建线程池
        ForkJoinPool pool = new ForkJoinPool(4);
        // 创建任务
        Fibonacci fib = new Fibonacci(30);
        // 启动任务
        Integer result = pool.invoke(fib);
        // 输出结果
        System.out.println(result);
    }

    private static class Fibonacci extends RecursiveTask<Integer> {

        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            // 创建子任务
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            // 等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }
    }
}
