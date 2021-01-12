package com.panpan.datastructure.tree;

import com.sun.org.apache.regexp.internal.RE;

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
//        int[] ints=new int[]{9,3,15,20,7};
//        int[] posts ={9,15,7,20,3};
//        buildTree(ints,posts);

//        Integer[] trees = new Integer[]{1,2,3,4,-99,-99,7,8,9,-99,-99,12,13,-99,14};
        Integer[] trees = new Integer[]{10,5,10};
//        {5,4,8,11,null,17,4,7,1,null,null,5,3};
//                                     {5,4,8,11,null,17,4,7,1,null,null,5,3};
        TreeNode treeNode = BinarySearchTree.generateTree(trees);
        sufficientSubsetIteration(treeNode,21);
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
                if(null!=poll.right) {treeNodes.addFirst(poll.right);}
            }
        }
        return result;
    }
    public List<Integer> inorderTraversal2(TreeNode root) {
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
//                    return false;
                }
            }
            root=root.right;
        }
        return null;
    }

    public List<Integer> inorderTraversalRecursion(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        if(null==root){ return result;}
        inorderRecursion(root,result);
        return result;
    }

    public void inorderRecursion(TreeNode root ,List<Integer> result){
        if(null!=root){
            inorderRecursion(root.left,result);
            result.add(root.val);
            inorderRecursion(root.right,result);
        }
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

    // 含义：将一个Node序列化为"parent[child_1,child_2...child_n]"的形式

//

    public String rserialize(TreeNode root, String str) {
        if (root == null) {
            str += "None,";
        } else {
            str += root.val + ",";
            str = rserialize(root.left, str);
            str = rserialize(root.right, str);
        }
        return str;
    }

    public String serialize(TreeNode root) {
        return rserialize(root, "");
    }

    public TreeNode rdeserialize(List<String> l) {
        if (l.get(0).equals("None")) {
            l.remove(0);
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(l.get(0)));
        l.remove(0);
        root.left = rdeserialize(l);
        root.right = rdeserialize(l);
        return root;
    }

    public TreeNode deserialize(String data) {
        String[] data_array = data.split(",");
        List<String> data_list = new LinkedList<String>(Arrays.asList(data_array));
        return rdeserialize(data_list);
    }

    //    根据一棵树的中序遍历与后序遍历构造二叉树。
    //
    //注意:
    //你可以假设树中没有重复的元素。
    //
    //例如，给出
    //
    //中序遍历 inorder = [9,3,15,20,7]
    //后序遍历 postorder = [9,15,7,20,3]

    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        if(postorder.length<=0){
            return null;
        }
        Deque<TreeNodeStruct> stack = new LinkedList<TreeNodeStruct>();
//        Deque<TreeNode> result = new LinkedList<TreeNode>();
        HashMap<Integer, Integer> inMap = new HashMap<>();
        HashMap<Integer, Integer> postMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i],i);
            postMap.put(postorder[i],i);
        }
        TreeNode root = new TreeNode(postorder[postorder.length - 1]);
        TreeNode left = new TreeNode();
        TreeNode right = new TreeNode();
        stack.push(new TreeNodeStruct(left,0,inMap.get(root.val)-1));
        while (!stack.isEmpty()){
            TreeNodeStruct peek = stack.peek();
            if(peek.start<peek.end){

            }else{

            }
        }
        Deque<TreeNodeStruct> result = new LinkedList<TreeNodeStruct>();
        result.push(new TreeNodeStruct(right,inMap.get(root.val)+1,inorder.length-1));
        while (!result.isEmpty()){

        }
return null;
    }
    static class TreeNodeStruct{
        public TreeNode node;
        public int start=-1;
        public int end=-1;
        public TreeNodeStruct(TreeNode node, int start, int end) {
            this.node = node;
            this.start = start;
            this.end = end;
        }
    }
