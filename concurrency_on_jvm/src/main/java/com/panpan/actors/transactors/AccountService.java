package com.panpan.actors.transactors;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/17 15:36
 * @Version V1.0
 **/

import akka.transactor.UntypedTransactor;
import akka.actor.ActorRef;
import akka.transactor.SendTo;

import java.util.Set;

public class AccountService extends UntypedTransactor {

    @Override
    public Set<SendTo> coordinate(final Object message) {
        if (message instanceof Transfer) {
            Set<SendTo> coordinations = new java.util.HashSet<SendTo>();
            Transfer transfer = (Transfer) message;
            coordinations.add(sendTo(transfer.to, new Deposit(transfer.amount)));
            coordinations.add(sendTo(transfer.from, new Withdraw(transfer.amount)));
            return java.util.Collections.unmodifiableSet(coordinations);
        }
        return nobody();
    }

    @Override
    public void atomically(final Object message) {
    }
}