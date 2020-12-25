package com.panpan.datastructure.tree;

import java.util.*;
import java.util.function.Consumer;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/25 17:43
 * @Version V1.0
 **/
public class NTree {
    //前

    public List<Integer> preorder(Node root) {
        List<Integer> result = new ArrayList<Integer>();
        if(null==root) return result;
        LinkedList<Node> nodes = new LinkedList<Node>();
        nodes.addFirst(root);
        while (!nodes.isEmpty()){
            Node node = nodes.pollFirst();
            result.add(node.val);
            for (int i = node.children.size()-1; i >=0 ; i--) {
                nodes.addFirst(node.children.get(i));
            }
            //            node.children.forEach(new Consumer<Node>() {
//                @Override
//                public void accept(Node node) {
//                    if(null!=node){
//                        nodes.addFirst(node);
//                    }
//                }
//            });
        }
        return result;
    }
    //后

    public List<Integer> postorder(Node root) {
        List<Integer> result = new ArrayList<Integer>();
        if(null==root) return result;
        LinkedList<Node> nodes = new LinkedList<Node>();
        nodes.addFirst(root);
        HashSet<Node> visited = new HashSet<>();
        while(!nodes.isEmpty()){
            Node node = nodes.peekFirst();
            boolean pass = true;
            for (int i = node.children.size()-1; i >=0; i--){
                Node nodeTmp = node.children.get(i);
                if(nodeTmp!=null&&!visited.contains(nodeTmp)){
                    nodes.addFirst(nodeTmp);
                    visited.add(nodeTmp);
                    pass=false;
                }
            }
            if(pass){
                nodes.pollFirst();
                result.add(node.val);
            }
        }
        return result;
    }
    public List<Integer> postorderLC(Node root) {
        //先进后出
        LinkedList<Node> stack = new LinkedList<>();
        //进入和输出倒叙
        LinkedList<Integer> output = new LinkedList<>();

        if (root == null) {
            return output;
        }
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pollLast();
            output.addFirst(node.val);
            for (Node item : node.children) {
                if (item != null) {
                    stack.add(item);
                }
            }
        }
        return output;
    }
    //层
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(null==root) return result;
        LinkedList<Node> nodes = new LinkedList<Node>();
        nodes.addLast(root);
        nodes.addLast(null);
        List<Integer> tmp = new ArrayList<Integer>();
        while (!nodes.isEmpty()){
            Node poll = nodes.pollFirst();
            if(poll!=null){
                tmp.add(poll.val);
                poll.children.forEach(new Consumer<Node>() {
                    @Override
                    public void accept(Node node) {
                        nodes.addLast(node);
                    }
                });
            }else{
                result.add(tmp);
                if(nodes.peekFirst()!=null){
                    tmp = new ArrayList<Integer>();
                    nodes.addLast(null);
                }
            }
        }
        return result;
    }

    //recursion
    public int maxDepth(Node root) {
        if(null==root){return 0;}
        ArrayList<Integer> sorts = new ArrayList<Integer>();
        root.children.forEach(new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                sorts.add(maxDepth(node));
            }
        });
        return sorts.size()==0? 1: Collections.max(sorts)+1;
    }


    // 含义：将一个Node序列化为"parent[child_1,child_2...child_n]"的形式

    public String serialize(Node root) {
        // 边界
        if (root == null) { return ""; }
        // 将parent加入
        StringBuilder ans = new StringBuilder();
        ans.append(root.val);
        if (root.children.isEmpty()) {
            return ans.toString();
        }
        // 将children都加入，children的两侧用[]包裹
        ans.append("[");
        for (Node c : root.children) {
            // 重新利用serialize()函数的含义，把每一个child Node都序列化即可
            ans.append(serialize(c));
            ans.append(",");
        }
        ans.deleteCharAt(ans.length() - 1);
        ans.append("]");

        return ans.toString();
    }

    // 含义：将一个形为"parent[child_1,child_2...child_n]"的字符串反序列化为Node
    public Node deserialize(String data) {
        // 边界
        if (data.isEmpty()) { return null; }

        // 找到parent
        int idx = data.indexOf("[");
        // 如果没有children则返回
        if (idx == -1) return new Node(Integer.parseInt(data), new ArrayList<>());

        // 如果有children
        String val = data.substring(0, data.indexOf("["));
        Node root = new Node(Integer.parseInt(val), new ArrayList<>());
        // 解析紧跟着parent的[]中的字符串，将其分为一个个代表child的字符串
        List<String> cData = parse(data.substring(idx + 1, data.length() - 1));
        for (String c : cData) {
            // 重新利用deserialize()函数的含义，把每一个child的字符串都反序列化再加入parent的children中即可
            root.children.add(deserialize(c));
        }
        return root;
    }

    // 解析形为"child_1,child_2...child_n"的字符串
    // 将其分为多个字符串，分别代表child_1,child_2...child_n
    List<String> parse(String data) {
        List<String> ans = new ArrayList<>();
        int leftBracket = 0;
        StringBuilder sb = new StringBuilder();
        for (char c : data.toCharArray()) {
            if (c == '[') {
                leftBracket++;
            } else if (c == ']') {
                leftBracket--;
            } else if (c == ',') {
                if (leftBracket == 0) {
                    ans.add(sb.toString());
                    sb.setLength(0);
                    continue;
                }
            }
            sb.append(c);
        }
        ans.add(sb.toString());
        return ans;
    }
}
