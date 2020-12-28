package com.panpan.datastructure.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/28 10:18
 * @Version V1.0
 **/
public class BinarySearchTree {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(7);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(15);
        TreeNode node4 = new TreeNode(9);
        TreeNode node5 = new TreeNode(20);
        node1.left=node2;node1.right=node3;
        node3.left=node4;node3.right=node5;
        BSTIterator bstIterator = new BSTIterator(node1);
        System.out.println(bstIterator.next());
    }
    //迭代判断
    public boolean isValidBST(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        Deque<TreeNode> queue = new LinkedList<>();
        Integer tmp = null;
        while (null!=root||!queue.isEmpty()){
            while (null!=root){
                queue.push(root);
                root = root.left;
            }
            root = queue.pop();
            if(tmp==null){
                tmp = root.val;
            }else{
                if(tmp<root.val){
                    tmp = root.val;
                }else{
                    return false;
                }
            }
            root=root.right;
        }
        return true;
    }

    //递归判断
    public boolean isValidBSTRecursion(TreeNode root) {
        return helper(root, null, null);
    }

    public boolean helper(TreeNode node, Integer lower, Integer upper) {
        if (node == null) {
            return true;
        }

        int val = node.val;
        if (lower != null && val <= lower) {
            return false;
        }
        if (upper != null && val >= upper) {
            return false;
        }

        if (!helper(node.right, val, upper)) {
            return false;
        }
        if (!helper(node.left, lower, val)) {
            return false;
        }
        return true;
    }

}
//二叉搜索树迭代器
class BSTIterator {
    Deque<Integer> result = new LinkedList<Integer>();

    public BSTIterator(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        while(root!=null||!stack.isEmpty()){
            while (root!=null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            result.push(root.val);
            root=root.right;
        }

        System.out.println("result");
    }
    public int next() {
        if(!result.isEmpty()){
            return result.pollLast();
        }else{
            return -1;
        }
    }

    public boolean hasNext() {
        if(result.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}

//偏平化二叉搜索树
class BSTIteratorLC {

    ArrayList<Integer> nodesSorted;
    int index;

    public BSTIteratorLC(TreeNode root) {

        // Array containing all the nodes in the sorted order
        this.nodesSorted = new ArrayList<Integer>();

        // Pointer to the next smallest element in the BST
        this.index = -1;

        // Call to flatten the input binary search tree
        this._inorder(root);
    }

    private void _inorder(TreeNode root) {

        if (root == null) {
            return;
        }

        this._inorder(root.left);
        this.nodesSorted.add(root.val);
        this._inorder(root.right);
    }

    /**
     * @return the next smallest number
     */
    public int next() {
        return this.nodesSorted.get(++this.index);
    }

    /**
     * @return whether we have a next smallest number
     */
    public boolean hasNext() {
        return this.index + 1 < this.nodesSorted.size();
    }
}
