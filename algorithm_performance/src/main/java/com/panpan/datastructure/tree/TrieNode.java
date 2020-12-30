package com.panpan.datastructure.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/30 16:43
 * @Version V1.0
 **/
public class TrieNode {
    public boolean isWord;
    public Map<Character, TrieNode> childrenMap = new HashMap<>();
}
