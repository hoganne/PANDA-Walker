//package com.panpan.springdesign.controller;
//
//import com.panpan.springdesign.entity.Province;
//import com.panpan.springdesign.service.ProvinceService;
////import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// * 省份 省份表(Province)表控制层
// *
// * @author makejava
// * @since 2021-03-16 10:17:56
// */
//@RestController
//@RequestMapping("province")
//public class ProvinceController {
//    /**
//     * 服务对象
//     */
//    @Resource
//    private ProvinceService provinceService;
//
//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @GetMapping("selectOne")
//    public Province selectOne(Integer id) {
//        return this.provinceService.queryById(id);
//    }
//
//}
