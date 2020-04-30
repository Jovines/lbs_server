package com.jovines.lbs_server.controller

import com.jovines.lbs_server.bean.StatusWarp
import com.jovines.lbs_server.config.IMG_PATH
import com.jovines.lbs_server.entity.User
import com.jovines.lbs_server.service.UserService
import org.springframework.dao.DuplicateKeyException
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.lang.reflect.InvocationTargetException
import java.net.PasswordAuthentication
import java.sql.SQLIntegrityConstraintViolationException
import java.util.*
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
        private val userService: UserService) {

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



    private fun savePicture(file: MultipartFile): String {
        if (file.isEmpty) return ""
        var fileName = file.originalFilename // 文件名
        val suffixName = fileName?.substringAfterLast(".") // 后缀名
        if (suffixName == null || suffixName.isEmpty()) return ""
        fileName = "${UUID.randomUUID()}.$suffixName" // 新文件名
        val dest = File("$IMG_PATH$fileName")
        if (!dest.parentFile.exists()) dest.parentFile.mkdirs()
        file.transferTo(dest)
        return fileName
    }

}