package com.jovines.nest_server.dao

import com.jovines.nest_server.entity.Comment

/**
 * Created by Jovines on 2020/6/7 15:07
 * description :
 */
interface CommentMapper {
    fun deleteByPrimaryKey(id: Long?): Int
    fun insert(record: Comment?): Int
    fun insertSelective(record: Comment?): Int
    fun selectByPrimaryKey(id: Long?): Comment?
    fun updateByPrimaryKeySelective(record: Comment?): Int
    fun updateByPrimaryKey(record: Comment?): Int
    fun queryCommentsByMessageId(messageID:Long): List<Comment>
}