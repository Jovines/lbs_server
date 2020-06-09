package com.jovines.nest_server.entity

import java.util.*

/**
 * Created by Jovines on 2020/6/7 15:07
 * description :
 */
class Comment(
        var content: String? = null,
        var messageId: Long? = null,
        var userId: Long? = null,
        var id: Long? = null,
        var time: Date? = null
)