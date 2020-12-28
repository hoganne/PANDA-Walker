package com.panpan.datastructure.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/25 17:19
 * @Version V1.0
 **/

public class Codec{
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        node1.left=node2;node1.right=node3;
        node3.left=node4;node3.right=node5;
        String str= new Codec().serialize(node1);
        System.out.println(str);
        TreeNode deserialize = new Codec().deserialize(str);

    }
    public String serialize(TreeNode root) {
        // 边界
        if (root == null) { return ""; }
        // 将parent加入
        StringBuilder ans = new StringBuilder();
        ans.append(root.val);
        if (null==root.left&&null==root.right) {
            return ans.toString();
        }
        // 将children都加入，children的两侧用[]包裹
        ans.append("[");
        if(root.left!=null){
            ans.append('<');
            ans.append(serialize(root.left));
            ans.append('>');
            ans.append(',');
        }
        if(root.right!=null){
            ans.append('{');
            ans.append(serialize(root.right));
            ans.append('}');
            ans.append(',');
        }
        ans.deleteCharAt(ans.length() - 1);
        ans.append("]");

        return ans.toString();
    }

    //         含义：将一个形为"parent[child_1,child_2...child_n]"的字符串反序列化为TreeNode
    public TreeNode deserialize(String data) {
        if (data.isEmpty()) { return null; }
        // 找到parent
        int idx = data.indexOf("[");
        // 如果没有children则返回

        if (idx == -1) { return new TreeNode(Integer.parseInt(data)); }

        // 如果有children

        String val = data.substring(0, data.indexOf("["));

        TreeNode root = new TreeNode(Integer.parseInt(val));

        // 解析紧跟着parent的[]中的字符串，将其分为一个个代表child的字符串

        List<String> cData = parse(data.substring(idx + 1, data.length() - 1));
        if(cData.get(0).equals("L")){
            root.left = deserialize(cData.get(1));
        }else if(cData.get(0).equals("R")){
            root.right = deserialize(cData.get(1));
        }else{
            root.left = deserialize(cData.get(0));
            root.right = deserialize(cData.get(1));
        }

        return root;
    }

    //         解析形为"child_1,child_2...child_n"的字符串
    //         将其分为多个字符串，分别代表child_1,child_2...child_n
    List<String> parse(String data) {
        List<String> ans = new ArrayList<String>();
        int leftBracket = 0;
        StringBuilder sb = new StringBuilder();
        if(data.charAt(0)=='<'){
            if(data.charAt(data.length()-1)=='>'){
                ans.add("L");
                ans.add(data.substring(1,data.length()-1));
                return ans;
            }else{
                int index = 0;
                for (char c : data.toCharArray()) {
                    if (c == '<') {
                        leftBracket++;
                    } else if (c == '>') {
                        leftBracket--;
                    } else if (c == ',') {
                        if (leftBracket == 0) {
                            ans.add(sb.toString().substring(1,sb.length()-1));
                            System.out.println(sb.toString().substring(1,sb.length()-1));
                            sb.setLength(0);
                            index++;
                            break;
                        }
                    }
                    index++;
                    sb.append(c);
                }
                index++;
                ans.add(data.substring(index,data.length()-1));
                return ans;
            }
        }else{
            ans.add("R");
            ans.add(data.substring(1,data.length()-1));
            return ans;
        }
    }

}