package com.panpan.actors.coordinating;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/17 16:59
 * @Version V1.0
 **/
import akka.transactor.Transactor.*;

public interface Account {
    int getBalance();
//    @Coordinated void deposit(final int amount);
//    @Coordinated void withdraw(final int amount);
}
