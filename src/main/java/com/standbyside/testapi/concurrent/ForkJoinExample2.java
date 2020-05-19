package com.standbyside.testapi.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


/**
 * 使用 ForkJoin 模拟 MapReduce 统计单词数量
 */
public class ForkJoinExample2 {

    public static void main(String[] args) {
        String[] words = {"hello world", "hello me", "hello fork", "hello join", "fork join in world"};
        // 创建线程池
        ForkJoinPool pool = new ForkJoinPool(3);
        // 创建任务
        WordCount wordCount = new WordCount(words, 0, words.length);
        // 启动任务
        Map<String, Long> result = pool.invoke(wordCount);
        // 输出结果
        System.out.println(result);
    }

    /**
     * 统计单词递归任务
     */
    static class WordCount extends RecursiveTask<Map<String, Long>> {

        private String[] words;
        private int start;
        private int end;

        WordCount(String[] words, int from, int to) {
            this.words = words;
            this.start = from;
            this.end = to;
        }

        @Override
        protected Map<String, Long> compute() {
            if (end - start == 1) {
                return calculate(words[start]);
            } else {
                int mid = (start + end) / 2;
                WordCount wordCount1 = new WordCount(words, start, mid);
                wordCount1.fork();
                WordCount wordCount2 = new WordCount(words, mid, end);
                // 计算子任务，并返回合并的结果
                return merge(wordCount2.compute(), wordCount1.join());
            }
        }

        /**
         * 合并结果.
         */
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

        /**
         * 统计单词数量.
         */
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
