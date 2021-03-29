package com.example.payment.entity;

import java.io.Serializable;

/**
 * (Payment)实体类
 *
 * @author makejava
 * @since 2021-03-26 10:46:25
 */
public class Payment implements Serializable {
    private static final long serialVersionUID = 990306463578105669L;

    private Long id;

    private String serial;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

}
