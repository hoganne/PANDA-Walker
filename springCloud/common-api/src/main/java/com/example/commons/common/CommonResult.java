package com.example.payment.common;

import java.io.Serializable;

/**
 * @Description
 * @Author xupan
 * @Date2021/3/26 13:39
 * @Version V1.0
 **/
public class CommonResult implements Serializable {
    public Object data;
    public String message;
    public String status;
    public CommonResult(){}
    public CommonResult(Object data, String message, String status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static CommonResult buildCommonResult(Object data, String message, String status){
        return new CommonResult(data,message,status);
    }
    public static CommonResult buildSuccess(Object data){
        return new CommonResult(data,"success","200");
    }
    public static CommonResult buildFail(Object data){
        return new CommonResult(data,"fail","500");
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
