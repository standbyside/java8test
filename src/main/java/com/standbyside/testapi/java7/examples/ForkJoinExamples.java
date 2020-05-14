package com.standbyside.testapi.java7.examples;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExamples {

    public static void main(String[] args) {
        fibonacci();
        wordCount();
    }

    /**
     * 使用 ForkJoin 计算斐波那契数
     */
    public static void fibonacci() {
        // 创建分治任务线程池
        ForkJoinPool pool = new ForkJoinPool(4);
        // 创建分治任务
        Fibonacci fib = new Fibonacci(30);
        // 启动分治任务
        Integer result = pool.invoke(fib);
        // 输出结果
        System.out.println(result);
    }

    /**
     * 使用 ForkJoin 模拟 MapReduce 统计单词数量
     */
    public static void wordCount() {
        String[] fc = {"hello world", "hello me", "hello fork", "hello join", "fork join in world"};
        // 创建 ForkJoin 线程池
        ForkJoinPool fjp = new ForkJoinPool(3);
        // 创建任务
        WorkCount mr = new WorkCount(fc, 0, fc.length);
        // 启动任务
        Map<String, Long> result = fjp.invoke(mr);
        // 输出结果
        result.forEach((k, v)-> System.out.println(k+":"+v));
    }

    /**
     * 斐波那契递归任务
     */
    static class Fibonacci extends RecursiveTask<Integer> {

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

    /**
     * 统计单词递归任务
     */
    static class WorkCount extends RecursiveTask<Map<String, Long>> {

        private String[] fc;

        private int start, end;

        public WorkCount(String[] fc, int fr, int to) {
            this.fc = fc;
            this.start = fr;
            this.end = to;
        }

        @Override
        protected Map<String, Long> compute() {
            if (end - start == 1) {
                return calculate(fc[start]);
            } else {
                int mid = (start + end) / 2;
                WorkCount mr1 = new WorkCount(fc, start, mid);
                mr1.fork();
                WorkCount mr2 = new WorkCount(fc, mid, end);
                // 计算子任务，并返回合并的结果
                return merge(mr2.compute(), mr1.join());
            }
        }

        // 合并结果
        private Map<String, Long> merge(Map<String, Long> r1, Map<String, Long> r2) {
            Map<String, Long> result = new HashMap<>(16);
            result.putAll(r1);
            // 合并结果
            r2.forEach((k, v) -> {
                Long c = result.get(k);
                if (c != null) {
                    result.put(k, c + v);
                } else {
                    result.put(k, v);
                }
            });
            return result;
        }

        // 统计单词数量
        private Map<String, Long> calculate(String line) {
            Map<String, Long> result = new HashMap<>(16);
            // 分割单词
            String[] words = line.split("\\s+");
            // 统计单词数量
            for (String w : words) {
                Long v = result.get(w);
                if (v != null) {
                    result.put(w, v + 1);
                } else {
                    result.put(w, 1L);
                }
            }
            return result;
        }
    }
}
