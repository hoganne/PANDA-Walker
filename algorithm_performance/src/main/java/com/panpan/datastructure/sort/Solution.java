package com.panpan.datastructure.sort;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * @Description
 * @Author xupan
 * @Date2021/2/4 15:09
 * @Version V1.0
 **/
public class Solution {
    public static Solution instance =  new Solution();
    public static void main(String[] args) {
        int[] ints = instance.bubbleSort(new int[]{1,0});
        int[] ints1 = {72, 6, 57, 88, 60, 42, 83, 73, 48, 85};
        instance.quickSort(ints1);
        Arrays.stream(ints).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        });
    }
    /**bubble sort*/
    public int[] bubbleSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j <arr.length-1-i ; j++) {
                if(arr[j]>arr[j+1]){
                    arr[j]=arr[j]^arr[j+1];
                    arr[j+1]=arr[j]^arr[j+1];
                    arr[j]=arr[j]^arr[j+1];
                }
            }
        }
        return arr;
    }
    /**Quick sort*/
    public int[] quickSort(int[] arr){
//        int i=0,j=arr.length-1,tmp =arr[i];
//        boolean from_front_to_back = false;
//        while (i!=j){
//            if(from_front_to_back){
//                if(tmp<arr[i]){
//                    arr[j]=arr[i];
//                    from_front_to_back =false;
//                    continue;
//                }
//                i++;
//            }else{
//                if(tmp>arr[j]){
//                    arr[i]=arr[j];
//                    from_front_to_back=true;
//                    continue;
//                }
//                j--;
//            }
//        }
//        arr[j]=tmp;
//
        helper(arr,0,arr.length-1);
        return arr;
    }
    public void helper(int[] arr,int start,int end){
        int i=start,j=end,tmp =arr[i];
        boolean from_front_to_back = false;
        while (i!=j){
            if(from_front_to_back){
                if(tmp<arr[i]){
                    arr[j]=arr[i];
                    from_front_to_back =false;
                    continue;
                }
                i++;
            }else{
                if(tmp>arr[j]){
                    arr[i]=arr[j];
                    from_front_to_back=true;
                    continue;
                }
                j--;
            }
        }
        arr[j]=tmp;
        if(start<j-1){
            helper(arr,start,j-1);
        }
        if(end>j+1){
            helper(arr,j+1,end);
        }
    }
}