//    给定一棵二叉树的根 root，请你考虑它所有 从根到叶的路径：从根到任何叶的路径。
//    （所谓一个叶子节点，就是一个没有子节点的节点）
//    假如通过节点 node 的每种可能的 “根-叶” 路径上值的总和全都小于给定的 limit，则该节点被称之为「不足节点」，需要被删除。
//    请你删除所有不足节点，并返回生成的二叉树的根。
//    public:
//    TreeNode* sufficientSubset(TreeNode* root, int limit) {
//        // 如果节点为空直接返回即可
//        if(!root)
//            return root;
//        // 如果当前节点为叶子节点
//        if(!root->left && !root->right){
//            // 大于limit直接返回
//            if(root->val >= limit)
//                return root;
//            // 小于limit  返回空节点 即代表删除这个节点
//            else
//                return nullptr;
//        }
//        // 非叶子节点，递归它的左右子节点
//        root->left = sufficientSubset(root->left, limit - root->val);
//        root->right = sufficientSubset(root->right, limit - root->val);
//        // 如果递归后左右子节点为空，就代表经过它的路径都是小于limit，那么这个节点都可以删除了
//        if(!root->left && !root->right)
//            return nullptr;
//        else
//            return root;
//    }

    public static TreeNode sufficientSubset(TreeNode root, int limit) {
        if(null==root){
            return root;
        }
        if(null==root.left&&null==root.right){
            if(root.val-limit>=0){
                return root;
            }else{
                return null;
            }
        }
        root.left=sufficientSubset(root.left,limit-root.val);
        root.right=sufficientSubset(root.right,limit-root.val);
        if(root.left==null&&root.right==null){
            return null;
        }else{
            return root;
        }
    }

    public static TreeNode sufficientSubsetIteration(TreeNode root,int limit){
        if(root==null){
            return root;
        }
        TreeNode head = root;
        Deque<AssistTreeNode> stack = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();
        stack.push(new AssistTreeNode(root,limit-root.val,"root"));
        Deque<AssistTreeNode> leftNodeStack = new LinkedList<>();
        int flag =0;
        while (!stack.isEmpty()){
            AssistTreeNode peek = stack.peek();
            TreeNode node  = peek.node;
            visited.add(node);
            if(node!=null&&(node.left==null&&node.right==null)){
                //路径和小于limit时
                if(peek.currentVal>0){
                    peek.node=null;
                }
            }
            //
            if(node!=null&&visited.contains(node)&&flag==1){
                //不是叶子节点但是要回退
                    AssistTreeNode nodeTmp = stack.pop();
                    if(nodeTmp.leftOrRight.equals("l")){
                        AssistTreeNode rightNode = stack.peek();
                        if(rightNode.node==null&&nodeTmp.node==null){
                            stack.pop();//弹出right节点
                            AssistTreeNode rootTmp = stack.peek();//拿到其父亲节点
                            rootTmp.node=null;
                            //右节点是否弹出过
                        }else if(!visited.contains(rightNode.node)){
                            if(rightNode.node!=null&&(rightNode.node.left!=null||rightNode.node.right!=null)){
                                visited.add(rightNode.node);
                                flag=0;//入栈中
                                //右节点非叶子节点
                                //left节点弹出后，需要存储 nodeTmp
                                leftNodeStack.push(nodeTmp);
                                TreeNode rNode = rightNode.node;
                                if (rNode.right!=null){
                                    if(!visited.contains(rNode.right)){
                                        stack.push(new AssistTreeNode(rNode.right,rightNode.currentVal-rNode.right.val,"r"));
                                    }
                                }else{
                                    stack.push(new AssistTreeNode(null,0,"r"));
                                }

                                if(rNode.left!=null){
                                    if(!visited.contains(rNode.left)){
                                        stack.push(new AssistTreeNode(rNode.left,rightNode.currentVal-rNode.left.val,"l"));
                                    }
                                }else{
                                    stack.push(new AssistTreeNode(null,0,"l"));
                                }
                            }else{
                                visited.add(rightNode.node);
                                //右节点 为叶子节点
                                if(rightNode.node!=null&&(rightNode.currentVal>0)){
                                    rightNode.node=null;
                                }
                                if(rightNode.node==null&&nodeTmp.node==null){
                                    stack.pop();//弹出right节点
                                    AssistTreeNode rootTmp = stack.peek();//拿到其父亲节点
                                    rootTmp.node=null;
                                }else{
                                    stack.pop();//弹出right节点
                                    AssistTreeNode rootTmp = stack.peek();//拿到其父亲节点
                                    rootTmp.node.left=nodeTmp.node;
                                    rootTmp.node.right=rightNode.node;
                                }
                            }
                        }else{
                            stack.pop();//弹出right节点
                            AssistTreeNode rootTmp = stack.peek();//拿到其父亲节点
                            if(rootTmp.leftOrRight.equals("root")){
                                if(nodeTmp.node==null&&rightNode.node==null){
                                    if(rootTmp.leftOrRight.equals("root")){
                                        return null;
                                    }
                                    rootTmp =null;
                                }else{
                                    rootTmp.node.left=nodeTmp.node;
                                    rootTmp.node.right=rightNode.node;
                                }
                                break;
                            }else{
                                if(nodeTmp.node==null&&rightNode.node==null){
                                    if(rootTmp.leftOrRight.equals("root")){
                                        return null;
                                    }
                                    rootTmp = null;
                                }else{
                                    rootTmp.node.left=nodeTmp.node;
                                    rootTmp.node.right=rightNode.node;
                                }

                            }
                        }
                    }else if(nodeTmp.leftOrRight.equals("r")){
                        AssistTreeNode left = leftNodeStack.pop();
                        if(nodeTmp.node==null&&left.node==null){
                            AssistTreeNode rootTmp = stack.peek();
                            rootTmp.node=null;
                        }else {
                            AssistTreeNode rootTmp = stack.peek();
                            rootTmp.node.left = left.node;
                            rootTmp.node.right = nodeTmp.node;
                        }
                    }else{
                        break;
                    }
                    continue;
            }else if(node==null|| (node.left==null&&node.right==null)){
                flag=1;//出栈中
                AssistTreeNode nodeTmp = stack.pop();
                if(nodeTmp.leftOrRight.equals("l")){
                    AssistTreeNode rightNode = stack.peek();
                    if(rightNode.node==null&&nodeTmp.node==null){
                        stack.pop();//弹出right节点
                        AssistTreeNode rootTmp = stack.peek();//拿到其父亲节点
                        if(rootTmp.leftOrRight.equals("root")){
                            return null;
                        }else {
                            rootTmp.node=null;
                        }
                        //右节点是否弹出过
                    }else if(!visited.contains(rightNode.node)){
                        if(rightNode.node!=null&&(rightNode.node.left!=null||rightNode.node.right!=null)){
                            visited.add(rightNode.node);
                            //右节点非叶子节点
                            flag=0;//入栈
                            //left节点弹出后，需要存储nodeTmp
                            leftNodeStack.push(nodeTmp);
                            TreeNode rNode = rightNode.node;
                            if (rNode.right!=null){
                                if(!visited.contains(rNode.right)){
                                    stack.push(new AssistTreeNode(rNode.right,rightNode.currentVal-rNode.right.val,"r"));
                                }
                            }else{
                                stack.push(new AssistTreeNode(null,0,"r"));
                            }

                            if(rNode.left!=null){
                                if(!visited.contains(rNode.left)){
                                    stack.push(new AssistTreeNode(rNode.left,rightNode.currentVal-rNode.left.val,"l"));
                                }
                            }else{
                                stack.push(new AssistTreeNode(null,0,"l"));
                            }
                        }else{
                            //右节点 为叶子节点
                            visited.add(rightNode.node);
                            if(rightNode.node!=null&&(rightNode.currentVal>0)){
                                rightNode.node=null;
                            }
                            if(rightNode.node==null&&nodeTmp.node==null){
                                stack.pop();//弹出right节点
                                AssistTreeNode rootTmp = stack.peek();//拿到其父亲节点
                                if(rootTmp.leftOrRight.equals("root")){
                                    return null;
                                }else{
                                    rootTmp.node=null;
                                }

                            }else{
                                stack.pop();//弹出right节点
                                AssistTreeNode rootTmp = stack.peek();//拿到其父亲节点
                                rootTmp.node.left=nodeTmp.node;
                                rootTmp.node.right=rightNode.node;
                            }
                        }
                    }else{
                        stack.pop();//弹出right节点
                        AssistTreeNode rootTmp = stack.peek();//拿到其父亲节点
                        if(rootTmp.leftOrRight.equals("root")){
                            if(nodeTmp.node==null&&rightNode.node==null){
                                return null;
                            }else{
                                rootTmp.node.left=nodeTmp.node;
                                rootTmp.node.right=rightNode.node;
                            }

                            break;
                        }else{
                            rootTmp.node.left=nodeTmp.node;
                            rootTmp.node.right=rightNode.node;
                        }
                    }
                }else if(nodeTmp.leftOrRight.equals("r")){
                    AssistTreeNode left = leftNodeStack.pop();
                    if(nodeTmp.node==null&&left.node==null){
                        AssistTreeNode rootTmp = stack.peek();
                        if(rootTmp.leftOrRight.equals("root")){
                            return null;
                        }
                        rootTmp.node=null;
                    }else {
                        AssistTreeNode rootTmp = stack.peek();
                        rootTmp.node.left = left.node;
                        rootTmp.node.right = nodeTmp.node;
                    }
                }else{
                    break;
                }
                continue;
            }

            if (node.right!=null){
                if(!visited.contains(node.right)){
                    stack.push(new AssistTreeNode(node.right,peek.currentVal-node.right.val,"r"));
                }
            }else{
                stack.push(new AssistTreeNode(null,0,"r"));
            }

            if(node.left!=null){
                if(!visited.contains(node.left)){
                    stack.push(new AssistTreeNode(node.left,peek.currentVal-node.left.val,"l"));
                }
            }else{
                stack.push(new AssistTreeNode(null,0,"l"));
            }
        }
        return head;
    }
//[5,4,8,11,null,17,4,7,null,null,null,5]

}
class AssistTreeNode{
    public TreeNode node;
    public Integer currentVal;
    public String  leftOrRight;

    public AssistTreeNode(TreeNode node, Integer currentVal, String leftOrRight) {
        this.node = node;
        this.currentVal = currentVal;
        this.leftOrRight = leftOrRight;
    }
}
class Solution{

    HashMap<Integer,Integer> memo = new HashMap<>();
    int[] post;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        for(int i = 0;i < inorder.length; i++) {
            memo.put(inorder[i], i);
        }
        post = postorder;
        TreeNode root = buildTree(0, inorder.length - 1, 0, post.length - 1);
        return root;
    }

    public TreeNode buildTree(int is, int ie, int ps, int pe) {
        if(ie < is || pe < ps) {
            return null;
        }
        int root = post[pe];
        int ri = memo.get(root);
        TreeNode node = new TreeNode(root);
        node.left = buildTree(is, ri - 1, ps, ps + ri - is - 1);
        node.right = buildTree(ri + 1, ie, ps + ri - is, pe - 1);
        return node;
    }
}















