package com.panpan.datastructure.tree;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/22 15:24
 * @Version V1.0
 **/
//Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}