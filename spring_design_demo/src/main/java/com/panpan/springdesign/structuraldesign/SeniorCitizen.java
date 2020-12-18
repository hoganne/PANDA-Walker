package com.panpan.springdesign.structuraldesign;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/18 12:01
 * @Version V1.0
 **/
public class SeniorCitizen extends AccountDecorator {
    Account account;

    public SeniorCitizen(Account account) {
        this.account = account;
    }
    public String getTotalBenefits() {
        return account.getTotalBenefits() + " other benefits are " + applyOtherBenefits();
    }
    @Override
    String applyOtherBenefits() {
        return " an medical insurance of up to $1,000 for Senior Citizen";
    }

}
