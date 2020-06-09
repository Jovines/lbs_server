package com.jovines.nest_server.bean

/**
 * Created by Jovines on 2020/6/7 15:42
 * description :
 */
data class CommentsBackBean(var id: Long? = null,
                            var messageId: Long? = null,
                            var userNickName: String? = null,
                            var avatar: String? = null,
                            var content: String? = null,
                            var time: String? = null,
                            var isSelf: Boolean = false)