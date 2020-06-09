package com.jovines.nest_server.network

import retrofit2.http.Field
import retrofit2.http.GET

/**
 * @author Jovines
 * create 2020-05-09 10:27 下午
 * description:
 */
interface MapService {
    @GET
    fun reverseAddressCoding(
            @Field("location") location: String,
            @Field("key") key: String = "e8575f2d7a229c1e13207354e69a3185",
            @Field("radius") radius: Long? = null
    )
}