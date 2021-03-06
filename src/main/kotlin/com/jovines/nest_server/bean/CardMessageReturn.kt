package com.jovines.nest_server.bean

/**
 * @author Jovines
 * create 2020-05-01 10:37 下午
 * description:
 */
data class CardMessageReturn(
        var id: Long? = null,
        var user: Long? = null,
        var title: String = "",
        var content: String = "",
        var time: String? = null,
        var nickname: String = "",
        var description: String = "",
        var avatar: String = "",
        var lon: Double? = null,
        var lat: Double? = null,
        var images: String? = null,
        var checkCount: Int? = null,
        var commentCount: Int = 0
)