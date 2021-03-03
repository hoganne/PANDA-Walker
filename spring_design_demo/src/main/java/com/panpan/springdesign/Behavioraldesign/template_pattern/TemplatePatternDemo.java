package com.panpan.springdesign.Behavioraldesign.template_pattern;

/**
 * @Description
 * @Author xupan
 * @Date2021/3/1 17:21
 * @Version V1.0
 **/
public class TemplatePatternDemo {
    public static void main(String[] args) {
        Game game = new Cricket();
        game.play();
        System.out.println();
        game = new Football();
        game.play();
    }
}
