package com.panpan.springdesign.creationdesign.abstractfactory;
import com.panpan.springdesign.creationdesign.Factorypattern.Account;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public abstract class AbstractFactory {
     abstract public  Bank getBank(String bankName);
     abstract public  Account getAccount(String accountType);
}
