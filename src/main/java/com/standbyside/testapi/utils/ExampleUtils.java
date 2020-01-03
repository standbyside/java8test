package com.standbyside.testapi.utils;

import java.util.concurrent.TimeUnit;

public class ExampleUtils {

    public static void sleepAndPrint(int second, String msg) {
        try {
            TimeUnit.SECONDS.sleep(second);
            System.out.println(msg);
        } catch (Exception e) {

        }
    }

    public static <T> T sleepAndReturn(int second, T t) {
        try {
            TimeUnit.SECONDS.sleep(second);
            return t;
        } catch (Exception e) {
            return null;
        }
    }
}
