package com.jovines.lbs_server.entity

import java.io.Serializable
import java.util.*

/**
 * (Lifecirclemessageitem)实体类
 *
 * @author Jovines
 * @since 2020-04-14 11:59:42
 */
data class Lifecirclemessageitem(
        var id: Long? = null,
        var user: Long? = null,
        var title: String? = null,
        var content: String? = null,
        var time: String? = null) : Serializable {

    companion object {
        private const val serialVersionUID = -95785221984917751L
    }
}