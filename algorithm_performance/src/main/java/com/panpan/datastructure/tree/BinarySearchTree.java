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
//        TreeNode node1 = new TreeNode(5);
//        TreeNode node2 = new TreeNode(3);
//        TreeNode node3 = new TreeNode(6);
//        TreeNode node4 = new TreeNode(2);
//        TreeNode node5 = new TreeNode(4);
//        TreeNode node6 = new TreeNode(7);
//        [4,null,7,6,8,5,null,null,9]
//        TreeNode node1 = new TreeNode(4);
//        TreeNode node2 = new TreeNode(7);
//        TreeNode node3 = new TreeNode(6);
//        TreeNode node4 = new TreeNode(8);
//        TreeNode node5 = new TreeNode(5);
//        TreeNode node6 = new TreeNode(9);
//        node1.left=null;node1.right=node2;
//        node2.left=node3;node2.right=node4;
//        node3.left=node5;node3.right = null;
//        node4.left=null;node4.right=node6;
//        deleteNode(node1,7);
//        BSTIterator bstIterator = new BSTIterator(node1);
//        System.out.println(bstIterator.next());

        Integer[] input = new Integer[]{2,0,33,null,1,25,40,null,null,11,31,34,45,10,18,29,32,null,36,43,46,4,null,12,24,26,30,null,null,35,39,42,44,null,48,3,9,null,14,22,null,null,27,null,null,null,null,38,null,41,null,null,null,47,49,null,null,5,null,13,15,21,23,null,28,37,null,null,null,null,null,null,null,null,8,null,null,null,17,19,null,null,null,null,null,null,null,7,null,16,null,null,20,6};
        TreeNode treeNode = generateTree(input);
        deleteNode(treeNode,33);

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

    public TreeNode searchBST(TreeNode root, int val) {
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        while(root!=null||!stack.isEmpty()){
            while(root!=null){
                stack.push(root);
                if(root.val==val){return root;}
                if(root.val<val){ break; }
                root=root.left;
            }
            root = stack.pop();
            root = root.right;
        }
        return null;
    }
    //给定二叉搜索树（BST）的根节点和要插入树中的值，将值插入二叉搜索树。 返回插入后二叉搜索树的根节点。 输入数据 保证 ，新值和原始二叉搜索树中的任意节点值都不同。
    //注意，可能存在多种有效的插入方式，只要树在插入后仍保持为二叉搜索树即可。 你可以返回 任意有效的结果 。
    //1,给定的树上的节点数介于 0 和 10^4 之间
    //2,每个节点都有一个唯一整数值，取值范围从 0 到 10^8
    //3,-10^8 <= val <= 10^8
    //4,新值和原始二叉搜索树中的任意节点值都不同

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if(null==root){return new TreeNode(val);}
        TreeNode head = root;
        TreeNode pre = root;
        while(null!=root){
            pre=root;
            if(root.val<val){
                root=root.right;
            }else{
                root=root.left;
            }
        }
        if(pre.val<val){
            pre.right= new TreeNode(val);
        }else{
            pre.left=new TreeNode(val);
        }
        return head;
    }

    public static TreeNode deleteNode(TreeNode root, int key) {
        Deque<TreeNode> stack = new LinkedList<>();
        if(root==null){return null;}
        if(root.val==key){
            if(null==root.left&&null==root.right){
               return null;
            }else if(null==root.left||null==root.right){
                return (null==root.left ? root.right:root.left);
            }else{
                TreeNode tmpNode = root.right;
                Deque<TreeNode> nodes = new LinkedList<>();
                boolean passed = false;
                while (tmpNode!=null||!nodes.isEmpty()){
                    while (tmpNode!=null){
                        nodes.push(tmpNode);
                        tmpNode = tmpNode.left;
                    }
                    tmpNode = nodes.pop();
                    if(nodes.peek()!=null){
                        nodes.peek().left = null;
                    }
                    tmpNode.left=root.left;
                    if(!tmpNode.equals(root.right)){
                        tmpNode.right=root.right;
                    }
                    return tmpNode;
                }
            }
        }
        TreeNode tmp = root;
        TreeNode pre = null;
        while (root!=null||!stack.isEmpty()){
            while (null!=root){
                stack.push(root);
                if(root.val<=key){
                    if(root.val==key){
                        root = stack.poll();
                        if(null==root.left&&null==root.right){
                            if(pre.left!=null&&pre.left.equals(root)){
                                pre.left=null;
                            }else{
                                pre.right=null;
                            }
                            return tmp;
                        }else if(null==root.left||null==root.right){
                            if(pre.left!=null&&pre.left.equals(root)){
                                pre.left = (null==root.left ? root.right:root.left);
                            }else{
                                pre.right = (null==root.left ? root.right:root.left);
                            }
                            return tmp;
                        }else{
                            TreeNode tmpNode = root.right;
                            Deque<TreeNode> nodes = new LinkedList<>();
                            boolean passed = false;
                            while (tmpNode!=null||!nodes.isEmpty()){
                                while (tmpNode!=null){
                                    nodes.push(tmpNode);
                                    tmpNode = tmpNode.left;
                                }
                                tmpNode = nodes.poll();
                                if(nodes.peek()!=null){
                                     if(tmpNode.right!=null){
                                         nodes.peek().left = tmpNode.right;
                                     }else{
                                         nodes.peek().left = null;
                                     }
                                }
                                tmpNode.left=root.left;
                                if(!tmpNode.equals(root.right)){
                                    tmpNode.right=root.right;
                                }
                                if(pre.right!=null&&pre.right.equals(root)){
                                    pre.right = tmpNode;
                                }else{
                                    pre.left = tmpNode;
                                }
                                return tmp;
                            }

                        }
                    }
                    break;
                }else{
                    pre = root;
                    root=root.left;
                }
            }
            root = stack.pop();
            pre = root;
            root = root.right;
        }
        return tmp;
    }

    public static TreeNode generateTree(Integer[] tree){
        TreeNode root = new TreeNode(tree[0]);
        TreeNode head = root;
        Deque<Integer> stack = new LinkedList<Integer>();
        Deque<TreeNode> roots = new LinkedList<TreeNode>();
        for (int i = 1; i <=tree.length - 1;i++) {
            stack.addLast(tree[i]);
        }
        roots.add(root);
        while (!stack.isEmpty()){
                    TreeNode rootTmp = roots.pollFirst();
                    Integer left = stack.poll();
                    Integer right = stack.poll();
                    if(null!=left){
                        TreeNode treeNode = new TreeNode(left);
                        rootTmp.left=treeNode;
                        roots.addLast(treeNode);
                    }else{
                        rootTmp.left=null;
                    }
                    if(null!=right){
                        TreeNode treeNode = new TreeNode(right);
                        rootTmp.right = treeNode;
                        roots.addLast(treeNode);
                    }else{
                        rootTmp.right=null;
                    }
                }
        return head;
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
