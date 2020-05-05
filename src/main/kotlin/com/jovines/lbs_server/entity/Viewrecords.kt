package com.jovines.lbs_server.entity

import java.io.Serializable
import java.util.*

/**
 * (Viewrecords)实体类
 *
 * @author Jovines
 * @since 2020-05-05 17:44:33
 */
class Viewrecords
(var id: Long? = null,
 var messageid: Long? = null,
 var checkuser: Long? = null,
 var time: Date? = null) : Serializable {

    companion object {
        private const val serialVersionUID = -56294422196623983L
    }
}