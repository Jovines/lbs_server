package com.jovines.lbs_server.service.impl

import com.jovines.lbs_server.dao.UserDao
import com.jovines.lbs_server.entity.User
import com.jovines.lbs_server.service.UserService
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * (User)表服务实现类
 *
 * @author Jovines
 * @since 2020-04-11 21:24:11
 */
@Service("userService")
class UserServiceImpl
    (@Resource
    private val userDao: UserDao): UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param phone 主键
     * @return 实例对象
     */
    override fun queryById(phone: Long): User? {
        return userDao.queryById(phone)
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    override fun queryAllByLimit(offset: Int, limit: Int): List<User?>? {
        return userDao.queryAllByLimit(offset, limit)
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    override fun insert(user: User): User? {
        userDao.insert(user)
        return user
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    override fun update(user: User): User? {
        userDao.update(user)
        return this.queryById(user.phone)
    }

    /**
     * 通过主键删除数据
     *
     * @param phone 主键
     * @return 是否成功
     */
    override fun deleteById(phone: Long): Boolean {
        return userDao.deleteById(phone) > 0
    }
}