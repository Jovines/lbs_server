package com.jovines.nest_server.entity

import java.util.*

/**
 * Created by Jovines on 2020/6/6 17:48
 * description :
 */
class Activity {
    var id: Long? = null
    var activityName: String? = null
    var content: String? = null
    var time: Date? = null
    var frontCover: String? = null
    var expired: Int? = null


    companion object {
        private const val serialVersionUID = -93750213342987018L
    }
}