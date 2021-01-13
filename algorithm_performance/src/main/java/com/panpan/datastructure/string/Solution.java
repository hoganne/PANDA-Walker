package com.panpan.datastructure.string;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description
 * @Author xupan
 * @Date2021/1/13 16:04
 * @Version V1.0
 **/
public class Solution {
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
}
