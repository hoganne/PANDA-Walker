package com.panpan.datastructure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Description
 * @Author xupan
 * @Date2021/2/3 14:02
 * @Version V1.0
 **/
public class Solution {
    public static void main(String[] args) {
        PriorityQueue<Integer> integers = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        integers.add(2);
        integers.add(1);
        integers.add(4);
        integers.add(25);
        integers.add(5);
        while (!integers.isEmpty()){
            System.out.println(integers.poll());
        }
    }

}
