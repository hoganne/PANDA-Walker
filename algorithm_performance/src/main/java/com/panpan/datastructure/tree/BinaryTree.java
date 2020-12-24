package com.panpan.datastructure.tree;

import java.util.*;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/22 15:10
 * @Version V1.0
 **/
public class BinaryTree {
    private Integer answer=0;
    private boolean result = false;
    public static void main(String[] args) {

    }
    //根左右
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        //空树返回空
        if(null==root) return result;
        LinkedList<TreeNode> treeNodes = new LinkedList<TreeNode>();
        treeNodes.addFirst(root);
        while (!treeNodes.isEmpty()){
            TreeNode poll = treeNodes.pollFirst();
            result.add(poll.val);
            if(null!=poll.left) treeNodes.addFirst(poll.left);
            if(null!=poll.right) treeNodes.addFirst(poll.right);
        }
        return result;
    }

    //左根右
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        HashSet<TreeNode> visited = new HashSet<TreeNode>();
        if(null==root) return result;
        LinkedList<TreeNode> treeNodes = new LinkedList<TreeNode>();
        treeNodes.addFirst(root);
        while (!treeNodes.isEmpty()){
            TreeNode poll = treeNodes.peekFirst();
            if(null!=poll.left&&!visited.contains(poll.left)) {
                treeNodes.addFirst(poll.left);
                visited.add(poll.left);
            }else{
                treeNodes.pollFirst();
                result.add(poll.val);
                if(null!=poll.right) treeNodes.addFirst(poll.right);
            }
        }
        return result;
    }
    //左右根
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        HashSet<TreeNode> visited = new HashSet<TreeNode>();
        if(null==root) return result;
        LinkedList<TreeNode> treeNodes = new LinkedList<TreeNode>();
        treeNodes.addFirst(root);
        while (!treeNodes.isEmpty()){
            TreeNode peek = treeNodes.peekFirst();
            if(
               (null!=peek.left&&!visited.contains(peek.left))||
               (null!=peek.right&&!visited.contains(peek.right))
                ){
                if(peek.right!=null){
                 treeNodes.addFirst(peek.right);
                 visited.add(peek.right);
                }
                if(peek.left!=null){
                    treeNodes.addFirst(peek.left);
                    visited.add(peek.left);
                }
            }else{
                treeNodes.pollFirst();
                result.add(peek.val);
            }
        }
        return result;
    }

    //层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        //空树返回空
        if(null==root) return result;
        LinkedList<TreeNode> treeNodes = new LinkedList<TreeNode>();
        treeNodes.addLast(root);
        treeNodes.addLast(null);
        List<Integer> level = new ArrayList<Integer>();
        while (!treeNodes.isEmpty()){
            TreeNode poll = treeNodes.pollFirst();
            if(null==poll){
                if(null!=treeNodes.peekFirst()){
                    treeNodes.addLast(null);
                }
                result.add(level);
                level = new ArrayList<Integer>();
                continue;
            }
            level.add(poll.val);
            if(null!=poll.left) treeNodes.addLast(poll.left);
            if(null!=poll.right) treeNodes.addLast(poll.right);
        }
        return result;
    }
    //递归遍历-> 自顶向下的解决方案：前序遍历
    public void recursionMaxDepth(TreeNode root,Integer depth){

        if(root==null) return;

        if(null==root.left&&null==root.right){
            answer = Math.max(answer,depth);
        }
        recursionMaxDepth(root.left,depth+1);
        recursionMaxDepth(root.right,depth+1);

        System.out.println("======"+answer);
        /*
        find maximum Depth
        return specific value for null node
        update the answer if needed
        left_ans = top_down(root,left,left_param)
        right_ans = top_down(root,right_param)
        return the answer if needed
        **/
    }

    /*
    递归遍历->自低向上的解决方案：后序遍历
    **/

    public Integer recursionMaxDepthDown(TreeNode root){
        if(root==null) return 0;
        Integer leftNum = recursionMaxDepthDown(root.left);
        Integer rightNum = recursionMaxDepthDown(root.right);
        return Math.max(leftNum,rightNum)+1;
    }

    //对称二叉树:给定一个二叉树，检查它是否是镜像对称的。

    public boolean isSymmetric(TreeNode root) {
        return check(root,root);
    }

    public boolean check(TreeNode rootOne, TreeNode rootTwo) {
        if(null==rootOne&&null==rootTwo){
            return true;
        }
        if(null==rootOne||null==rootTwo){
            return false;
        }
        return rootOne.val==rootTwo.val&&check(rootOne.left,rootTwo.right)&&check(rootOne.right,rootTwo.left);
    }

    //路径总和：给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
    // 说明: 叶子节点是指没有子节点的节点。
    public boolean hasPathSum(TreeNode root, int sum) {
        if(null==root) return false;
        if(null==root.left&&null==root.right&&0==sum-root.val){
            return true;
        }
        return hasPathSum(root.left,sum-root.val)||hasPathSum(root.right,sum-root.val);
    }
    public boolean hasPathSumT(TreeNode root, int sum){return false;}
    
}













