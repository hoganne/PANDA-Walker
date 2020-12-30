package com.panpan.datastructure.tree;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/30 16:40
 * @Version V1.0
 **/
public class TrieTree {
    /** Initialize your data structure here. */
    private TrieNode trie;
    public TrieTree() {
        trie = new TrieNode();
        trie.isWord=false;
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode cur = trie;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(!cur.childrenMap.containsKey(chars[i])){
                cur.childrenMap.put(chars[i],new TrieNode());
            }
            cur=cur.childrenMap.get(chars[i]);
        }
        cur.isWord =true;
    }

    /** Returns if the word is in the trie.
     * Initialize: cur = root
     * for each char c in target string S:
     * 		if cur does not have a child c:
     * 		search fails
     * 		cur = cur.children[c]
     * search successes
     * */
    public boolean search(String word) {
        TrieNode cur = trie;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(!cur.childrenMap.containsKey(chars[i])){
                return false;
            }
            cur=cur.childrenMap.get(chars[i]);
        }

        return cur.isWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix.
     *
     * Initialize: cur = root
     * for each char c in target string S:
     * 		if cur does not have a child c:
     * 		search fails
     * 		cur = cur.children[c]
     * search successes
     * */
    public boolean startsWith(String prefix) {
        TrieNode cur = trie;
        char[] chars = prefix.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(!cur.childrenMap.containsKey(chars[i])){
                return false;
            }
            cur=cur.childrenMap.get(chars[i]);
        }
        return true;
    }
}
/**
 * Your Trie object will be instantiated and called as such:
 * TrieTree obj = new TrieTree();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */