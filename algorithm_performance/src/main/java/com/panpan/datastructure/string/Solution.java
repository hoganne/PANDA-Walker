package com.panpan.datastructure.string;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * @Description
 * @Author xupan
 * @Date2021/1/13 16:04
 * @Version V1.0
 **/
public class Solution {
   static int resultCount =0;

    public static void main(String[] args) {
        numTilePossibilities("AAB");
    }
//给出第一个词 first 和第二个词 second，考虑在某些文本
//text 中可能以 "first second third" 形式出现的情况，
//其中 second 紧随 first 出现，third 紧随 second 出现。
//对于每种这样的情况，将第三个词 "third" 添加到答案中，并返回答案。

    public String[] findOcurrences(String text, String first, String second) {
        String[] source = text.split(" ");
        ArrayList<String> result = new ArrayList<>();
        if(source.length<=2){
            return null;
        }else{
            int fi = 0;
            int si =fi+1;
            while (si<=source.length-1){
                if(source[fi].equals(first)&&source[si].equals(second)){
                    if(si+1<=source.length-1){
                        result.add(source[si+1]);
                    }
                }
                fi++;si++;
            }
        }
        return result.toArray(new String[]{});
    }

//leetcode 1079:活字印刷
//你有一套活字字模 tiles，其中每个字模上都刻有一个字母 tiles[i]。
// 返回你可以印出的非空字母序列的数目。
//注意：本题中，每个活字字模只能使用一次。

    public static int numTilePossibilities(String tiles) {
        TreeNode root = new TreeNode();
        char[] node = tiles.toCharArray();
        HashMap<Character, Integer> cache = new HashMap<>();
        Deque<HashMap<Character, Integer>> result = new LinkedList<>();
        for (int i = 0; i < node.length; i++) {
            if(cache.containsKey(node[i])){
                cache.put(node[i],(cache.get(node[i])+1));
            }else{
                cache.put(node[i],1);
            }
        }
        cache.forEach(new BiConsumer<Character, Integer>() {
            @Override
            public void accept(Character character, Integer integer) {
                HashMap<Character,Integer> clone = (HashMap<Character, Integer>) cache.clone();
                if(integer>0){
                    resultCount++;
                    integer--;
                    clone.put(character,integer);
                }else{
                    clone.remove(character);
                }
                result.push(clone);
            }
        });
        while (!result.isEmpty()){
            HashMap<Character, Integer> pop = result.pop();
            pop.forEach(new BiConsumer<Character, Integer>() {
                @Override
                public void accept(Character character, Integer integer) {
                    HashMap<Character,Integer> clone = (HashMap<Character, Integer>) pop.clone();
                    if(integer>0){
                        resultCount++;
                        integer--;
                        clone.put(character,integer);
                    }else{
                        clone.remove(character);
                    }
                    result.push(clone);
                }
            });
        }
        return resultCount;
    }
}
class TreeNode{

}
