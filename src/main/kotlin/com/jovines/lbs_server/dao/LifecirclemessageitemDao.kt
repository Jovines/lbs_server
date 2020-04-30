package com.jovines.lbs_server.dao

import com.jovines.lbs_server.entity.Lifecirclemessageitem
import org.apache.ibatis.annotations.Param

/**
 * (Lifecirclemessageitem)表数据库访问层
 *
 * @author Jovines
 * @since 2020-04-14 11:59:42
 */
interface LifecirclemessageitemDao {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    fun queryById(id: Long?): Lifecirclemessageitem?


    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    fun queryAllByLimit(@Param("offset") offset: Int, @Param("limit") limit: Int): List<Lifecirclemessageitem?>?

    /**
     * 通过实体作为筛选条件查询
     *
     * @param lifecirclemessageitem 实例对象
     * @return 对象列表
     */
    fun queryAll(lifecirclemessageitem: Lifecirclemessageitem?): List<Lifecirclemessageitem?>?

    /**
     * 新增数据
     *
     * @param lifecirclemessageitem 实例对象
     * @return 影响行数
     */
    fun insert(lifecirclemessageitem: Lifecirclemessageitem?): Int

    /**
     * 修改数据
     *
     * @param lifecirclemessageitem 实例对象
     * @return 影响行数
     */
    fun update(lifecirclemessageitem: Lifecirclemessageitem?): Int

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    fun deleteById(id: Long?): Int


}