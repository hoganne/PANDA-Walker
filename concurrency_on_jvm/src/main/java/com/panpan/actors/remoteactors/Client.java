package com.panpan.actors.remoteactors;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/17 17:14
 * @Version V1.0
 **/

import static akka.actor.Actors.remote;

import akka.actor.ActorRef;

import java.io.File;

public class Client {
    public static void main(final String[] args) {
        ActorRef systemMonitor = remote().actorFor("system-monitor", "localhost", 8000);
        systemMonitor.sendOneWay("Cores: " + Runtime.getRuntime().availableProcessors());
        systemMonitor.sendOneWay("Total Space: " + new File("/").getTotalSpace());
        systemMonitor.sendOneWay("Free Space: " + new File("/").getFreeSpace());
    }
}
