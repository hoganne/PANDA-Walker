package com.panpan.datastructure.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author xupan
 * @Date2021/2/1 11:28
 * @Version V1.0
 **/
public class Solution {
//    给出矩阵 matrix 和目标值 target，返回元素总和等于目标值的非空子矩阵的数量。
//    子矩阵 x1, y1, x2, y2 是满足 x1 <= x <= x2 且 y1 <= y <= y2 的所有单元 matrix[x][y] 的集合。
//    如果 (x1, y1, x2, y2) 和 (x1', y1', x2', y2') 两个子矩阵中部分坐标不同（如：x1 != x1'），那么这两个子矩阵也不同。

    public static void main(String[] args) {
        int[][] arr = new int[][]{{0,1,0},{1,1,1},{0,1,0}};
        numSubMatrixSumTarget(arr,0);
    }
    
    public static int numSubMatrixSumTarget(int[][] matrix, int target) {
        int res = 0,m=matrix.length,n=matrix[0].length;
        int[][] presum = new int[m][n+1];
        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n + 1; j++) {
                presum[i][j]=presum[i][j]+presum[i][j-1]+matrix[i][j-1];
            }
        }

        Map<Integer,Integer> counter = new HashMap<>();
        for (int c1 = 0; c1 < n + 1; c1++) {
            for (int c2 = c1+1; c2 < n + 1; c2++) {
                counter.put(0,1);
                int cur=0;
                for (int row = 0; row < m; row++) {
                    cur=cur+presum[row][c2]-presum[row][c1];
                    res+=counter.containsKey(cur-target)==true ? counter.get(cur-target):0;
                    counter.put(cur,counter.get(cur)==null?0:counter.get(cur) +1);
                }
                counter.clear();
            }
        }
        return res;
    }

}
