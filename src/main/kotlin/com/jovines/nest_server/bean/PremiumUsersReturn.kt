package com.jovines.nest_server.bean

import java.util.*

/**
 * @author Jovines
 * create 2020-05-07 9:32 下午
 * description:
 */
data class PremiumUsersReturn(var id: Long? = null,
                              var user: Long? = null,
                              var jointime: Date? = null,
                              var goodreason: String? = null,
                              var nickname: String? = null,
                              var description: String? = null,
                              var avatar: String? = null,
                              var lon: Double? = null,
                              var lat: Double? = null,
                              var messages: List<CardMessageReturn>? = null)