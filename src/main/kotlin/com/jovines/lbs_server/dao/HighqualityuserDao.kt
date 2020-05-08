package com.jovines.lbs_server.dao

import com.jovines.lbs_server.entity.Highqualityuser
import org.apache.ibatis.annotations.Param

/**
 * (Highqualityuser)表数据库访问层
 *
 * @author Jovines
 * @since 2020-05-07 21:22:47
 */
interface HighqualityuserDao {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    fun queryById(id: Long?): Highqualityuser?

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    fun queryAllByLimit(@Param("offset") offset: Int, @Param("limit") limit: Int): List<Highqualityuser?>?

    /**
     * 通过实体作为筛选条件查询
     *
     * @param highqualityuser 实例对象
     * @return 对象列表
     */
    fun queryAll(highqualityuser: Highqualityuser?): List<Highqualityuser?>?

    /**
     * 新增数据
     *
     * @param highqualityuser 实例对象
     * @return 影响行数
     */
    fun insert(highqualityuser: Highqualityuser?): Int

    /**
     * 修改数据
     *
     * @param highqualityuser 实例对象
     * @return 影响行数
     */
    fun update(highqualityuser: Highqualityuser?): Int

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    fun deleteById(id: Long?): Int
}