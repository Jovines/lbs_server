package com.jovines.nest_server.dao

import com.jovines.nest_server.entity.Viewrecords
import org.apache.ibatis.annotations.Param
import java.util.*

/**
 * (Viewrecords)表数据库访问层
 *
 * @author Jovines
 * @since 2020-05-05 17:44:33
 */
interface ViewrecordsDao {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    fun queryById(id: Long?): Viewrecords?

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    fun queryAllByLimit(@Param("offset") offset: Int, @Param("limit") limit: Int): List<Viewrecords?>?

    /**
     * 通过实体作为筛选条件查询
     *
     * @param viewrecords 实例对象
     * @return 对象列表
     */
    fun queryAll(viewrecords: Viewrecords?): List<Viewrecords?>?

    /**
     * 新增数据
     *
     * @param viewrecords 实例对象
     * @return 影响行数
     */
    fun insert(viewrecords: Viewrecords?): Int

    /**
     * 修改数据
     *
     * @param viewrecords 实例对象
     * @return 影响行数
     */
    fun update(viewrecords: Viewrecords?): Int

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    fun deleteById(id: Long?): Int

    fun getNewsActiveUsers(@Param("messageId") messageId: Long?,
                           @Param("time") time: Date? = null): List<Long>

    fun deletePassMessageId(messageId: Long):Int
}