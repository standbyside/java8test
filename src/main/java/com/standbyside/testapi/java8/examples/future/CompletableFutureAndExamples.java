package com.standbyside.testapi.java8.examples.future;

import com.standbyside.testapi.utils.ExampleUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * AND关系.
 *
 * 来源 https://juejin.im/post/5e0d5f4ef265da5d0e322759
 */
public class CompletableFutureAndExamples {

    public static void main(String[] args) throws Exception {
        thenCombine();
        thenAcceptBoth();
        runAfterBoth();
        allOf();
    }

    /**
     * thenCombine结合的2个CompletableFuture没有依赖关系，
     * 且第二个CompletableFuture不需要等第一个CompletableFuture执行完成才开始。
     * thenCombine组合的多个CompletableFuture虽然是独立的，但是整个流水线是同步的。
     */
    public static void thenCombine() throws ExecutionException, InterruptedException {
        System.out.println("------------------then combine------------------");

        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(1, "A"));
        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(2, "B"));

        CompletableFuture<String> future = future1.thenCombine(
                future2, (s1, s2) -> s1 + " + " + s2
        );

        future.join();
        System.out.println(future.get());
    }

    /**
     * 2个CompletableFuture处理完成后，将结果进行消费.
     * 与thenCombine类似，第2个CompletableFuture不依赖第1个CompletableFuture执行完成，
     * 返回值Void.
     */
    public static void thenAcceptBoth() {
        System.out.println("------------------then accept both------------------");

        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(1, "A"));
        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(2, "B"));

        CompletableFuture<Void> future = future1.thenAcceptBoth(
                future2, (s1, s2) -> System.out.println(s1 + " + " + s2)
        );

        future.join();
    }

    /**
     * 2个CompletableFuture都完成之后，会执行一个Runnable。
     * 这点与thenAcceptBoth、thenCombine都类似，
     * 但是不同点在于runAfterBoth毫不关心任意一个CompletableFuture的返回值，
     * 只要CompletableFuture都执行完成它就run，同样它也没有返回值.
     */
    public static void runAfterBoth() {
        System.out.println("------------------run after both------------------");

        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(1, "A"));
        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> ExampleUtils.sleepAndReturn(2, "B"));

        CompletableFuture<Void> future = future1.runAfterBoth(
                future2, () -> System.out.println("finished")
        );

        future.join();

    }

    /**
     * 只是单纯等待所有的CompletableFuture执行完成，返回一个 CompletableFuture.
     */
    public static void allOf() {
        System.out.println("------------------all of------------------");
        long start = System.currentTimeMillis();

        CompletableFuture<Void> future1 = CompletableFuture
                .runAsync(() -> ExampleUtils.sleepAndPrint(1, "i am sleep 1 "));
        CompletableFuture<Void> future2 = CompletableFuture
                .runAsync(() -> ExampleUtils.sleepAndPrint(2, "i am sleep 2 "));
        CompletableFuture<Void> future3 = CompletableFuture
                .runAsync(() -> ExampleUtils.sleepAndPrint(3, "i am sleep 3 "));

        CompletableFuture future = CompletableFuture.allOf(
                future1, future2, future3
        );
        future.join();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
