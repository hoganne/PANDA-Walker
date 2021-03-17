package com.panpan.springdesign.dao;

import com.panpan.springdesign.entity.Province;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 省份 省份表(Province)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-16 10:17:52
 */
public interface ProvinceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Province queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Province> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param province 实例对象
     * @return 对象列表
     */
    List<Province> queryAll(Province province);

    /**
     * 新增数据
     *
     * @param province 实例对象
     * @return 影响行数
     */
    int insert(Province province);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Province> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Province> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Province> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Province> entities);

    /**
     * 修改数据
     *
     * @param province 实例对象
     * @return 影响行数
     */
    int update(Province province);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

