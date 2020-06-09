package com.jovines.nest_server.dao

import com.jovines.nest_server.entity.Banner
import org.apache.ibatis.annotations.Param

/**
 * (Banner)表数据库访问层
 *
 * @author Jovines
 * @since 2020-06-07 18:52:58
 */
interface BannerDao {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    fun queryById(id: Long?): Banner?

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    fun queryAllByLimit(@Param("offset") offset: Int, @Param("limit") limit: Int): List<Banner?>?

    /**
     * 通过实体作为筛选条件查询
     *
     * @param banner 实例对象
     * @return 对象列表
     */
    fun queryAll(banner: Banner?): List<Banner>

    /**
     * 新增数据
     *
     * @param banner 实例对象
     * @return 影响行数
     */
    fun insert(banner: Banner?): Int

    /**
     * 修改数据
     *
     * @param banner 实例对象
     * @return 影响行数
     */
    fun update(banner: Banner?): Int

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    fun deleteById(id: Long?): Int
}