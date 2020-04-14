package com.jovines.lbs_server.controller

import com.jovines.lbs_server.entity.Lifecirclemessageitem
import com.jovines.lbs_server.service.LifecirclemessageitemService
import com.jovines.lbs_server.service.UserService
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource

/**
 * (Lifecirclemessageitem)表控制层
 *
 * @author Jovines
 * @since 2020-04-14 11:59:43
 */
@RestController
@RequestMapping("lifecirclemessageitem")
class LifecirclemessageitemController(
        /**
         * 生活圈消息
         */
        @Resource
        private val lifecirclemessageitemService: LifecirclemessageitemService,
        /**
         * 用户
         */
        @Resource
        private val userService: UserService) {

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
    @PostMapping("checkPersonData",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun checkPersonData(
            @RequestParam("userOwner") userOwner: Long,
            @RequestParam("checkUser") checkUser: Long
    ):List<Lifecirclemessageitem?>? {
//        val userOwner = userService.queryById(userOwner)
//        if (userOwner != null) {
            return lifecirclemessageitemService.queryByUser(checkUser)
//        }
//        return null
    }
}