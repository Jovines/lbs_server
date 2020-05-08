package com.jovines.lbs_server.entity

import java.io.Serializable
import java.util.*

/**
 * (Highqualityuser)实体类
 *
 * @author Jovines
 * @since 2020-05-07 21:22:45
 */
class Highqualityuser(
        var id: Long? = null,
        var user: Long? = null,
        var jointime: Date? = null,
        var goodreason: String? = null) : Serializable {

    companion object {
        private const val serialVersionUID = 764227037206262316L
    }
}