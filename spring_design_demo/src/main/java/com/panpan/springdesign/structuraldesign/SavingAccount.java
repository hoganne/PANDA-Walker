package com.panpan.springdesign.structuraldesign;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/18 11:58
 * @Version V1.0
 **/
public class SavingAccount implements Account {

    public String getTotalBenefits() {
        return "This account has 4% interest rate with per day $5000 withdrawal limit";
    }
}
