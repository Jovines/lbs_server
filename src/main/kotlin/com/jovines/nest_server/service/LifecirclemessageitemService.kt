package com.jovines.nest_server.service

import com.jovines.nest_server.entity.Lifecirclemessageitem

/**
 * (Lifecirclemessageitem)表服务接口
 *
 * @author Jovines
 * @since 2020-04-14 11:59:42
 */
interface LifecirclemessageitemService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    fun queryById(id: Long?): Lifecirclemessageitem?


        /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    fun queryByUser(user: Long?): List<Lifecirclemessageitem?>?



    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    fun queryAllByLimit(offset: Int, limit: Int): List<Lifecirclemessageitem?>?

    /**
     * 新增数据
     *
     * @param lifecirclemessageitem 实例对象
     * @return 实例对象
     */
    fun insert(lifecirclemessageitem: Lifecirclemessageitem?): Lifecirclemessageitem?

    /**
     * 修改数据
     *
     * @param lifecirclemessageitem 实例对象
     * @return 实例对象
     */
    fun update(lifecirclemessageitem: Lifecirclemessageitem): Lifecirclemessageitem?

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    fun deleteById(id: Long?): Boolean

}