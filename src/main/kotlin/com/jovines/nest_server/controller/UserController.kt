package com.jovines.nest_server.controller

import com.jovines.nest_server.bean.CardMessageReturn
import com.jovines.nest_server.bean.PremiumUsersReturn
import com.jovines.nest_server.bean.StatusWarp
import com.jovines.nest_server.dao.HighqualityuserDao
import com.jovines.nest_server.dao.LifecirclemessageitemDao
import com.jovines.nest_server.dao.UserDao
import com.jovines.nest_server.entity.Highqualityuser
import com.jovines.nest_server.entity.User
import com.jovines.nest_server.service.UserService
import com.jovines.nest_server.util.dateFormat
import com.jovines.nest_server.util.savePicture
import org.springframework.dao.DuplicateKeyException
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

/**
 * (User)表控制层
 *
 * @author Jovines
 * @since 2020-04-11 21:24:12
 */
@RestController
@RequestMapping("user")
class UserController(
        /**
         * 用户
         */
        @Resource
        private val userService: UserService,
        @Resource
        private val userDao: UserDao,
        @Resource
        private val highqualityuserDao: HighqualityuserDao,

        @Resource
        private val lifecirclemessageitemDao: LifecirclemessageitemDao
) {

    /**
     * 通过主键查询单条数据
     *
     * @param phone 主键
     * @param password
     * @code 1000 成功登陆
     *       1001 该用户名未注册，或者用户名错误
     *       1002 密码错误
     * @return
     */
    @PostMapping("login",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun login(@RequestParam("phone") phone: Long,
              @RequestParam("password") password: String): StatusWarp<User> {
        val user = userService.queryById(phone)
        return if (user != null) {
            if (user.password == password) {
                StatusWarp(1000, user)
            } else {
                StatusWarp(1002, User())
            }
        } else {
            StatusWarp(1001, User())
        }
    }


    /**
     * 注册
     *
     * @param phone 主键
     * @param password 密码
     * @param nickname 昵称
     * @code 1000 成功注册
     *       1001 该用户名已经注册
     *       1002 未知错误
     * @return
     */
    @PostMapping("register",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun register(@RequestParam("phone") phone: Long,
                 @RequestParam("password") password: String,
                 @RequestParam("nickname") nickname: String
    ): StatusWarp<User> {
        val user: User?
        try {
            user = userService.insert(User(phone, nickname, password))
        } catch (e: DuplicateKeyException) {
            return StatusWarp(1001, User())
        }
        return if (user != null) {
            StatusWarp(1000, user)
        } else {
            StatusWarp(1002, User())
        }
    }


    /**
     * code:
     *      1000 成功更新
     *      1001 更新失败
     */
    @PostMapping("/changeAvatar", produces = ["application/json;charset=UTF-8"])
    fun changeAvatar(@RequestParam(value = "image") file: MultipartFile,
                     @RequestParam("phone") phone: Long,
                     @RequestParam("password") password: String,
                     request: HttpServletRequest): StatusWarp<User> {
        var user = userService.queryById(phone)
        if (user != null) {
            if (user.password ?: "" == password) {
                val avatar = savePicture(file)
                user = userService.update(User(phone, avatar = avatar))
            }
        }
        return if (user == null) StatusWarp(1001, User()) else StatusWarp(1000, user)
    }


    @PostMapping("findNearby",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun findNearby(
            @RequestParam("phone") phone: Long,
            @RequestParam("range") range: Int,
            @RequestParam("time") time: Int
    ): StatusWarp<List<User?>?> {
//        val date = Calendar.getInstance().apply { add(Calendar.HOUR, -time) }.time
//        val messageList = lifecirclemessageitemDao.checkNearbyNews(time = date)?.map { it?.user }
////        val highQualityUserList = highqualityuserDao.queryAll(null)?.mapNotNull { it?.user }
//        val userList = userDao
//                .usersMeetLocationRequirements(doubles[0], doubles[1], doubles[2], doubles[3])
//                ?.filter {
//                    it?.phone != phone
//                            && messageList?.contains(it?.phone) == true
////                            && highQualityUserList?.contains(it?.phone) == false
//                }
        return StatusWarp(1000, listOf())
    }

    @PostMapping("updateLocation",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun updateLocation(
            @RequestParam("phone") phone: Long,
            @RequestParam("password") password: String): StatusWarp<User> {
        val update = userService.update(User(phone))
        return if (update != null) StatusWarp(1000, update)
        else StatusWarp(1001, User())
    }


    /**
     * code：
     *      1000 请求成功
     *      1001 账号密码错误
     *      1002 未知错误
     *      1003 未提供更改数据
     */
    @PostMapping("updateUserInformation",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun updateUserInformation(@RequestParam("phone") phone: Long,
                              @RequestParam("password") password: String,
                              @RequestParam("nickname", required = false) nickname: String?,
                              @RequestParam("description", required = false) description: String?
    ): StatusWarp<User> {
        val checkUser = userDao.queryById(phone)
        return if (checkUser?.password == password) {
            var realName = nickname
            if (nickname != null) {
                if (nickname.isBlank()) {
                    val toString = checkUser.phone.toString()
                    realName = "用户${toString.substring(toString.length - 4, toString.length)}"
                }
            }
            val user = userService.update(User(phone, realName, description = description))
            if (user != null) {
                user.password = null
                StatusWarp(1000, user)
            } else StatusWarp(1002, User())
        } else StatusWarp(1001, User())
    }

    @GetMapping("getPremiumUsers",
            produces = ["application/json;charset=UTF-8"])
    fun getPremiumUsers(): StatusWarp<List<PremiumUsersReturn>> {
        val queryAll = highqualityuserDao.queryAll(Highqualityuser())
        val list = queryAll
                ?.mapNotNull {
                    it?.user?.let { it1 ->
                        Pair(it, userDao.queryById(it1))
                    }
                }?.map {
                    PremiumUsersReturn(it.first.id, it.first.user,
                            it.first.jointime, it.first.goodreason,
                            it.second?.nickname, it.second?.description,
                            it.second?.avatar, it.second?.lat, it.second?.lon)
                }
        list?.forEach {
            it.messages = lifecirclemessageitemDao.queryAllByLimit(0, 1, it.user)?.mapNotNull { lifecirclemessageitem ->
                if (lifecirclemessageitem != null)
                    CardMessageReturn(lifecirclemessageitem.id, lifecirclemessageitem.user, lifecirclemessageitem.title
                            ?: "", lifecirclemessageitem.content ?: "",
                            dateFormat.format(lifecirclemessageitem.time),
                            it.nickname ?: "",
                            it.description ?: "",
                            it.avatar
                                    ?: "", lifecirclemessageitem.lon, lifecirclemessageitem.lat, lifecirclemessageitem.images)
                else null
            }
        }
        return if (list != null) {
            StatusWarp(1000, list)
        } else {
            StatusWarp(1001, listOf())
        }
    }
}