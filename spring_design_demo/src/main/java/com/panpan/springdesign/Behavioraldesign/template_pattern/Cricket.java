package com.panpan.springdesign.Behavioraldesign.template_pattern;

/**
 * @Description
 * @Author xupan
 * @Date2021/3/1 17:17
 * @Version V1.0
 **/
public class Cricket extends Game {

    @Override
    void endPlay() {
        System.out.println("Cricket Game Finished!");
    }

    @Override
    void initialize() {
        System.out.println("Cricket Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Cricket Game Started. Enjoy the game!");
    }
}
