package com.panpan.datastructure.tree;

import java.util.*;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/22 15:10
 * @Version V1.0
 **/
public class BinaryTree {
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
}













