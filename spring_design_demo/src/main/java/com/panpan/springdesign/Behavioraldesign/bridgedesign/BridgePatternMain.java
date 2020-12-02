package com.panpan.springdesign.Behavioraldesign.bridgedesign;

import com.panpan.springdesign.Behavioraldesign.bridgedesign.Impl.CurrentAccount;
import com.panpan.springdesign.Behavioraldesign.bridgedesign.Impl.HdfcBank;
import com.panpan.springdesign.Behavioraldesign.bridgedesign.Impl.IciciBank;
import com.panpan.springdesign.Behavioraldesign.bridgedesign.Impl.SavingAccount;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 16:57
 * @Version V1.0
 **/
public class BridgePatternMain {

    public static void main(String[] args) {

        Bank icici = new IciciBank(new CurrentAccount());
        Account current = icici.openAccount();
        current.accountType();

        Bank hdfc = new HdfcBank(new SavingAccount());
        Account saving = hdfc.openAccount();
        saving.accountType();

    }
}
