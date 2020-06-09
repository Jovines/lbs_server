package com.jovines.nest_server.dao

import com.jovines.nest_server.entity.User
import org.apache.ibatis.annotations.Param

/**
 * (User)表数据库访问层
 *
 * @author Jovines
 * @since 2020-04-11 21:24:10
 */
interface UserDao {
    /**
     * 通过ID查询单条数据
     *
     * @param phone 主键
     * @return 实例对象
     */
    fun queryById(phone: Long): User?

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    fun queryAllByLimit(@Param("offset") offset: Int, @Param("limit") limit: Int): List<User>?

    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    fun queryAll(user: User): List<User?>?

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    fun insert(user: User): Int

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    fun update(user: User): Int

    /**
     * 通过主键删除数据
     *
     * @param phone 主键
     * @return 影响行数
     */
    fun deleteById(phone: Long): Int

    fun usersMeetLocationRequirements(
            @Param("minLat") minLat: Double,
            @Param("minLon") minLon: Double,
            @Param("maxLat") maxLat: Double,
            @Param("maxLon") maxLon: Double
    ): List<User?>?

}