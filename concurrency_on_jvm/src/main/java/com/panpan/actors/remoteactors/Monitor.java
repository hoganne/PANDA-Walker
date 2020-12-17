package com.panpan.actors.remoteactors;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/17 17:10
 * @Version V1.0
 **/
import akka.actor.UntypedActor;
import akka.actor.Actors;
import java.io.File;

public class Monitor extends UntypedActor {
    @Override
    public void onReceive(Object message) {
        System.out.println(message);
    }

    public static void main(final String[] args) {
        Actors.remote().start("localhost", 8000).register("system-monitor", Actors.actorOf(Monitor.class));
        System.out.println("Press key to stop");
        System.console().readLine();
        Actors.registry().shutdownAll();
        Actors.remote().shutdown();
    }
}
