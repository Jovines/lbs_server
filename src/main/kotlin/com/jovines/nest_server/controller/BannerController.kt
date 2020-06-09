package com.jovines.nest_server.controller

import com.jovines.nest_server.bean.StatusWarp
import com.jovines.nest_server.dao.BannerDao
import com.jovines.nest_server.entity.Banner
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

/**
 * (Banner)表控制层
 *
 * @author Jovines
 * @since 2020-06-07 18:52:58
 */
@RestController
@RequestMapping("banner")
class BannerController(
        /**
         * 服务对象
         */
        @Resource
        private val bannerService: BannerDao) {


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("getBanner")
    fun getBanner(id: Long?): StatusWarp<List<Banner>> {
        return StatusWarp(1000, bannerService.queryAll(null))
    }
}