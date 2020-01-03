package com.standbyside.testapi.java8.examples.future;

import com.standbyside.testapi.utils.ExampleUtils;

import java.util.concurrent.CompletableFuture;

/**
 * OR关系.
 *
 * 来源 https://juejin.im/post/5e0d5f4ef265da5d0e322759
 */
public class CompletableFutureOrExamples {

    public static void main(String[] args) throws Exception {
        applyToEither();
        acceptEither();
        runAfterEither();
        anyOf();
    }

    /**
     * CompletableFuture最快的执行完成的结果作为下一个stage的输入结果.
     */
    public static void applyToEither() throws Exception {
        System.out.println("------------------apply to either------------------");

        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(1, "A"));
        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(2, "B"));

        CompletableFuture<String> future = future1.applyToEither(
                future2, s -> s + " 最快返回"
        );

        future.join();
        System.out.println(future.get());
    }

    /**
     * 最快CompletableFuture执行完成的时候，action消费就会得到执行.
     */
    public static void acceptEither() {
        System.out.println("------------------accept either------------------");

        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(1, "A"));
        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(2, "B"));

        CompletableFuture<Void> future = future1.acceptEither(
                future2, s -> System.out.println(s + " 最快返回")
        );

        future.join();
    }

    /**
     * 任何一个CompletableFuture完成之后，就会执行下一步的Runnable.
     */
    public static void runAfterEither() {
        System.out.println("------------------run after either------------------");
        long start = System.currentTimeMillis();

        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(1, "A"));
        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(2, "B"));

        CompletableFuture<Void> future = future1.runAfterEither(
                future2, () -> System.out.println("finished")
        );

        future.join();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * 任何一个CompletableFuture完成，anyOf函数就会返回.
     */
    public static void anyOf() {
        System.out.println("------------------any of------------------");
        long start = System.currentTimeMillis();

        CompletableFuture<Void> future1 = CompletableFuture
                .runAsync(() -> ExampleUtils.sleepAndPrint(1, "i am sleep 1 "));
        CompletableFuture<Void> future2 = CompletableFuture
                .runAsync(() -> ExampleUtils.sleepAndPrint(2, "i am sleep 2 "));
        CompletableFuture<Void> future3 = CompletableFuture
                .runAsync(() -> ExampleUtils.sleepAndPrint(3, "i am sleep 3 "));

        CompletableFuture future = CompletableFuture.anyOf(
                future1, future2, future3
        );

        future.join();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
