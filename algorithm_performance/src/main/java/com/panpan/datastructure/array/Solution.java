package com.panpan.datastructure.array;

import java.util.Arrays;

/**
 * @Description
 * @Author xupan
 * @Date2021/1/16 10:32
 * @Version V1.0
 **/
public class Solution {
    public static void main(String[] args) {
//        int[] arr1 = {1, 1, 1, 1, 1};
//        int[] arr2 = {1,0,1};
        int[] arr1 = {1,1};
        int[] arr2 = {1};
        new Solution().addNegabinary2(arr1,arr2);
    }

    //    给出基数为 -2 的两个数 arr1 和 arr2，返回两数相加的结果。
    //    数字以 数组形式 给出：数组由若干 0 和 1 组成，按最高有效位到最低有效位的顺序排列。
    //    例如，arr = [1,1,0,1] 表示数字 (-2)^3 + (-2)^2 + (-2)^0 = -3。
    //    数组形式的数字也同样不含前导零：以 arr 为例，这意味着要么 arr == [0]，要么 arr[0] == 1。
    //    返回相同表示形式的 arr1 和 arr2 相加的结果。两数的表示形式为：不含前导零、由若干 0 和 1 组成的数组。

    public int[] addNegabinary(int[] arr1, int[] arr2) {
        int count = arr1.length > arr2.length ? arr1.length: arr2.length;
        int[] result = new int[count+1];
        int flag = 0;
        int i = arr1.length-1,j=arr2.length-1;
        int oddEven = 0;
        for (; i >=0&&j>=0 ; i--,j--,count--,oddEven++) {
            if(arr1[i]+arr2[j]==2){
                if(flag !=0){
                    if(oddEven%2==0){//为正
                        result[count] = 1;
                    }else{//为负
                        result[count] = 1;
                    }
                    flag = 0;
                }else{
                    result[count] = 0;
                    flag=1;
                }
            }else{
                if(flag!=0){
                    if(oddEven%2==0){//为正

                    }else{//为负

                    }
                    if(arr1[i]+arr2[j]+flag==2){
                        result[count] = 0;
                        flag=1;
                    }else{
                        result[count] = arr1[i]+arr2[j]+flag;
                        flag=0;
                    }

                }else{
                    result[count] = arr1[i]+arr2[j];
                }
            }
        }
        if(i>=0){
            count++;
            for (int k = i+1; k >=0 ; k--,count--) {
                if(flag==1){
                    if(flag+arr1[k]==2){
                        result[count] = 0;
                        flag=1;
                    }else{
                        result[count] = flag+arr1[k];
                        flag=0;
                    }
                }else{
                    result[count] = arr1[k];
                }
            }
        }
        if(j>=0){
            count++;
            for (int k = j+1; k >=0 ; k--,count--) {
                if(flag==1){
                    if(flag+arr2[k]==2){
                        result[count] = 0;
                        flag=1;
                    }else{
                        result[count] = flag+arr2[k];
                        flag=0;
                    }
                }else{
                    result[count] = arr2[k];
                }
            }
        }
        if(result[0]==0){
            int[] ints = new int[result.length-1];
            for (int k = 1; k < result.length; k++) {
                ints[k-1] = result[k];
            }
            return ints;
        }else{
            return result;
        }
    }

    public int[] addNegabinaryLC(int[] arr1, int[] arr2) {
        int max=Math.max(arr1.length,arr2.length);
        int result[]=new int[max+2];//最多多出两位
        int index=result.length-1,carry=0;
        for (int i = 0; i < result.length; i++) {
            int a1= i< arr1.length? arr1[arr1.length-1-i] :0;//从数组最后开始，没有补0
            int a2= i< arr2.length? arr2[arr2.length-1-i] :0;
            //a1a2= 00 01 10 11  flag=1,-1,0,则sum[-1,3]
            int sum=a1+a2+carry;
            switch (sum){
                case 0:
                    result[index--]=0;
                    carry=0;
                    break;
                case 1:
                    result[index--]=1;
                    carry=0;
                    break;
                case 2:
                    result[index--]=0;
                    carry=-1;
                    break;
                case 3:
                    result[index--]=1;
                    carry=-1;
                    break;
                case -1:
                    result[index--]=1;
                    carry=1;
                    break;
            }
        }
        int not_zero=0;
        //除去前面的0，若结果全是0，保留最后个0
        while(not_zero<result.length-1 &&result[not_zero]==0) {
            not_zero++;
        }
        return Arrays.copyOfRange(result,not_zero,result.length);
    }

    public int[] addNegabinary2(int[] arr1, int[] arr2){
        int[] result = new int[(arr1.length>arr2.length?arr1.length:arr2.length)+2];
        int carrybit=0,i,j,index=result.length-1;
        for (i = arr1.length-1,j=arr2.length-1; i >=0&&j>=0 ; i--,j--) {
            switch (arr1[i]+arr2[j]+carrybit){
                case 2:
                    carrybit=-1;
                    result[index] = 0;
                    index--;
                    break;
                case 1:
                    result[index] = 1;
                    carrybit =0;
                    index--;
                    break;
                case 0:
                    result[index] = 0;
                    carrybit =0;
                    index--;
                    break;
                case -1:
                    result[index] = 1;
                    carrybit =1;
                    index--;
                    break;
                case 3:
                    result[index] = -1;
                    carrybit =-1;
                    index--;
                    break;
            }
        }
        if(i>=0){
            for (;i>=0;i--) {
                switch (arr1[i]+carrybit){
                    case 2:
                        carrybit=-1;
                        result[index] = 0;
                        index--;
                        break;
                    case 1:
                        result[index] = 1;
                        carrybit =0;
                        index--;
                        break;
                    case 0:
                        result[index] = 0;
                        carrybit =0;
                        index--;
                        break;
                    case -1:
                        result[index] = 1;
                        carrybit =1;
                        index--;
                        break;
                    case 3:
                        result[index] = -1;
                        carrybit =-1;
                        index--;
                        break;
                }
            }
        }
        if(j>=0){
            for (; j>=0; j--) {
                switch (arr2[j]+carrybit){
                    case 2:
                        carrybit=-1;
                        result[index] = 0;
                        index--;
                        break;
                    case 1:
                        result[index] = 1;
                        carrybit =0;
                        index--;
                        break;
                    case 0:
                        result[index] = 0;
                        carrybit =0;
                        index--;
                        break;
                    case -1:
                        result[index] = 1;
                        carrybit =1;
                        index--;
                        break;
                    case 3:
                        result[index] = -1;
                        carrybit =-1;
                        index--;
                        break;
                }
            }
        }

        while (carrybit!=0){
            switch (carrybit){
                case 2:
                    carrybit=-1;
                    result[index] = 0;
                    index--;
                    break;
                case 1:
                    result[index] = 1;
                    carrybit =0;
                    if(index>0){
                        index--;
                    }
                    break;
                case 0:
                    result[index] = 0;
                    carrybit =0;
                    index--;
                    break;
                case -1:
                    result[index] = 1;
                    carrybit =1;
                    index--;
                    break;
                case 3:
                    result[index] = -1;
                    carrybit =-1;
                    index--;
                    break;
            }
        }
        int[] tmp = result;
        while (tmp[0]==0&&tmp.length>1){
            int[] tmp2 = new int[tmp.length-1];
            for (int k = 0; k <tmp2.length ; k++) {
                tmp2[k] = tmp[k+1];
            }
            tmp = tmp2;
        }
        return tmp;
    }
}
