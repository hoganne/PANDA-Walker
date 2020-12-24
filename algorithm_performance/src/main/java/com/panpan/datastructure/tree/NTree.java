package com.panpan.datastructure.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/24 11:04
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

}
