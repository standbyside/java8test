package com.standbyside.testapi.api;

import com.google.common.collect.Lists;

import java.util.List;

public class CollectionOperationApiExamples {

    public static void main(String[] args) {
        // 移除
        List<String> list = getList();
        list.removeIf(o -> "苹果".equals(o));
        System.out.println(list);
    }

    private static List<String> getList() {
        return Lists.newArrayList("苹果", "香蕉", "西瓜");
    }
}
