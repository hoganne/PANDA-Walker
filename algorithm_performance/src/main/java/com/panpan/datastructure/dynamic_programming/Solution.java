package com.panpan.datastructure.dynamic_programming;

/**
 * @Description
 * @Author xupan
 * @Date2021/3/12 10:46
 * @Version V1.0
 **/
public class Solution {
    final static int INF =Integer.MAX_VALUE;
    //djikstra算法，邻阶矩阵存储图
    public int[] minLen(int[][] matrix){
        int n=matrix[0].length,m=matrix.length;
        int[] dis = new int[matrix[0].length];
        int[] book = new int[matrix[0].length];
        // init dis
        for(int i=1;i<=n;i++) {
            dis[i]=matrix[1][i];
        }
        // init book
        for(int i=1;i<=n;i++) {
            book[i]=0;
        }
        book[1]=1;// 程序以源点为 1来举例

        for(int i=1;i<=n-1;i++)// n-1次循环，而非n次循环(因为 1节点自身已确定)
        {
            // 找到距离1号顶点最近的顶点(min_index)
            int min_num=INF;
            int min_index=0;
            for(int k=1;k<=n;k++)// n次循环
            {
                if(min_num>dis[k] && book[k]==0)
                {
                    min_num=dis[k];
                    min_index=k;
                }
            }
            book[min_index]=1;// 标记
            for(int j=1;j<=n;j++)
            {
                // 节点 min__index =>j 有边
                if(matrix[min_index][j]<INF)
                {
                    // 加入之后使得距离变得更短
                    // 可以写为 dis[j]=min(dis[j],dis[min_index]+e[min_index][j]);
                    if(dis[j]>dis[min_index]+matrix[min_index][j])
                    {
                        dis[j]=dis[min_index]+matrix[min_index][j];
                    }
                }
            }
        }
        return dis;
    }
}
