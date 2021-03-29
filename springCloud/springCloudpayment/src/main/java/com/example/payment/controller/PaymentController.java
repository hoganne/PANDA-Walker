package com.example.payment.controller;

import com.example.payment.common.CommonResult;
import com.example.payment.entity.Payment;
import com.example.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * (Payment)表控制层
 *
 * @author makejava
 * @since 2021-03-26 10:46:28
 */
@RestController
@RequestMapping("payment")
public class PaymentController {
    /**
     * 服务对象
     */
    @Resource
    private PaymentService paymentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne/{id}")
    public Payment selectOne(@PathVariable("id") Long id) {
        return this.paymentService.queryById(id);
    }
    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){
        Payment insert = this.paymentService.insert(payment);
        return CommonResult.buildSuccess(insert);
    }
}
