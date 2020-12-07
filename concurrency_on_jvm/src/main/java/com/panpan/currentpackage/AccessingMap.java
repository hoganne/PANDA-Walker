package com.panpan.currentpackage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/12/7
 **/
public class AccessingMap {
    private static void useMap(final Map<String, Integer> scores)
            throws InterruptedException {
        scores.put("Fred", 10);
        scores.put("Sara", 12);
        try {
            for (final String key : scores.keySet()) {
                System.out.println(key + " score " + scores.get(key));
                scores.put("Joe", 14);
            }
        } catch (Exception ex) {
            System.out.println("Failed: " + ex);
        }
        System.out.println("Number of elements in the map: " +
                scores.keySet().size());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Using Plain vanilla HashMap");
        useMap(new HashMap<String, Integer>());
        System.out.println("Using Synchronized HashMap");
        useMap(Collections.synchronizedMap(new HashMap<String, Integer>()));
        System.out.println("Using Concurrent HashMap");
        useMap(new ConcurrentHashMap<String, Integer>());
    }
}
