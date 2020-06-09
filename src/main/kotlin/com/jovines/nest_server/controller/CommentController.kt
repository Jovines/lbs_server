package com.jovines.nest_server.controller

import com.jovines.nest_server.bean.CommentsBackBean
import com.jovines.nest_server.bean.StatusWarp
import com.jovines.nest_server.dao.CommentMapper
import com.jovines.nest_server.dao.LifecirclemessageitemDao
import com.jovines.nest_server.dao.UserDao
import com.jovines.nest_server.entity.Comment
import com.jovines.nest_server.util.dateFormat
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

/**
 * Created by Jovines on 2020/6/7 15:07
 * description :
 */

@RestController
@RequestMapping("comment")
class CommentController(
        @Resource
        val commentMapper: CommentMapper,
        @Resource
        val userDao: UserDao,
        @Resource
        val lifecirclemessageitemDao: LifecirclemessageitemDao

) {


    /**
     * code:
     *      1000 成功
     *      1001 账号密码错误
     *      1002 账号没注册
     *
     */
    @PostMapping("queryCommentsByMessageId",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun queryCommentsByMessageId(@RequestParam("phone") phone: Long,
                                 @RequestParam("password") password: String,
                                 @RequestParam("messageId") messageId: Long): StatusWarp<List<CommentsBackBean>> {
        val user = userDao.queryById(phone)
        return if (user != null) {
            if (user.password == password) {
                StatusWarp(1000, commentMapper.queryCommentsByMessageId(messageId).mapNotNull {
                    val commentUser = userDao.queryById(it.userId ?: 0)
                    if (commentUser != null) {
                        CommentsBackBean(it.id, it.messageId, commentUser.nickname, commentUser.avatar, it.content, dateFormat.format(it.time), it.userId == user.phone)
                    } else null
                })
            } else {
                StatusWarp(1001, listOf())
            }
        } else {
            StatusWarp(1003, listOf())
        }
    }


    /**
     * code:
     *      1000 成功
     *      1001 账号密码错误
     *      1002 账号没注册
     *      1003 该消息不存在
     *
     */
    @PostMapping("addComment",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun addComment(@RequestParam("phone") phone: Long,
                   @RequestParam("password") password: String,
                   @RequestParam("messageId") messageId: Long,
                   @RequestParam("content") content: String
    ): StatusWarp<String> {
        val user = userDao.queryById(phone)
        return if (user != null) {
            if (user.password == password) {
                val lifecirclemessageitem = lifecirclemessageitemDao.queryById(messageId)
                if (lifecirclemessageitem == null) {
                    StatusWarp(1003, "该消息不存在")
                } else {
                    commentMapper.insert(Comment(content, messageId, phone))
                    StatusWarp(1000, "评论成功")
                }
            } else {
                StatusWarp(1001, "密码错误")
            }
        } else {
            StatusWarp(1002, "该用户没注册")
        }
    }


    /**
     * code:
     *      1000 成功
     *      1001 账号密码错误
     *      1002 账号没注册
     *      1003 这条消息不是你的
     *
     */
    @PostMapping("delete",
            produces = ["application/json;charset=UTF-8"],
            consumes = ["application/x-www-form-urlencoded;charset=UTF-8"])
    fun delete(@RequestParam("phone") phone: Long,
               @RequestParam("password") password: String,
               @RequestParam("commentId") commentId: Long
    ): StatusWarp<String> {
        val user = userDao.queryById(phone)
        return if (user != null) {
            if (user.password == password) {
                val comment = commentMapper.selectByPrimaryKey(commentId)
                if (comment?.userId == user.phone) {
                    commentMapper.deleteByPrimaryKey(commentId)
                    StatusWarp(1000, "删除成功")
                } else {
                    StatusWarp(1003, "这条消息不是你的")
                }
            } else {
                StatusWarp(1001, "密码错误")
            }
        } else {
            StatusWarp(1002, "该用户没注册")
        }
    }
}