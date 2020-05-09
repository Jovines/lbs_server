package com.jovines.lbs_server.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Jovines
 * create 2020-05-09 10:24 下午
 * description:
 */
object ApiGenerator {

    private var defaultRetrofit: Retrofit =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                    .build()

    fun <T> getApiService(clazz: Class<T>) = defaultRetrofit.create(clazz)

    fun <T> getApiService(retrofit: Retrofit, clazz: Class<T>) = retrofit.create(clazz)
}