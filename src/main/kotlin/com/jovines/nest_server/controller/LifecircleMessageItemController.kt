package com.jovines.nest_server.controller

import com.google.gson.Gson
import com.jovines.nest_server.bean.CardMessageReturn
import com.jovines.nest_server.bean.PersonalMessageDetailsBean
import com.jovines.nest_server.bean.StatusWarp
import com.jovines.nest_server.dao.*
import com.jovines.nest_server.entity.Lifecirclemessageitem
import com.jovines.nest_server.entity.User
import com.jovines.nest_server.entity.Viewrecords
import com.jovines.nest_server.service.LifecirclemessageitemService
import com.jovines.nest_server.util.dateFormat
import com.jovines.nest_server.util.savePicture
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.annotation.Resource

/**
 * (Lifecirclemessageitem)表控制层
 *
 * @author Jovines
 * @since 2020-04-14 11:59:43
 */
@RestController
@RequestMapping("lifecirclemessageitem")
class LifecircleMessageItemController(
        /**
         * 生活圈消息
         */
        @Resource
        private val lifecirclemessageitemService: LifecirclemessageitemService,

        @Resource
        private val userDao: UserDao,

        @Resource
        private val viewrecordsDao: ViewrecordsDao,
        @Resource
        private val highqualityuserDao: HighqualityuserDao,

        @Resource
        private val lifecirclemessageitemDao: LifecirclemessageitemDao,

        @Resource
        private val commentMapper: CommentMapper
) {

    /**
     * 通过用户名查询自己所发布的消息
     *
     */
    @PostMapping("queryMessage")
    fun queryMessage(@RequestParam("phone") phone: Long,
                     @RequestParam("password") password: String): StatusWarp<List<PersonalMessageDetailsBean?>?>? {
        val user = userDao.queryById(phone)
        return if (user != null && user.password == password) {
            val queryByUser = lifecirclemessageitemService
                    .queryByUser(phone)
                    ?.sortedByDescending { it?.time }
                    ?.map {
                        PersonalMessageDetailsBean(it?.id, it?.user, it?.title, it?.content, it?.time, it?.lon, it?.lat, it?.images, viewrecordsDao.getNewsActiveUsers(it?.id).size)
                    }
            StatusWarp(1000, queryByUser ?: listOf())
        } else StatusWarp(1001, listOf())
    }

    /**
     *
     */
    @PostMapping("checkPersonMessage",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun checkPersonData(
            @RequestParam("userOwner") userOwner: Long,
            @RequestParam("checkUser") checkUser: Long
    ): StatusWarp<List<Lifecirclemessageitem?>?> {
        val user = lifecirclemessageitemDao.queryById(userOwner)
        return if (user != null) {
            StatusWarp(1000, lifecirclemessageitemService.queryByUser(checkUser))
        } else
            StatusWarp(1001, listOf())
    }


    /**
     * @param phone 是那个用户在请求
     * @param lat 这个用户的纬度
     * @param lon 这个用户的经度
     * @param range 查找多少米范围内的消息
     * @param time 从现在开始计算往前推移多少个小时的消息
     */
    @PostMapping("findLatestNewsNearby",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun findLatestNewsNearby(
            @RequestParam("phone") phone: Long,
            @RequestParam("range") range: Int,
            @RequestParam("time") time: Int
    ): StatusWarp<List<CardMessageReturn?>?> {
        return StatusWarp(1000, lifecirclemessageitemDao.queryAll(null)
                //将数据转换成完成返回数据，（带用户信息的）
                .map {
                    it.let { lifecirclemessageitem ->
                        val userData = userDao.queryById(lifecirclemessageitem.user ?: 0)
                        CardMessageReturn(
                                lifecirclemessageitem.id,
                                lifecirclemessageitem.user, lifecirclemessageitem.title ?: "",
                                lifecirclemessageitem.content ?: "",
                                dateFormat.format(lifecirclemessageitem.time),
                                userData?.nickname ?: "",
                                userData?.description ?: "",
                                userData?.avatar ?: "",
                                lifecirclemessageitem.lon,
                                lifecirclemessageitem.lat,
                                lifecirclemessageitem.images,
                                viewrecordsDao.getNewsActiveUsers(lifecirclemessageitem.id).size,
                                commentMapper.queryCommentsByMessageId(lifecirclemessageitem.id ?: 0).size
                        )
                    }
                })
    }


    /**
     *
     */
    @PostMapping("postAMessage",
            produces = ["application/json;charset=UTF-8"])
    fun postAMessage(
            @RequestParam("phone") phone: Long,
            @RequestParam("password") password: String,
            @RequestParam("title") title: String,
            @RequestParam("content") content: String,
            @RequestParam("images") files: Array<MultipartFile>
    ): StatusWarp<String> {
        val list = mutableListOf<String>()
        files.forEach {
            list.add(savePicture(it))
        }
        val insert: Lifecirclemessageitem? =
                lifecirclemessageitemService.insert(
                        Lifecirclemessageitem(
                                null, phone, title, content,
                                Date(), 0.0, 0.0,
                                Gson().toJson(list)))
        return if (insert != null) {
            StatusWarp(1000, "成功")
        } else StatusWarp(1001, "失败")
    }

    /**
     * code:
     *      1000 成功
     *      1001 账号密码错误
     *      1002
     *
     */
    @PostMapping("addTimeVisited",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun addTimeVisited(@RequestParam("phone") phone: Long,
                       @RequestParam("password") password: String,
                       @RequestParam("messageId") messageId: Long): StatusWarp<String> {
        val user = userDao.queryById(phone)
        if (user == null || user.password != password) return StatusWarp(1001, "账号密码错误")
        val insert = viewrecordsDao.insert(Viewrecords(null, messageId, phone, Date()))
        return if (insert > 0) StatusWarp(1000, "成功") else StatusWarp(1002, "未知错误")
    }


    /**
     * code:
     *      1000 成功
     *      1001 账号密码错误
     *      1002 位置错误
     *
     */
    @PostMapping("getNewsActiveUsers",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun getNewsActiveUsers(@RequestParam("phone") phone: Long,
                           @RequestParam("password") password: String,
                           @RequestParam("messageId") messageId: Long): StatusWarp<List<User>> {
        val user = userDao.queryById(phone)
        if (user == null || user.password != password) return StatusWarp(1001, listOf())
        val listUser = viewrecordsDao.getNewsActiveUsers(messageId, Calendar.getInstance().apply {
            add(Calendar.HOUR, -24)
        }.time).mapNotNull {
            userDao.queryById(it)
        }
        return StatusWarp(1000, listUser)
    }

    /**
     * code:
     *      1000 成功
     *      1001 账号密码错误
     *
     */
    @PostMapping("deleteMessage",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun deleteMessage(@RequestParam("phone") phone: Long,
                      @RequestParam("password") password: String,
                      @RequestParam("messageId") messageId: Long): StatusWarp<String> {
        val user = userDao.queryById(phone)
        if (user == null || user.password != password) return StatusWarp(1001, "")
        val count = lifecirclemessageitemDao.deleteById(messageId)
        viewrecordsDao.deletePassMessageId(messageId)
        return if (count > 0) {
            StatusWarp(1000, "成功删除")
        } else {
            StatusWarp(1002, "该消息不存在")
        }
    }

    @GetMapping("getQualityUserNews",
            produces = ["application/json;charset=UTF-8"])
    fun getQualityUserNews(): StatusWarp<List<CardMessageReturn>> {
        val list = lifecirclemessageitemDao.getQualityUserNews()
                .mapNotNull {
                    val user = it.user?.let { it1 -> userDao.queryById(it1) }
                    if (user != null)
                        CardMessageReturn(it.id, it.user, it.title ?: "", it.content
                                ?: "", dateFormat.format(it.time), user.nickname ?: "", user.description
                                ?: "", user.avatar
                                ?: "", it.lon, it.lat, it.images, viewrecordsDao.getNewsActiveUsers(it.id).size)
                    else null
                }
        return StatusWarp(1000, list)
    }
}