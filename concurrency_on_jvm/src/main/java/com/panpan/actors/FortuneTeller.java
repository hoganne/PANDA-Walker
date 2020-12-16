package com.panpan.actors;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/16 14:06
 * @Version V1.0
 **/

import akka.actor.UntypedActor;
import akka.actor.ActorRef;
import akka.actor.Actors;
import akka.actor.ActorTimeoutException;

public class FortuneTeller extends UntypedActor {
//    @Override
//    public void onReceive(final Object name) {
//        getContext().replyUnsafe(String.format("%s you'll rock", name));
//    }
//    public static void main(final String[] args) {
//        final ActorRef fortuneTeller = Actors.actorOf(FortuneTeller.class).start();
//        try {
//            final Object response = fortuneTeller.sendRequestReply("Joe");
//            System.out.println(response);
//        } catch (ActorTimeoutException ex) {
//            System.out.println("Never got a response before timeout");
//        } finally {
//            fortuneTeller.stop();
//        }
//    }

    @Override
    public void onReceive(final Object name) {
        if (getContext().replySafe(String.format("%s you'll rock", name)))
            System.out.println("Message sent for " + name);
        else
            System.out.println("Sender not found for " + name);
    }

    public static void main(final String[] args) {
        final ActorRef fortuneTeller = Actors.actorOf(FortuneTeller.class).start();
        try {
            fortuneTeller.sendOneWay("Bill");
            final Object response = fortuneTeller.sendRequestReply("Joe");
            System.out.println(response);
        } catch (ActorTimeoutException ex) {
            System.out.println("Never got a response before timeout");
        } finally {
            fortuneTeller.stop();
        }
    }
}
