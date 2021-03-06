package com.jovines.nest_server.service

import com.jovines.nest_server.entity.User

/**
 * (User)表服务接口
 *
 * @author Jovines
 * @since 2020-04-11 21:24:11
 */
interface UserService {
    /**
     * 通过ID查询单条数据
     *
     * @param phone 主键
     * @return 实例对象
     */
    fun queryById(phone: Long): User?

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    fun queryAllByLimit(offset: Int, limit: Int): List<User?>?

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    fun insert(user: User): User?

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    fun update(user: User): User?

    /**
     * 通过主键删除数据
     *
     * @param phone 主键
     * @return 是否成功
     */
    fun deleteById(phone: Long): Boolean
}