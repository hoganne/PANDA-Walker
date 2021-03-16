package com.panpan.springdesign.entity;

import java.io.Serializable;

/**
 * 省份 省份表(Province)实体类
 *
 * @author makejava
 * @since 2021-03-16 10:17:45
 */
public class Province implements Serializable {
    private static final long serialVersionUID = 440699761942059285L;
    /**
     * 主键id 省份id
     */
    private Integer id;
    /**
     * 省份 省份名称
     */
    private String province;
    /**
     * 内网映射省份
     */
    private Integer code;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
