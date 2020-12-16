package com.panpan.actors;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/16 13:26
 * @Version V1.0
 **/
import akka.actor.ActorRef;
import akka.actor.Actors;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

public class UseHollywoodActor {
    public static void main(final String[] args) throws InterruptedException {
//        final ActorRef johnnyDepp = Actors.actorOf(HollywoodActor.class).start();
//        johnnyDepp.sendOneWay("Jack Sparrow");
//        Thread.sleep(100);
//        johnnyDepp.sendOneWay("Edward Scissorhands");
//        Thread.sleep(100);
//        johnnyDepp.sendOneWay("Willy Wonka");
//        Actors.registry().shutdownAll();

        final ActorRef tomHanks = Actors.actorOf(new UntypedActorFactory() {
            @Override
            public UntypedActor create() {
                return new HollywoodActor("Hanks");
            }
        }).start();
        tomHanks.sendOneWay("James Lovell");
        tomHanks.sendOneWay(new StringBuilder("Politics"));
        tomHanks.sendOneWay("Forrest Gump");
        Thread.sleep(1000);
        tomHanks.stop();
    }
    }

