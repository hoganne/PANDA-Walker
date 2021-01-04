package com.panpan.datastructure.tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.BiConsumer;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/31 11:36
 * @Version V1.0
 **/
public class MapSum {
    public static void main(String[] args) {
        new MapSum().insert("apple",3);
    }
    public TrieNode root;
    /** Initialize your data structure here. */
    public MapSum() {
        root = new TrieNode();
    }

    public void insert(String key, int val) {
        char[] chars = key.toCharArray();
        TrieNode cur = root;
        for (int i = 0; i < chars.length; i++) {
            if(!cur.childrenMap.containsKey(chars[i])){
                cur.childrenMap.put(chars[i],new TrieNode());
            }
            cur = cur.childrenMap.get(chars[i]);
        }
        cur.value = val;
    }

    public int sum(String prefix) {
        char[] chars = prefix.toCharArray();
        TrieNode cur = root;
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            if(!cur.childrenMap.containsKey(chars[i])){
                return -1;
            }
            cur=cur.childrenMap.get(chars[i]);
        }
        Deque<TrieNode> stack = new LinkedList<TrieNode>();
        stack.push(cur);
        while (!stack.isEmpty()){
            cur=stack.pop();
            if(cur.value!=null){
                sum+=cur.value;
            }
            cur.childrenMap.forEach(new BiConsumer<Character, TrieNode>() {
                @Override
                public void accept(Character character, TrieNode trieNode) {
                    stack.push(trieNode);
                }
            });
        }

        return sum;
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */
