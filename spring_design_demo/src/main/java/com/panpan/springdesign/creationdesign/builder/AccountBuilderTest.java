package com.panpan.springdesign.creationdesign.builder;


/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 15:04
 * @Version V1.0
 **/
public class AccountBuilderTest {

    public static void main(String[] args) {
//        BeanDefinitionBuilder.genericBeanDefinition().
//        RequestMappingHandlerAdapter
        Account account = new Account.AccountBuilder("Saving Account", "Dinesh Rajput", 1111L)

                .balance(38458.32)

                .interest(4.5)

                .type("SAVING")

                .build();
        System.out.println(account);

    }

}