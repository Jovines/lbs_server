package com.jovines.lbs_server.entity

import java.io.Serializable

/**
 * (User)实体类
 *
 * @author Jovines
 * @since 2020-04-30 20:43:59
 */
class User
(var phone: Long? = null,
 var nickname: String? = null,
 var password: String? = null,
 var description: String? = null,
 var avatar: String? = null,
 var lng: Double? = null,
 var lat: Double? = null) : Serializable {

    companion object {
        private const val serialVersionUID = -87868821129653017L
    }
}