package com.jovines.lbs_server.service.impl

import com.jovines.lbs_server.dao.LifecirclemessageitemDao
import com.jovines.lbs_server.entity.LifecircleMessageItem
import com.jovines.lbs_server.service.LifecirclemessageitemService
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * (Lifecirclemessageitem)表服务实现类
 *
 * @author Jovines
 * @since 2020-04-14 11:59:43
 */
@Service("lifecirclemessageitemService")
class LifecirclemessageitemServiceImpl(
        @Resource
        private val lifecirclemessageitemDao: LifecirclemessageitemDao
) : LifecirclemessageitemService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    override fun queryById(id: Long?): LifecircleMessageItem? {
        return lifecirclemessageitemDao.queryById(id)
    }

    override fun queryByUser(user: Long?): List<LifecircleMessageItem?>? {
        return lifecirclemessageitemDao.queryAll(LifecircleMessageItem(null,user,null,null,null))
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    override fun queryAllByLimit(offset: Int, limit: Int): List<LifecircleMessageItem?>? {
        return lifecirclemessageitemDao.queryAllByLimit(offset, limit)
    }

    /**
     * 新增数据
     *
     * @param lifecircleMessageItem 实例对象
     * @return 实例对象
     */
    override fun insert(lifecircleMessageItem: LifecircleMessageItem?): LifecircleMessageItem? {
        lifecirclemessageitemDao.insert(lifecircleMessageItem)
        return lifecircleMessageItem
    }

    /**
     * 修改数据
     *
     * @param lifecircleMessageItem 实例对象
     * @return 实例对象
     */
    override fun update(lifecircleMessageItem: LifecircleMessageItem): LifecircleMessageItem? {
        lifecirclemessageitemDao.update(lifecircleMessageItem)
        return queryById(lifecircleMessageItem.id)
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    override fun deleteById(id: Long?): Boolean {
        return lifecirclemessageitemDao.deleteById(id) > 0
    }
}