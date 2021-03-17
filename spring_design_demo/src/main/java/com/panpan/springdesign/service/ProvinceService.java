package com.panpan.springdesign.service;

import com.panpan.springdesign.entity.Province;

import java.util.List;

/**
 * 省份 省份表(Province)表服务接口
 *
 * @author makejava
 * @since 2021-03-16 10:17:56
 */
public interface ProvinceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Province queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Province> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param province 实例对象
     * @return 实例对象
     */
    Province insert(Province province);

    /**
     * 修改数据
     *
     * @param province 实例对象
     * @return 实例对象
     */
    Province update(Province province);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
