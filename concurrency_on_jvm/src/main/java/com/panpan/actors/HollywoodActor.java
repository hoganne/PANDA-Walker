package com.panpan.actors;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/16 13:23
 * @Version V1.0
 **/
import akka.actor.UntypedActor;

@SuppressWarnings("unchecked")
public class HollywoodActor extends UntypedActor {
//    @Override
//    public void onReceive(final Object role) {
//        System.out.println("Playing " + role + " from Thread " + Thread.currentThread().getName());
//    }

    private final String name;
    public HollywoodActor(final String theName) { name = theName; }
    @Override
    public void onReceive(final Object role) {
        if(role instanceof String)
            System.out.println(String.format("%s playing %s", name, role));
        else
            System.out.println(name + " plays no " + role);
    }
}