package com.panpan.actors.transactors;

import akka.actor.ActorRef;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/17 15:33
 * @Version V1.0
 **/
public class Transfer {
    public final ActorRef from;
    public final ActorRef to;
    public final int amount;
    public Transfer(final ActorRef fromAccount, final ActorRef toAccount, final int theAmount) {
        from = fromAccount;
        to = toAccount;
        amount = theAmount;
    }
}
