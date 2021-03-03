package com.panpan.springdesign.Behavioraldesign.template_pattern;

/**
 * @Description
 * @Author xupan
 * @Date2021/3/1 17:14
 * @Version V1.0
 **/
public abstract class Game {
    abstract void initialize();
    abstract void startPlay();
    abstract void endPlay();

    //模板
    public final void play(){

        //初始化游戏
        initialize();

        //开始游戏
        startPlay();

        //结束游戏
        endPlay();
    }
}
