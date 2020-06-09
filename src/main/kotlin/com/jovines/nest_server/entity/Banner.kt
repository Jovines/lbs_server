package com.jovines.nest_server.entity

import java.io.Serializable

/**
 * (Banner)实体类
 *
 * @author Jovines
 * @since 2020-06-07 18:52:58
 */
class Banner : Serializable {
    var id: Long? = null
    var frontcover: String? = null

    companion object {
        private const val serialVersionUID = -72600401629973966L
    }
}