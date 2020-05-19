package com.standbyside.testapi.concurrent;

import com.google.common.base.Stopwatch;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTaskExamples {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // 计时器
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        // 线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();

        try {
            // 执行任务1
            stopwatch.start();
            test1(pool);
            stopwatch.stop();
            System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));

            // 执行任务2
            stopwatch.start();
            test2(pool);
            stopwatch.stop();
            System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        } finally {
            pool.shutdown();
        }
    }

    /**
     * 不设置等待时间
     */
    public static void test1(ExecutorService pool) throws InterruptedException, ExecutionException {
        // 定义任务
        Callable<String> callable = () -> {
            Thread.sleep(2000);
            return "finished";
        };
        // 将任务包装成FutureTask对象
        FutureTask<String> futureTask = new FutureTask(callable);
        // 任务提交到线程池
        pool.submit(futureTask);
        // 获取任务执行结果
        System.out.println(futureTask.get());
    }

    /**
     * 设置等待时间，等待超时
     */
    public static void test2(ExecutorService pool) throws InterruptedException, ExecutionException, TimeoutException {
        // 定义任务
        Callable<String> callable = () -> {
            Thread.sleep(2000);
            return "finished";
        };
        // 将任务包装成FutureTask对象
        FutureTask<String> futureTask = new FutureTask(callable);
        // 任务提交到线程池
        pool.submit(futureTask);
        // 获取任务执行结果
        System.out.println(futureTask.get(1, TimeUnit.SECONDS));
    }
}
