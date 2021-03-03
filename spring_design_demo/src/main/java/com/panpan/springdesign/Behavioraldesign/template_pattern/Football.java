package com.panpan.springdesign.Behavioraldesign.template_pattern;

/**
 * @Description
 * @Author xupan
 * @Date2021/3/1 17:20
 * @Version V1.0
 **/
public class Football extends Game {

    @Override
    void endPlay() {
        System.out.println("Football Game Finished!");
    }

    @Override
    void initialize() {
        System.out.println("Football Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Football Game Started. Enjoy the game!");
    }
}
