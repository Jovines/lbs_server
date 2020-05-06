package com.jovines.lbs_server.bean

import java.util.*

/**
 * @author Jovines
 * create 2020-05-06 5:21 下午
 * description:
 */
data class PersonalMessageDetailsBean(var id: Long? = null,
                                      var user: Long? = null,
                                      var title: String? = null,
                                      var content: String? = null,
                                      var time: Date? = null,
                                      var lon: Double? = null,
                                      var lat: Double? = null,
                                      var images: String? = null,
                                      var viewUserCount: Int? = null)