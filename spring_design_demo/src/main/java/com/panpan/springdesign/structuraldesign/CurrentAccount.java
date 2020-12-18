package com.panpan.springdesign.structuraldesign;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/18 11:59
 * @Version V1.0
 **/
public class CurrentAccount implements Account {

    public String getTotalBenefits() {
        return "There is no withdrawal limit for current account";
    }

}
