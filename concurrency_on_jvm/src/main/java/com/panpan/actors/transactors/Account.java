package com.panpan.actors.transactors;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/17 15:34
 * @Version V1.0
 **/
import akka.transactor.UntypedTransactor;
import akka.stm.Ref;

public class Account extends UntypedTransactor {
    private final Ref<Integer> balance = new Ref<Integer>(0);

    @Override
    public void atomically(final Object message) {
        if(message instanceof Deposit) {
            int amount = ((Deposit)(message)).amount;
            if (amount > 0) {
                balance.swap(balance.get() + amount);
                System.out.println("Received Deposit request " + amount);
            }
        }
        if(message instanceof Withdraw) {
            int amount = ((Withdraw)(message)).amount;
            System.out.println("Received Withdraw request " + amount);
            if (amount > 0 && balance.get() >= amount)
                balance.swap(balance.get() - amount);
            else {
                System.out.println("...insufficient funds...");
                throw new RuntimeException("Insufficient fund");
            }
        }
        if(message instanceof FetchBalance) {
            getContext().replySafe(new Balance(balance.get()));
        }
    }
}
