package com.jovines.lbs_server.controller

import com.google.gson.Gson
import com.jovines.lbs_server.bean.CardMessageReturn
import com.jovines.lbs_server.bean.StatusWarp
import com.jovines.lbs_server.dao.LifecirclemessageitemDao
import com.jovines.lbs_server.dao.UserDao
import com.jovines.lbs_server.entity.Lifecirclemessageitem
import com.jovines.lbs_server.service.LifecirclemessageitemService
import com.jovines.lbs_server.service.UserService
import com.jovines.lbs_server.util.LatLonUtil
import com.jovines.lbs_server.util.dateFormat
import com.jovines.lbs_server.util.savePicture
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
        /**
         * 用户
         */
        @Resource
        private val userService: UserService,

        @Resource
        private val userDao: UserDao,

        @Resource
        private val lifecirclemessageitemDao: LifecirclemessageitemDao
) {

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    fun selectOne(id: Long?): Lifecirclemessageitem? {
        return lifecirclemessageitemService.queryById(id)
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
            @RequestParam("lat") lat: Double,
            @RequestParam("lon") lon: Double,
            @RequestParam("range") range: Int,
            @RequestParam("time") time: Int
    ): StatusWarp<List<CardMessageReturn?>?> {
        val doubles = LatLonUtil.getRange(lat, lon, range)
        val filter = userDao.usersMeetLocationRequirements(
                doubles[0], doubles[1], doubles[2], doubles[3])?.filter { it?.phone != phone }
        //如果有用户
        if (filter != null && filter.isNotEmpty()) {
            //查询符合要求的消息
            val userList = filter.map { it?.phone }
            val timeInMillis = Calendar.getInstance().apply { add(Calendar.HOUR, -time) }.timeInMillis
            val messageList = lifecirclemessageitemDao.checkNearbyNews(doubles[0], doubles[1], doubles[2], doubles[3], timeInMillis)
                    ?.filter { userList.contains(it?.user) }
                    //将数据转换成完成返回数据，（带用户信息的）
                    ?.map {
                        it?.let { lifecirclemessageitem ->
                            val userData = filter.filter { phone -> lifecirclemessageitem.user == phone?.phone }[0]
                            CardMessageReturn(lifecirclemessageitem.id, lifecirclemessageitem.user, lifecirclemessageitem.title
                                    ?: "", lifecirclemessageitem.content ?: "",
                                    lifecirclemessageitem.time,
                                    userData?.nickname ?: "",
                                    userData?.description ?: "",
                                    userData?.avatar ?: "", lifecirclemessageitem.lon, lifecirclemessageitem.lat)
                        }
                    }
            if (messageList != null && messageList.isNotEmpty()) {
                return StatusWarp(1000, messageList)
            }
        }
        return StatusWarp(1001, listOf())
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
            @RequestParam("lat") lat: Double,
            @RequestParam("lon") lon: Double,
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
                                dateFormat.format(Date()), lon, lat,
                                Gson().toJson(list)))
        return if (insert != null) {
            StatusWarp(1000, "成功")
        } else StatusWarp(1001, "失败")
    }

}