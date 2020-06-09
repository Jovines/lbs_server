package com.jovines.nest_server.dao

import com.jovines.nest_server.entity.Activity

/**
 * Created by Jovines on 2020/6/6 17:48
 * description :
 */
interface ActivityDao {
    fun insert(record: Activity?): Int
    fun insertSelective(record: Activity?): Int
    fun getUnexpiredEvents():List<Activity>
}