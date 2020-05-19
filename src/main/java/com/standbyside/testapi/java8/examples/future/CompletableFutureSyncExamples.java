package com.standbyside.testapi.java8.examples.future;

import com.standbyside.testapi.util.ExampleUtils;

import java.util.concurrent.CompletableFuture;

/**
 * 串行关系.
 *
 * 来源 https://juejin.im/post/5e0d5f4ef265da5d0e322759
 */
public class CompletableFutureSyncExamples {

    public static void main(String[] args) throws Exception {
        thenApply();
        thenRun();
        thenAccept();
        thenCompose();
    }

    /**
     * 最简单也是最常用的一个方法，CompletableFuture的链式调用.
     */
    public static void thenApply() throws Exception {
        System.out.println("------------------then apply------------------");
        CompletableFuture<String> future = CompletableFuture
                .runAsync(() -> ExampleUtils.sleepAndPrint(1, "hello"))
                .thenApply(s1 -> s1 + "big ")
                .thenApply(s2 -> s2 + "world ");
        future.join();
        System.out.println(future.get());
    }

    /**
     * 计算完成时候执行一个Runnable，不使用CompletableFuture计算结果，
     * 此前的CompletableFuture计算结果也会被忽略，返回CompletableFuture类型。
     */
    public static void thenRun() {
        System.out.println("------------------then run------------------");
        CompletableFuture future = CompletableFuture
                .runAsync(() -> ExampleUtils.sleepAndPrint(1, "hello"))
                .thenRun(() -> System.out.println("big world"));
        future.join();
    }

    /**
     * thenApply、thenAccept类似，区别在于thenAccept纯消费，无返回值，支持链式调用。
     */
    public static void thenAccept() {
        System.out.println("------------------then accept------------------");
        CompletableFuture future = CompletableFuture
                .supplyAsync(() -> {
                    ExampleUtils.sleepAndPrint(1, "hello");
                    return "big world";
                })
                .thenAccept(s -> System.out.println(s));
        future.join();
    }

    /**
     * 都是接受一个Function，输入是当前CompletableFuture计算值，返回一个新CompletableFuture。
     * 即这个新的CompletableFuture组合原来的CompletableFuture和函数返回的CompletableFuture。
     * 通常使用在第二个CompletableFuture需要使用第一个CompletableFuture结果作为输入情况下。
     */
    public static void thenCompose() throws Exception {
        System.out.println("------------------then compose------------------");
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(() -> 1)
                .thenCompose(x -> CompletableFuture.supplyAsync(() -> x + 1));
        future.join();
        System.out.println(future.get());
    }
}
