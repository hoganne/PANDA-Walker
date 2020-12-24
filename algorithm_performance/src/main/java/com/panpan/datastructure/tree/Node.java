package com.panpan.datastructure.tree;

import java.util.List;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/24 11:03
 * @Version V1.0
 **/

/**
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;
    public Node() {}
    public Node(int _val) {
        val = _val;
    }
    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

public class Node {
    public int val;
    public List<Node> children;
    public Node() {
    }
    public Node(int _val) {
        val = _val;
    }
    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
}
