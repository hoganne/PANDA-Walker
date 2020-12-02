package com.panpan.springdesign.creationdesign.builder;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 14:58
 * @Version V1.0
 **/
public class Account {

    private String accountName;
    private Long accountNumber;
    private String accountHolder;
    private double balance;
    private String type;

    private double interest;

    private Account(AccountBuilder accountBuilder) {
        super();

        this.accountName = accountBuilder.accountName;
        this.accountNumber = accountBuilder.accountNumber;
        this.accountHolder = accountBuilder.accountHolder;
        this.balance = accountBuilder.balance;

        this.type = accountBuilder.type;
        this.interest = accountBuilder.interest;

    }

//setters and getters

    public static class AccountBuilder {
        private final String accountName;
        private final Long accountNumber;
        private final String accountHolder;
        private double balance;

        private String type;
        private double interest;

        public AccountBuilder(String accountName, String accountHolder, Long accountNumber) {
            this.accountName = accountName;
            this.accountHolder = accountHolder;
            this.accountNumber = accountNumber;

        }

        public AccountBuilder balance(double balance) {
            this.balance = balance;

            return this;

        }

        public AccountBuilder type(String type) {
            this.type = type;

            return this;

        }

        public AccountBuilder interest(double interest) {
            this.interest = interest;

            return this;

        }

        public Account build() {
            Account user = new Account(this);
            return user;
        }

    }

    @Override
    public String toString() {

        return "Account [accountName=" + accountName + ", accountNumber=" + accountNumber + ", accountHolder="

                + accountHolder + ", balance=" + balance + ", type="

                + type + ", interest=" + interest + "]";

    }

}