package com.jovines.nest_server.controller

import com.jovines.nest_server.bean.StatusWarp
import com.jovines.nest_server.dao.ActivityDao
import com.jovines.nest_server.entity.Activity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

/**
 * Created by Jovines on 2020/6/6 17:50
 * description :
 */

@RestController
@RequestMapping("activity")
class ActivityController(@Resource
                         val activityDao: ActivityDao) {


    @GetMapping("getUnexpiredEvents",
            produces = ["application/json;charset=UTF-8"])
    fun getUnexpiredEvents(): StatusWarp<List<Activity>> {
        return StatusWarp(1000, activityDao.getUnexpiredEvents())
    }




    @GetMapping("getTheLatestEvents",
            produces = ["application/json;charset=UTF-8"])
    fun getTheLatestEvents(): StatusWarp<List<Activity>> {
        return StatusWarp(1000, activityDao.getUnexpiredEvents())
    }




    @GetMapping("getTheHottestEvent",
            produces = ["application/json;charset=UTF-8"])
    fun getTheHottestEvent(): StatusWarp<List<Activity>> {
        return StatusWarp(1000, activityDao.getUnexpiredEvents())
    }

}